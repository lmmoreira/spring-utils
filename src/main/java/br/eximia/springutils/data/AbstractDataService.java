package br.eximia.springutils.data;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.eximia.springutils.context.SpringApplicationContext;

public abstract class AbstractDataService<T extends Comparable<T>, ID extends Serializable> implements DataService<T, ID> {
	
	@Override
	@Transactional(readOnly=true)
	public List<T> readNative(String sql) {
		Query nativeQuery = this.getEntityManager().createNativeQuery(sql, this.getType());
		@SuppressWarnings("unchecked")
		List<T> results = nativeQuery.getResultList();
		return results;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<T> findAll() {
		List<T> list = getDao().findAll(); 
		Collections.sort(list);
		return list;
	}

	@Override
	@Transactional(readOnly=true)
	public List<T> findAll(Sort sort) {
		return getDao().findAll(sort);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<T> findAll(Pageable pageable) {
		return getDao().findAll(pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public T findById(ID id) {
		return getDao().findOne(id);
	}

	@Override
	@Transactional(readOnly=true)
	public boolean exists(ID id) {
		return getDao().exists(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Long count() {
		return getDao().count();
	}

	@Override
	@Transactional
	public T save(T entity) {
		return getDao().save(entity);
	}

	@Override
	@Transactional
	public List<T> save(Iterable<T> entities) {
		return getDao().save(entities);
	}

	@Override
	@Transactional
	public void delete(T entity) {
		getDao().delete(entity);
	}

	@Override
	@Transactional
	public void delete(Iterable<? extends T> entities) {
		getDao().delete(entities);
	}

	@Override
	@Transactional
	public void deleteAll() {
		getDao().deleteAll();
	}
	
	private Object getBean(Class<?> classType) {
		return SpringApplicationContext.getBean(classType);
	}

	public EntityManager getEntityManager() {
		return ((EntityManager) this.getBean(EntityManager.class));
	}
	
	@SuppressWarnings("unchecked")
	private Class<T> getType() {
		Type type = getClass().getGenericSuperclass();
		ParameterizedType paramType = (ParameterizedType) type;
		return (Class<T>) paramType.getActualTypeArguments()[0];
	}

	public abstract JpaRepository<T, ID> getDao();
	
}

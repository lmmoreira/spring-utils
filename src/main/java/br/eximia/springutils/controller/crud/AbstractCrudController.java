package br.eximia.springutils.controller.crud;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.eximia.springutils.controller.crud.exception.InstanceCreationException;
import br.eximia.springutils.data.DataService;
import br.eximia.springutils.data.domain.GenericEntity;

public class AbstractCrudController<E extends GenericEntity<E>, T extends Serializable> {

	@Autowired
	private DataService<E, T> defaultGenericDataService;
	private List<E> list;
	private E entity;

	public List<E> getList() {
		if (list == null) {
			list = defaultGenericDataService.findAll();
		}

		return list;
	}

	public void setList(List<E> list) {
		this.list = list;
	}

	public E getEntity() {
		return entity;
	}
	
	public void setEntity(E entity) {
		this.entity = entity;
	}

	public boolean isNew() {
		return entity.isNew();
	}

	public void add() {
		try {
			this.setEntity(getType().newInstance());
		} catch (Exception e) {
			throw new InstanceCreationException(e);
		}
	}

	public void delete() {
		defaultGenericDataService.delete(entity);
		reset();
	}

	public void save() {
		defaultGenericDataService.save(entity);
		reset();
	}

	public void reset() {
		this.setList(null);
		this.setEntity(null);
		this.add();
	}

	@SuppressWarnings("unchecked")
	private Class<E> getType() {
		Type type = getClass().getGenericSuperclass();
		ParameterizedType paramType = (ParameterizedType) type;
		return (Class<E>) paramType.getActualTypeArguments()[0];
	}

}
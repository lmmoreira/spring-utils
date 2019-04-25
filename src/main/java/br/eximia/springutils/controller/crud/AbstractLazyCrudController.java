package br.eximia.springutils.controller.crud;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.faces.event.ComponentSystemEvent;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;

import br.eximia.springutils.controller.crud.exception.InstanceCreationException;
import br.eximia.springutils.controller.lazy.LazyModel;
import br.eximia.springutils.data.DataService;
import br.eximia.springutils.data.domain.GenericEntity;

public class AbstractLazyCrudController<E extends GenericEntity<E>, T extends Serializable> {

	@Autowired
	private DataService<E, T> defaultGenericDataService;
	private LazyDataModel<E> list;
	private E entity;
	private Integer total = 0;

	public void pageLoad(ComponentSystemEvent event) {
		this.setList(createLazyModel());
	}
	
	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public LazyDataModel<E> getList() {
		total = list.getRowCount();
		return list;
	}
	
	public LazyModel<E, T> createLazyModel(){
		return new LazyModel<E, T>(this.getType());
	}

	public void setList(LazyDataModel<E> list) {
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
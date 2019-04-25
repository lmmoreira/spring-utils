package br.eximia.springutils.data.domain;

import java.io.Serializable;

import org.springframework.data.jpa.domain.AbstractPersistable;

import br.eximia.springutils.context.SpringApplicationContext;
import br.eximia.springutils.internationalization.Messages;

public abstract class AbstractGenericEntity<E extends GenericEntity<E>, ID extends Serializable> extends AbstractPersistable<ID> implements GenericEntity<E> {
	
	private static final long serialVersionUID = 1L;

	public Object getBean(Class<?> classType) {
        return SpringApplicationContext.getBean(classType);
    }
	
	public Messages getMessages(){
		return (Messages) this.getBean(Messages.class);
	}
	
	public String getIdAsString() {
		return this.getId().toString();
	}
	
	public void cleanId(){
		this.setId(null);
	}
	
}

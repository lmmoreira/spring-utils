package br.eximia.springutils.data.domain;

import java.io.Serializable;

import br.eximia.jsfutils.converter.IdAsString;

public interface GenericEntity<E> extends Serializable, Comparable<E>, IdAsString {

	public abstract boolean isNew();
    @Override
    public abstract int hashCode();
    @Override
    public abstract boolean equals(Object o);
    @Override
    public abstract String toString();
        
}
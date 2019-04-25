package br.eximia.springutils.controller.lazy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.eximia.springutils.context.SpringApplicationContext;
import br.eximia.springutils.data.domain.GenericEntity;

public class LazyModel<T extends GenericEntity<T>, E extends Serializable> extends LazyDataModel<T> {

	private static final long serialVersionUID = 1L;
	
	Class<T> clazz;
	Map<String, Object> conditions;
	
	public LazyModel() {
		super();
	}
	
	public LazyModel(Class<T> clazz) {
		super();
		this.clazz = clazz;
	}

	public LazyModel(Class<T> clazz, Map<String, Object> conditions) {
		super();
		this.clazz = clazz;
		this.conditions = conditions;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		
		if((conditions != null) && (!conditions.isEmpty())){
			filters.putAll(conditions);
	    }
		
		CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(clazz);
		Root<T> from = query.from(clazz);
		query.select(from);
		
	    List<Predicate> predicates = new ArrayList<Predicate>();
	    
	    for(String key : filters.keySet()){
		
	    	String[] fromKey = key.split("___");
	    	
	    	if((fromKey.length == 1) && (fromKey[0].split("---").length == 1)){  
	    		predicates.add(builder.or(
						builder.like(builder.upper(from.<String>get(key)), "%" + filters.get(key).toString().toUpperCase() + "%" ), 
			            builder.equal(from.get(key), filters.get(key)))
						);
	    	} else {

		    	@SuppressWarnings("rawtypes")
				Join join = null;
		    	
		    	for(String fromJoin : fromKey){
		    		
		    		String[] fieldKey = fromJoin.split("---");
		    		
		    		if((join == null) && (fieldKey.length == 1))
		    			join = from.join(fromJoin);
		    		else 
		    			if(join == null)
		    				join = from.join(fieldKey[0]);
		    			else
		    				join = join.join(fieldKey[0]);
		    		
		    		if(fieldKey.length > 1){
		    			predicates.add(builder.or(
		    										builder.like(builder.upper(join.get(fieldKey[1])), "%" + filters.get(key).toString().toUpperCase() + "%"),
		    										builder.equal(join.get(fieldKey[1]), filters.get(key))
		    									 )
		    						 );
		    		}
		    		
		    	}
	    	}
	    	
	    }
		
		if(!filters.isEmpty()){
			query.where(predicates.toArray(new Predicate[]{}));
		}
		
		if(!(sortField.equals("sortBy"))){
			Order order = sortOrder.equals(SortOrder.ASCENDING) ? builder.asc(from.get(sortField)) : builder.desc(from.get(sortField));
			query.orderBy(order);
		}
		
		TypedQuery<T> typedQuery = this.getEntityManager().createQuery(query);
		List<T> results = typedQuery.getResultList();
		//rowCount
        int dataSize = results.size();
        this.setRowCount(dataSize);
        //paginate
        if(dataSize > pageSize) {
            try {
                return results.subList(first, first + pageSize);
            }
            catch(IndexOutOfBoundsException e) {
                return results.subList(first, first + (dataSize % pageSize));
            }
        }
        else {
            return results;
        }
	}

	public Object getBean(Class<?> classType) {
		return SpringApplicationContext.getBean(classType);
	}

	public EntityManager getEntityManager() {
		return ((EntityManager) this.getBean(EntityManager.class));
	}

}

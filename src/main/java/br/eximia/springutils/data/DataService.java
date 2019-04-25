package br.eximia.springutils.data;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataService<T, ID extends Serializable> {
	List<T> findAll();
	List<T> findAll(Sort sort);
	Page<T> findAll(Pageable pageable);
	
	T findById(ID id);
	
	boolean exists(ID id);
	
	Long count();
	
	T save(T entity);
	List<T> save(Iterable<T> entities);
	//
    void delete(T entity);
    void delete(Iterable<? extends T> entities);
    void deleteAll();
    //
    JpaRepository<T, ID> getDao();
    //
    List<T> readNative(String sql);

}

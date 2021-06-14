package com.mcosta.dao;

import java.util.List;

public interface Persistence<T> {

    public void save(T dado) throws Exception;

    public List<T> findAll() throws Exception;

    public void update(T dado) throws Exception;
    
    public void delete(T dado) throws Exception;

    public T findById(Long id) throws Exception;
}

package br.edu.femass.dao;

import java.util.List;

public interface Dao<T> {

    public void gravar(T objeto) throws Exception;
    public List<T> listar() throws Exception;
    public void alterar(T objeto) throws Exception;
    public void excluir(T objeto) throws Exception;
    public T getPorId(Long id) throws Exception;

}

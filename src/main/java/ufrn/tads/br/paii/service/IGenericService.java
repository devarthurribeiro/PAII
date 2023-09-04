package ufrn.tads.br.paii.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ufrn.tads.br.paii.domain.AbstractEntity;

import java.util.List;

public interface IGenericService <E extends AbstractEntity>{
    public E create(E e);
    public E update(E e, Long id);
    public void delete(Long id);
    public List<E> list();
    public E getById(Long id);
    public Page<E> find(Pageable page);
}
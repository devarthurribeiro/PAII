package ufrn.tads.br.paii.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ufrn.tads.br.paii.domain.AbstractEntity;

public interface IGenericRepository <E extends AbstractEntity> extends ListCrudRepository<E, Long>, PagingAndSortingRepository<E, Long> {
}

package ufrn.tads.br.paii.service;

import org.springframework.stereotype.Service;
import ufrn.tads.br.paii.domain.Residencia;
import ufrn.tads.br.paii.repository.ResidenciaRepository;


@Service
public class ResidenciaService extends GenericService<Residencia, ResidenciaRepository> {
    public ResidenciaService(ResidenciaRepository repository) {
        super(repository);
    }
}

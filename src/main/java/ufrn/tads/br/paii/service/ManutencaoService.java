package ufrn.tads.br.paii.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ufrn.tads.br.paii.domain.Manutencao;
import ufrn.tads.br.paii.domain.Residencia;
import ufrn.tads.br.paii.repository.ManutencaoRepository;

import java.util.List;

@Service
public class ManutencaoService extends GenericService<Manutencao, ManutencaoRepository>{
    public ManutencaoService(ManutencaoRepository repository) {
        super(repository);
    }
}

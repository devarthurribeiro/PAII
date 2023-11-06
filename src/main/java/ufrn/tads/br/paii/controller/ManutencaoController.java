package ufrn.tads.br.paii.controller;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufrn.tads.br.paii.domain.Manutencao;
import ufrn.tads.br.paii.repository.ManutencaoRepository;
import ufrn.tads.br.paii.service.ManutencaoService;

@RestController
@RequestMapping("/manutencoes")
@CrossOrigin(origins = "*")
public class ManutencaoController {
    ManutencaoService service;
    ModelMapper mapper;
    public ManutencaoController(ManutencaoService service, ModelMapper mapper){
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Manutencao.DtoResponse create(@RequestBody Manutencao.DtoRequest m){
        Manutencao manutencao = this.service.create(Manutencao.DtoRequest.convertToEntity(m, mapper));
        Manutencao.DtoResponse response = Manutencao.DtoResponse.convertToDto(manutencao, mapper);
        response.generateLinks(manutencao.getId());
        return response;
    }

    @GetMapping
    public ResponseEntity<Page<Manutencao.DtoResponse>> find(Pageable page){
        Page<Manutencao.DtoResponse> dtoResponses = service
                .find(page)
                .map(record -> {
                    Manutencao.DtoResponse response = Manutencao.DtoResponse.convertToDto(record, mapper);
                    return response;
                });
        return new ResponseEntity<>(dtoResponses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Manutencao.DtoResponse getById(@PathVariable Long id){

        Manutencao m = this.service.getById(id);
        Manutencao.DtoResponse response = Manutencao.DtoResponse.convertToDto(m, mapper);
        response.generateLinks(m.getId());

        return response;
    }


    @PutMapping("/{id}")
    public Manutencao.DtoResponse update(@RequestBody Manutencao.DtoRequest dtoRequest, @PathVariable Long id){
        Manutencao m = Manutencao.DtoRequest.convertToEntity(dtoRequest, mapper);
        Manutencao.DtoResponse response = Manutencao.DtoResponse.convertToDto(this.service.update(m, id), mapper);
        response.generateLinks(id);
        return response;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        this.service.delete(id);
    }
}

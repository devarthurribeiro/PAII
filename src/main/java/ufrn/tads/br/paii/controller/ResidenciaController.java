package ufrn.tads.br.paii.controller;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufrn.tads.br.paii.domain.Residencia;
import ufrn.tads.br.paii.service.ResidenciaService;

@RestController
@RequestMapping("/residencias")
@CrossOrigin(origins = "*")
public class ResidenciaController {

    ResidenciaService service;
    ModelMapper mapper;
    public ResidenciaController(ResidenciaService service, ModelMapper mapper){
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Residencia.DtoResponse create(@RequestBody Residencia.DtoRequest r){
        Residencia residencia = this.service.create(Residencia.DtoRequest.convertToEntity(r, mapper));
        Residencia.DtoResponse response = Residencia.DtoResponse.convertToDto(residencia, mapper);
        response.generateLinks(residencia.getId());

        return response;
    }

    @GetMapping
    public ResponseEntity<Page<Residencia.DtoResponse>> find(Pageable page) {

        Page<Residencia.DtoResponse> dtoResponses = service
                .find(page)
                .map(record -> {
                    Residencia.DtoResponse response = Residencia.DtoResponse.convertToDto(record, mapper);
                    response.generateLinks(record.getId());
                    return response;
                });

        return new ResponseEntity<>(dtoResponses, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public Residencia.DtoResponse getById(@PathVariable Long id){
        Residencia r = this.service.getById(id);
        Residencia.DtoResponse response = Residencia.DtoResponse.convertToDto(r, mapper);
        response.generateLinks(r.getId());

        return response;
    }

    @PutMapping("/{id}")
    public Residencia.DtoResponse update(@RequestBody Residencia.DtoRequest dtoRequest, @PathVariable Long id){
        Residencia r = Residencia.DtoRequest.convertToEntity(dtoRequest, mapper);
        Residencia.DtoResponse response = Residencia.DtoResponse.convertToDto(this.service.update(r, id), mapper);
        response.generateLinks(id);
        return response;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        this.service.delete(id);
    }

}

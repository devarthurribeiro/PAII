package ufrn.tads.br.paii.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;
import ufrn.tads.br.paii.controller.ResidenciaController;

import java.util.Date;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manutencao extends AbstractEntity{

    private Date dataCadastro;
    private Date dataFinalizacao;
    private String descricao;
    private String eletricistaResponsavel;
    private String status;

    @ManyToOne
    @JoinColumn(name = "fk_residencia")
    private Residencia residencia;

    @ManyToOne
    @JoinColumn(name = "fk_usuario")
    private Usuario usuario;

    @Override
    public void partialUpdate(AbstractEntity e) {
        if(e instanceof Manutencao manutencao){
            this.dataCadastro = manutencao.dataCadastro;
            this.dataFinalizacao = manutencao.dataFinalizacao;
            this.descricao = manutencao.descricao;
            this.eletricistaResponsavel = manutencao.eletricistaResponsavel;
            this.status = manutencao.status;
        }
    }

    @Data
    public static class DtoRequest{
        //Os atributos dataCadastro e dataFinalizados são atribuidos no constructor
        //ao criar/atualizar uma manutencao
        @NotBlank(message = "Descrição em branco")
        String descricao;
        @NotBlank(message = "Eletricista responsável em branco")
        String eletricistaResponsavel;
        @NotBlank(message = "Status em branco")
        String status;
        Residencia residencia;
        Usuario usuario;
        public static Manutencao convertToEntity(DtoRequest dto, ModelMapper mapper) {
            return mapper.map(dto, Manutencao.class);
        }
    }
    @Data
    public static class DtoResponse extends RepresentationModel<Residencia.DtoResponse> {
        Date dataCadastro;
        Date dataFinalizacao;
        String descricao;
        String eletricistaResponsavel;
        String status;
        @JsonIgnoreProperties({"cargaInstalada","padraoEntrada","quadroDistribuicao",
                "usuario","manutencoes","deletedAt","createdAt","updatedAt"})
        Residencia residencia;
        @JsonIgnoreProperties({"login","password","email","tipo","isAdmin","residencias","manutencoes",
                "enabled","authorities","credentialsNonExpired","accountNonExpired","accountNonLocked",
                "deletedAt","createdAt","updatedAt"})
        Usuario usuario;
        public static DtoResponse convertToDto(Manutencao manutencao, ModelMapper mapper) {
            return mapper.map(manutencao, DtoResponse.class);
        }

        public void generateLinks(Long id){
            add(linkTo(ResidenciaController.class).slash(id).withSelfRel());
            add(linkTo(ResidenciaController.class).withRel("manutencoes"));
            add(linkTo(ResidenciaController.class).slash(id).withRel("delete"));
        }
    }
}

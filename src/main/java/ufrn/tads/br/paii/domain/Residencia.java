package ufrn.tads.br.paii.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import ufrn.tads.br.paii.controller.ResidenciaController;
import org.springframework.hateoas.RepresentationModel;
import org.modelmapper.ModelMapper;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Residencia extends AbstractEntity{

    private String endereco;
    private double cargaInstalada;
    private String padraoEntrada;
    private String quadroDistribuicao;

    @ManyToOne
    @JoinColumn(name="fk_usuario")
    private Usuario usuario;

    @OneToMany(mappedBy = "residencia")
    private List<Manutencao> manutencoes;

    @Override
    public void partialUpdate(AbstractEntity e) {
        if(e instanceof Residencia residencia){
            this.endereco = residencia.endereco;
            this.cargaInstalada = residencia.cargaInstalada;
            this.padraoEntrada = residencia.padraoEntrada;
            this.quadroDistribuicao = residencia.quadroDistribuicao;
        }
    }

    @Data
    public static class DtoRequest{
        @NotBlank(message = "Endereco em branco")
        String endereco;
        @NotBlank(message = "Carga instalada em branco")
        double cargaInstalada;
        @NotBlank(message = "Padrao de entrada em branco")
        String padraoEntrada;
        @NotBlank(message = "Quadro de distribuição em branco")
        String quadroDistribuicao;
        Usuario usuario;
        List<Manutencao> manutencoes;
        public static Residencia convertToEntity(DtoRequest dto, ModelMapper mapper) {
            return mapper.map(dto, Residencia.class);
        }
    }
    @Data
    public static class DtoResponse extends RepresentationModel<DtoResponse>{
        String endereco;
        double cargaInstalada;
        String padraoEntrada;
        String quadroDistribuicao;
        @JsonIgnoreProperties({"login","password","email","tipo","isAdmin","residencias","manutencoes",
                "enabled","authorities","credentialsNonExpired","accountNonExpired","accountNonLocked",
                "deletedAt","createdAt","updatedAt"})
        Usuario usuario;
        @JsonIgnoreProperties({"dataCadastro","dataFinalizacao","descricao","eletricistaResponsavel",
                "status","residencia","usuario","deletedAt","createdAt","updatedAt"})
        List<Manutencao> manutencoes;

        public static DtoResponse convertToDto(Residencia residencia, ModelMapper mapper) {
            return mapper.map(residencia, DtoResponse.class);
        }

        public void generateLinks(Long id){
            add(linkTo(ResidenciaController.class).slash(id).withSelfRel());
            add(linkTo(ResidenciaController.class).withRel("residencias"));
            add(linkTo(ResidenciaController.class).slash(id).withRel("delete"));
        }
    }
}

package ufrn.tads.br.paii.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ufrn.tads.br.paii.controller.UsuarioController;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE usuario SET deleted_at = CURRENT_TIMESTAMP WHERE id=?")
@Where(clause = "deleted_at is null")
public class Usuario extends AbstractEntity implements UserDetails {
    private String username;
    @Column(unique = true)
    private String login;
    private String password;
    private String email;
    private String tipo;
    private Boolean isAdmin = false;

    @OneToMany(mappedBy = "usuario")
    private List<Residencia> residencias;

    @OneToMany(mappedBy = "usuario")
    private List<Manutencao> manutencoes;

    @Override
    public void partialUpdate(AbstractEntity e) {
        if (e instanceof Usuario usuario){
            this.username = usuario.username;
            this.login = usuario.login;
            this.password = usuario.password;
            this.email = usuario.email;
            this.tipo = usuario.tipo;
        }
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Data
    public static class DtoRequest {
        @NotBlank(message = "Usuário em branco")
        String username;
        @NotBlank(message = "Login em branco")
        String login;
        @NotBlank(message = "Password em branco")
        String password;
        @NotBlank(message = "Email em branco")
        String email;
        @NotBlank(message = "Tipo em branco")
        String tipo;
        List<Residencia> residencias;


        public static Usuario convertToEntity(DtoRequest dto, ModelMapper mapper) {
            return mapper.map(dto, Usuario.class);
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class DtoResponse extends RepresentationModel<DtoResponse> {
        Long id;
        String username;
        String login;
        String password;
        @JsonIgnoreProperties({"cargaInstalada", "padraoEntrada", "quadroDistribuicao","usuario",
                "manutencoes","deletedAt", "createdAt", "updatedAt"})
        List<Residencia> residencias;
        @JsonIgnoreProperties({"dataCadastro","dataFinalizacao","descricao","eletricistaResponsavel",
                "status","residencia","usuario","deletedAt", "createdAt", "updatedAt"})
        List<Manutencao> manutencoes;
        public static DtoResponse convertToDto(Usuario u, ModelMapper mapper){
            return mapper.map(u, DtoResponse.class);
        }

        public void generateLinks(Long id){
            add(linkTo(UsuarioController.class).slash(id).withSelfRel());
            add(linkTo(UsuarioController.class).withRel("usuarios"));
            add(linkTo(UsuarioController.class).slash(id).withRel("delete"));
        }
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "username='" + username + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}

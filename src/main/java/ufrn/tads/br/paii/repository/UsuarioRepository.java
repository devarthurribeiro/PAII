package ufrn.tads.br.paii.repository;

import org.springframework.security.core.userdetails.UserDetails;
import ufrn.tads.br.paii.domain.Usuario;

public interface UsuarioRepository extends IGenericRepository<Usuario>{
    UserDetails findByLogin(String login);
}

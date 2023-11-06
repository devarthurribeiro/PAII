package ufrn.tads.br.paii.controller;

import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ufrn.tads.br.paii.config.LoginDTO;
import ufrn.tads.br.paii.config.TokenResponse;
import ufrn.tads.br.paii.service.TokenService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/login")
public class AuthController {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;


    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public TokenResponse token(@RequestBody @Valid LoginDTO loginDTO) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.login(), loginDTO.password()));

        return TokenResponse.builder().token(tokenService.generateToken(authentication)).build();
    }
}

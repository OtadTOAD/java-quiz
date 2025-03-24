package ge.ibsu.demo.security.auth;

import ge.ibsu.demo.entities.User;
import ge.ibsu.demo.repositories.UserRepository;
import ge.ibsu.demo.security.config.JwtService;
import ge.ibsu.demo.util.GeneralUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegistrationRequest request) throws Exception {
        User user = new User();
        GeneralUtil.getCopyOf(request, user, "password");
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationData data) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        data.getUserName(),
                        data.getPassword())
        );
        var user = repository.findByEmail(data.getUserName())
                .orElseThrow(
                    () ->  new UsernameNotFoundException("USER_NOT_FOUND")
                );
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}

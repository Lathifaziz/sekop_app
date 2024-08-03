package programer.sekop.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import programer.sekop.model.User;
import programer.sekop.repository.UserRepository;
import programer.sekop.utill.dataTransfer.User.LoginUserRequest;
import programer.sekop.utill.dataTransfer.TokenResponse;
import programer.sekop.utill.security.BCrypt;

import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ValidationService validationService;

    @Transactional
    public TokenResponse login(LoginUserRequest request){
        validationService.validation(request);

        User user = userRepository.findById(request.getUsername()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Username/Password salah")
        );

        if (BCrypt.checkpw(request.getPassword(),user.getPassword())){
          user.setToken(UUID.randomUUID().toString());
          user.setTokenExpiredAt(System.currentTimeMillis() + (1000*60*24*30));
          userRepository.save(user);

          return TokenResponse.builder()
                  .token(user.getToken())
                  .expiredAt(user.getTokenExpiredAt())
                  .build();

        }else {
            throw new  ResponseStatusException(HttpStatus.UNAUTHORIZED,"Username/Password salah");
        }

    }
    @Transactional
    public void logout(User user){
        user.setToken(null);
        user.setTokenExpiredAt(null);

        userRepository.save(user);
    }

}

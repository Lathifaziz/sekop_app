package programer.sekop.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import programer.sekop.model.User;
import programer.sekop.repository.UserRepository;
import programer.sekop.service.UserService;
import programer.sekop.utill.dataTransfer.User.RegisterUserRequest;
import programer.sekop.utill.dataTransfer.User.UpdateUserRequest;
import programer.sekop.utill.dataTransfer.User.UserResponse;
import programer.sekop.utill.security.BCrypt;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    @Override
    @Transactional
    public void register(RegisterUserRequest request) {
        validationService.validation(request);

        if (userRepository.existsById(request.getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Username telah Digunakan");
        }
        User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(BCrypt.hashpw(request.getPassword(),BCrypt.gensalt()));
            user.setName(request.getName());
            userRepository.save(user);

    }

    @Override
    public UserResponse getAll(User user){
        return UserResponse.builder()
                .username(user.getUsername())
                .name(user.getName())
                .build();
    }

    @Override
    public UserResponse update(User user, UpdateUserRequest request){
        if (Objects.nonNull(request.getName())){
            user.setName(request.getName());
        }
        if (Objects.nonNull(request.getPassword())){
            user.setPassword(request.getPassword());
        }

        userRepository.save(user);

        return UserResponse.builder()
                .username(user.getUsername())
                .name(user.getName())
                .build();
    }



}

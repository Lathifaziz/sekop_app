package programer.sekop.service;

import programer.sekop.model.User;
import programer.sekop.utill.dataTransfer.User.RegisterUserRequest;
import programer.sekop.utill.dataTransfer.User.UpdateUserRequest;
import programer.sekop.utill.dataTransfer.User.UserResponse;

public interface UserService {
    void register(RegisterUserRequest request);
    UserResponse getAll(User user);
    UserResponse update(User user, UpdateUserRequest request);
}

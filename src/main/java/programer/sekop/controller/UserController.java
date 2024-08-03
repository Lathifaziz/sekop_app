package programer.sekop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import programer.sekop.model.User;
import programer.sekop.service.impl.UserServiceImpl;
import programer.sekop.utill.dataTransfer.User.RegisterUserRequest;
import programer.sekop.utill.dataTransfer.User.UpdateUserRequest;
import programer.sekop.utill.dataTransfer.User.UserResponse;
import programer.sekop.utill.dataTransfer.WebResponse;

@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping(
            path = "/users",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> register(
            @RequestBody RegisterUserRequest request)
    {
        userService.register(request);

        return WebResponse.<String>builder().data("Berhasil Register").build();
    }

    @GetMapping(
            path = "/users/current",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> getAll(User user){
        UserResponse userResponse = userService.getAll(user);

        return WebResponse.<UserResponse>builder()
                .data(userResponse).build();
    }

    @PatchMapping(
            path = "/users/current",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> update(User user, @RequestBody UpdateUserRequest request){
        UserResponse response = userService.update(user, request);
        return WebResponse.<UserResponse>builder()
                .data(response)
                .build();
    }



}

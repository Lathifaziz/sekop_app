package programer.sekop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import programer.sekop.model.User;
import programer.sekop.service.impl.AuthService;
import programer.sekop.utill.dataTransfer.User.LoginUserRequest;
import programer.sekop.utill.dataTransfer.TokenResponse;
import programer.sekop.utill.dataTransfer.WebResponse;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @RequestMapping(
            path = "/user/auth",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<TokenResponse> login(@RequestBody LoginUserRequest request){
        TokenResponse tokenResponse = authService.login(request);
        return WebResponse.<TokenResponse>builder().data(tokenResponse).build();

    }

    @DeleteMapping(
            path = "user/auth",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> logout(User user){
        authService.logout(user);
        return WebResponse.<String>builder().data("ok").build();
    }

}

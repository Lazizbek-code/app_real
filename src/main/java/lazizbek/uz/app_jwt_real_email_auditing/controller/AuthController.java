package lazizbek.uz.app_jwt_real_email_auditing.controller;

import lazizbek.uz.app_jwt_real_email_auditing.payload.ApiResponse;
import lazizbek.uz.app_jwt_real_email_auditing.payload.LoginDto;
import lazizbek.uz.app_jwt_real_email_auditing.payload.RegisterDto;
import lazizbek.uz.app_jwt_real_email_auditing.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public HttpEntity<?> registerUser(@RequestBody RegisterDto registerDto){
        ApiResponse apiResponse = authService.registerUser(registerDto);
        return ResponseEntity.status(apiResponse.getSuccess() ? 201: 409).body(apiResponse);
    }

    @GetMapping("/verifyEmail")
    public HttpEntity<?> verifyEmail(@RequestParam String emailCode, @RequestParam String email){
        ApiResponse apiResponse = authService.verifyEmail(emailCode, email);
        return ResponseEntity.status(apiResponse.getSuccess() ? 201: 409).body(apiResponse);
    }

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto loginDto){
        ApiResponse apiResponse = authService.login(loginDto);
        return ResponseEntity.status(apiResponse.getSuccess() ? 200: 401).body(apiResponse);
    }

}

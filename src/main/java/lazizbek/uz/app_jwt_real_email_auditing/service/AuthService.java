package lazizbek.uz.app_jwt_real_email_auditing.service;

import lazizbek.uz.app_jwt_real_email_auditing.entity.User;
import lazizbek.uz.app_jwt_real_email_auditing.entity.enums.RoleName;
import lazizbek.uz.app_jwt_real_email_auditing.payload.ApiResponse;
import lazizbek.uz.app_jwt_real_email_auditing.payload.LoginDto;
import lazizbek.uz.app_jwt_real_email_auditing.payload.RegisterDto;
import lazizbek.uz.app_jwt_real_email_auditing.repository.RoleRepository;
import lazizbek.uz.app_jwt_real_email_auditing.repository.UserRepository;
import lazizbek.uz.app_jwt_real_email_auditing.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Lazy
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if (optionalUser.isPresent()){
            return optionalUser.get();
        }else {
            throw new UsernameNotFoundException(username+ " topilmadi");
        }
    }

    public ApiResponse registerUser(RegisterDto registerDto){

        boolean email = userRepository.existsByEmail(registerDto.getEmail());
        if (email){
            return new ApiResponse("Bunday email mavjud", false);
        }
        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRoles(Collections.singleton(roleRepository.findByRoleName(RoleName.ROLE_USER)));
        user.setEmailCode(UUID.randomUUID().toString());
        userRepository.save(user);
        sendEmail(user.getEmail(),user.getEmailCode());
        return new ApiResponse("Muvaffaqiyatli ro'yhatdan o'tdingiz. Akkountingiz aktivlashtirilishi uchun emailingizni tasdiqlang", true);
    }

    public Boolean sendEmail(String sendingEmail, String emailCode){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("Millioner.uz");
            message.setTo(sendingEmail);
            message.setSubject("Akkauntni tasdiqlash");
            message.setText("<a href='http://localhost:9099/api/auth/verifyEmail?emailCode=" + emailCode + "&email=" + sendingEmail + "' >Tasdiqlang</a>");
            javaMailSender.send(message);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public ApiResponse verifyEmail(String emailCode, String email) {
        Optional<User> userOptional = userRepository.findByEmailCodeAndEmail(emailCode, email);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            user.setEnabled(true);
            user.setEmailCode(null);
            userRepository.save(user);
            return new ApiResponse("Akkaunt tasdiqlandi", true);
        }
        return new ApiResponse("Akkaunt allaqachon tasdiqlangan", false);
    }
    
    public ApiResponse login(LoginDto loginDto){
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(),
                    loginDto.getPassword()
            ));
            User user = (User)authentication.getPrincipal();
            String token = jwtProvider.generateToken(loginDto.getUsername(), user.getRoles());
            return new ApiResponse("Token", true, token);
        }catch (BadCredentialsException e){
            return new ApiResponse("Parol yoki login xato", false);
        }
    }

}

package lazizbek.uz.app_jwt_real_email_auditing.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class LoginDto {
    @NotNull
    @Email
    private String username;

    @NotNull
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

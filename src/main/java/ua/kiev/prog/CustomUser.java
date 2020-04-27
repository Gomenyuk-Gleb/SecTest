package ua.kiev.prog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@JsonFormat
@Data @NoArgsConstructor
public class CustomUser {

    private String login;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private String email;
    private String phone;

    public CustomUser(String login, String password, UserRole role, String email, String phone) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.email = email;
        this.phone = phone;
    }
}

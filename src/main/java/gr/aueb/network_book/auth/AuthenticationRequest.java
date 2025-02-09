package gr.aueb.network_book.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationRequest {

    @Email(message = "Email is required field")
    @NotEmpty(message = "Email is required field")
    @NotBlank(message = "Email is required field")
    private String email;

    @NotEmpty(message = "Password is required field")
    @NotBlank(message = "Password is required field")
    @Size(min = 8 , message = "Password must be 8 characters minimum or is invalid")
    private String password;
}

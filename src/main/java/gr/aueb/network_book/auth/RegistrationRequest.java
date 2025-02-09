package gr.aueb.network_book.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class RegistrationRequest {

    @NotEmpty(message = "Firstname is required field")
    @NotBlank(message = "Firstname is required field")
    private String firstname;

    @NotEmpty(message = "Lastname is required field")
    @NotBlank(message = "Lastname is required field")
    private String lastname;

    @Email(message = "Email is not formatted")
    @NotEmpty(message = "Email is required field")
    @NotBlank(message = "Email is required field")
    private String email;

    @Size(min = 8)
    @NotEmpty(message = "Password is required field")
    @NotBlank(message = "Password is required field")
    private String password;

}

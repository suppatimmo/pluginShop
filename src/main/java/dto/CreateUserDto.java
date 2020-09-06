package dto;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import model.ROLE;
import org.springframework.validation.annotation.Validated;

import org.hibernate.validator.constraints.Length;
import javax.validation.Valid;

@Builder
@Getter
@Validated
public class CreateUserDto {

    @NotNull
    @Valid
    @Length(min = 3)
    private String login;

    @NotNull
    @Valid
    @Length(min = 3)
    private String userName;

    @NotNull
    @Valid
    @Length(min = 5)
    private String email;

    @NotNull
    @Valid
    @Length(min = 5)
    private String password;

}


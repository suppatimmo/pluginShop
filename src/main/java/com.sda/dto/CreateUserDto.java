package com.sda.dto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

// @Builder
@Getter
@Validated
@Data
public class CreateUserDto {
    @NotNull
    @Valid
    private String email;

    @NotNull
    @Valid
    @Length(min = 3)
    private String userName;

    @NotNull
    @Valid
    @Length(min = 5)
    private String password;

}


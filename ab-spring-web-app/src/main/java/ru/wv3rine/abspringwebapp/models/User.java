package ru.wv3rine.abspringwebapp.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

// В задании написано про
// name notnull, хотя логичнее это сделать с логином (с учетом
// акцента на аутентификации
@Table(name = "users")
public record User(
        @Id
        Integer id,
        String name,
        @NotNull
        @NotBlank
        String login,
        @NotNull
        @NotBlank
        String password,
        String url
) {
}
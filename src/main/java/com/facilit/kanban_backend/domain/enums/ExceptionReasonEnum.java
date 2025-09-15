package com.facilit.kanban_backend.domain.enums;


import com.facilit.kanban_backend.exception.BusinessException;
import com.facilit.kanban_backend.exception.EmailEmUsoException;
import com.facilit.kanban_backend.exception.UsuarioInvalido;
import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
public enum ExceptionReasonEnum {
    LOGIN_INEXISTENTE(UsuarioInvalido.class, HttpStatus.UNPROCESSABLE_ENTITY, "Invalid login or password"),
    EMAIL_JA_EXISTE_NA_BASE(EmailEmUsoException.class, HttpStatus.CONFLICT, "Email already exists"),
    ERRO_NEGOCIO(BusinessException.class, HttpStatus.UNPROCESSABLE_ENTITY, "Business error"),
    TOKEN_EXPIRADO(JwtException.class,HttpStatus.UNAUTHORIZED, "Unauthorized - invalid session")
    ;
    @SuppressWarnings("rawtypes")
    private Class exception;
    private HttpStatus statusCode;
    private String dsError;

    public static ExceptionReasonEnum getEnum(@SuppressWarnings("rawtypes") Class param) {
        for (ExceptionReasonEnum valor : values()) {
            if (valor.exception == param) {
                return valor;
            }
        }
        return null;
    }
}
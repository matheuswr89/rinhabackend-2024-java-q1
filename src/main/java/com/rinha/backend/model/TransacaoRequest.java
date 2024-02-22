package com.rinha.backend.model;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

public record TransacaoRequest(
        @NotNull @Min(0) Long valor,
        @NotNull @NotEmpty @Pattern(regexp = "[c|d]") String tipo,
        @NotNull @NotEmpty @NotBlank @Length(max = 10) String descricao) {
}
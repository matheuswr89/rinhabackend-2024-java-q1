package com.rinha.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public record Extrato(Saldo saldo, List<Transacao> ultimas_transacoes) {
    public record Saldo(Long total, Long limite, @JsonProperty("data_extrato") LocalDateTime dataExtrato) {
    }

    public record Transacao(long valor, String tipo, String descricao, ZonedDateTime realizada_em) {
    }
}

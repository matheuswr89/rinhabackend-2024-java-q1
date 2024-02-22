package com.rinha.backend.model;

import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;

@Table("transacao")
public record Transacao(Long cliente_id, Long valor, OffsetDateTime realizada_em, String descricao, String tipo) {
}

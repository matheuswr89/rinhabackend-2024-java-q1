package com.rinha.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("cliente")
public record Cliente(@Id int id, long limite, long saldo) {
}
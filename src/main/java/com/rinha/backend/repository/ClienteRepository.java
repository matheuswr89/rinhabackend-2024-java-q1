package com.rinha.backend.repository;

import com.rinha.backend.model.Cliente;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ClienteRepository extends R2dbcRepository<Cliente, Long> {
    @Query("UPDATE cliente SET saldo = (saldo + :valor) WHERE id = :id AND (saldo + :valor) > (-limite) RETURNING *")
    Mono<Cliente> updateClienteSaldo(Long id, Long valor);
}

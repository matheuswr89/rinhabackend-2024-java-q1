package com.rinha.backend.repository;

import com.rinha.backend.model.Transacao;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TransacaoRepository extends R2dbcRepository<Transacao, Long> {
    @Query("SELECT * FROM transacao WHERE cliente_id = :id ORDER BY realizada_em DESC LIMIT 10")
    Flux<Transacao> buscarTransacao(Long id);
}

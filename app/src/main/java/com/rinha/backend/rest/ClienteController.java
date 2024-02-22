package com.rinha.backend.rest;

import com.rinha.backend.handler.InconsistentBalanceException;
import com.rinha.backend.handler.ResourceNotFoundException;
import com.rinha.backend.model.*;
import com.rinha.backend.repository.ClienteRepository;
import com.rinha.backend.repository.TransacaoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RestController
public class ClienteController {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private TransacaoRepository transacaoRepository;

    @PostMapping("clientes/{id}/transacoes")
    public Mono<SaldoAtualizado> postTransacao(@PathVariable("id") Long id, @RequestBody @Valid TransacaoRequest transacaoRequest) {
        if (id < 1 || id > 5) {
            throw new ResourceNotFoundException("Cliente não encontrado!");
        }

        long valor = "c".equals(transacaoRequest.tipo()) ? transacaoRequest.valor() : (-transacaoRequest.valor());
        return clienteRepository.updateClienteSaldo(id, valor)
                    .flatMap(cliente -> transacaoRepository.save(
                                new Transacao(id, transacaoRequest.valor(), null, transacaoRequest.descricao(), transacaoRequest.tipo())
                            ).thenReturn(new SaldoAtualizado(cliente.limite(), cliente.saldo()))
                    ).switchIfEmpty(Mono.error(new InconsistentBalanceException("Transação rejeitada")));
    }

    @GetMapping("clientes/{id}/extrato")
    public Mono<Extrato> getExtrato(@PathVariable("id") Long id) {
        if (id < 1 || id > 5) {
            return Mono.error(new ResourceNotFoundException("Cliente não encontrado!"));
        }

        Mono<Extrato.Saldo> clienteMono = clienteRepository.findById(id).cache()
                .map(cliente -> new Extrato.Saldo(cliente.saldo(), cliente.limite(), LocalDateTime.now()));

        Flux<Extrato.Transacao> transacaoFlux = transacaoRepository.buscarTransacao(id)
                .map(transacaos -> new Extrato.Transacao(
                        transacaos.valor(),
                        transacaos.tipo(),
                        transacaos.descricao(),
                        transacaos.realizada_em().atZoneSameInstant(ZoneOffset.UTC)
                ));
        return clienteMono.zipWith(transacaoFlux.collectList(), Extrato::new);
    }
}

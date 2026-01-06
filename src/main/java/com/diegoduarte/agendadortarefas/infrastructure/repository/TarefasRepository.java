package com.diegoduarte.agendadortarefas.infrastructure.repository;

import com.diegoduarte.agendadortarefas.infrastructure.entity.Tarefas;
import com.diegoduarte.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TarefasRepository extends MongoRepository<Tarefas, String> {

    List<Tarefas> findByDataEventoBetweenAndStatusNotificacaoEnum (LocalDateTime dataInicial,
                                                                   LocalDateTime dataFinal,
                                                                   StatusNotificacaoEnum statusNotificacaoEnum);
    List<Tarefas> findByEmailUsuario (String email);
}

package com.diegoduarte.agendadortarefas.infrastructure.repository;

import com.diegoduarte.agendadortarefas.infrastructure.entity.Tarefas;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefasRepository extends MongoRepository<Tarefas, String> {
}

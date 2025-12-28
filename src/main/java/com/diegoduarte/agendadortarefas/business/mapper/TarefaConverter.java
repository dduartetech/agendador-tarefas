package com.diegoduarte.agendadortarefas.business.mapper;

import com.diegoduarte.agendadortarefas.business.dto.TarefasDTO;
import com.diegoduarte.agendadortarefas.infrastructure.entity.Tarefas;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TarefaConverter {

    Tarefas paraTarefaEntity (TarefasDTO tarefasDTO);
    TarefasDTO paraTarefasDTO (Tarefas tarefas);
}

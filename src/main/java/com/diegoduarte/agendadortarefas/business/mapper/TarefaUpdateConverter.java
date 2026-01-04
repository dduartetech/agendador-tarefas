package com.diegoduarte.agendadortarefas.business.mapper;

import com.diegoduarte.agendadortarefas.business.dto.TarefasDTO;
import com.diegoduarte.agendadortarefas.infrastructure.entity.Tarefas;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TarefaUpdateConverter {

    void updateTarefas (TarefasDTO tarefasDTO, @MappingTarget Tarefas tarefas);
}

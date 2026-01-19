package com.diegoduarte.agendadortarefas.business.mapper;

import com.diegoduarte.agendadortarefas.business.dto.TarefasDTO;
import com.diegoduarte.agendadortarefas.infrastructure.entity.Tarefas;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefaConverter {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "dataEvento", target = "dataEvento")
    @Mapping(source = "dataCriacao", target = "dataCriacao")

    Tarefas paraTarefaEntity (TarefasDTO TarefasDTO);
    TarefasDTO paraTarefasDTO (Tarefas tarefas);
    List<Tarefas> paraListTarefa (List<TarefasDTO> TarefasDTO);
    List<TarefasDTO> paraListTarefaDTO (List<Tarefas> tarefas);
}



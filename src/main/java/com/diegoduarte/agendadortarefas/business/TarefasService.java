package com.diegoduarte.agendadortarefas.business;

import com.diegoduarte.agendadortarefas.business.dto.TarefasDTO;
import com.diegoduarte.agendadortarefas.business.mapper.TarefaConverter;
import com.diegoduarte.agendadortarefas.infrastructure.entity.Tarefas;
import com.diegoduarte.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.diegoduarte.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.diegoduarte.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefaConverter tarefaConverter;
    private final JwtUtil jwtUtil;

    public TarefasDTO gravarTarefa (String token, TarefasDTO tarefasDTO) {
        String email = jwtUtil.extractUsername(token.substring(7));
        tarefasDTO.setDataCriacao(LocalDateTime.now());
        tarefasDTO.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        tarefasDTO.setEmailUsuario(email);
        Tarefas tarefa = tarefaConverter.paraTarefaEntity(tarefasDTO);
        return tarefaConverter.paraTarefasDTO(
                tarefasRepository.save(tarefa));
    }

    public List<TarefasDTO> buscaListaDeTarefasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        return tarefaConverter.paraListTarefaDTO(
                tarefasRepository.findByDataEventoBetween(dataInicial, dataFinal));
    }

    public List<TarefasDTO> buscaTarefasPorEmail (String token){
        String email = jwtUtil.extractUsername(token.substring(7));
        List<Tarefas> listaTarefas = tarefasRepository.findByEmailUsuario(email);

        return tarefaConverter.paraListTarefaDTO(listaTarefas);
    }
}

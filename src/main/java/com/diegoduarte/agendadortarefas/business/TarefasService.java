package com.diegoduarte.agendadortarefas.business;

import com.diegoduarte.agendadortarefas.business.dto.TarefasDTO;
import com.diegoduarte.agendadortarefas.business.mapper.TarefaConverter;
import com.diegoduarte.agendadortarefas.business.mapper.TarefaUpdateConverter;
import com.diegoduarte.agendadortarefas.infrastructure.entity.Tarefas;
import com.diegoduarte.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.diegoduarte.agendadortarefas.infrastructure.exceptions.ResourceNotFoundException;
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
    private final TarefaUpdateConverter tarefaUpdateConverter;

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
                tarefasRepository.findByDataEventoBetweenAndStatusNotificacaoEnum(dataInicial, dataFinal,
                        StatusNotificacaoEnum.PENDENTE));
    }

    public List<TarefasDTO> buscaTarefasPorEmail (String token){
        String email = jwtUtil.extractUsername(token.substring(7));
        List<Tarefas> listaTarefas = tarefasRepository.findByEmailUsuario(email);

        return tarefaConverter.paraListTarefaDTO(listaTarefas);
    }

    public void deletaPorId (String id){
        try {
            tarefasRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao deletar tarefa: ID INEXISTENTE: " + id, e.getCause());
        }
    }

    public TarefasDTO alteraStatus (StatusNotificacaoEnum statusNotificacaoEnum, String id) {
        try {
            Tarefas tarefas = tarefasRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Tarefa não encontrada"));
            tarefas.setStatusNotificacaoEnum(statusNotificacaoEnum);
            return tarefaConverter.paraTarefasDTO(tarefasRepository.save(tarefas));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar status da tarefa", e.getCause());
        }
    }

    public TarefasDTO updateTarefas (TarefasDTO tarefasDTO, String id) {
        try {
            Tarefas tarefas = tarefasRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Tarefa não encontrada"));
            tarefaUpdateConverter.updateTarefas(tarefasDTO, tarefas);
            return tarefaConverter.paraTarefasDTO(tarefasRepository.save(tarefas));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar tarefa", e.getCause());
        }
    }
}

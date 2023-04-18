package br.com.alura.forum.service

import br.com.alura.forum.controller.AtualizacaoTopicoForm
import br.com.alura.forum.dto.NovoTopicoForm
import br.com.alura.forum.dto.TopicoView
import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.mapper.TopicoFormMappper
import br.com.alura.forum.mapper.TopicoViewMapper
import br.com.alura.forum.model.Topico
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class TopicoService(
    private var topicos: List<Topico> = ArrayList(),
    private val topicoViewMapper: TopicoViewMapper,
    private val topicoFormMapper: TopicoFormMappper,
    private val notFoundMessage: String = "O não foi possível localizar o registro"
) {
    fun listar(): List<TopicoView> {
        return topicos.stream().map { topico ->
            topicoViewMapper.map(topico)
        }.collect(Collectors.toList())
    }

    fun buscarPorId(id: Long): TopicoView {
        val topico = obterTopicoPeloId(id)
        return topicoViewMapper.map(topico)
    }

    fun cadastrar(novoTopicoForm: NovoTopicoForm): TopicoView {
        val topico = topicoFormMapper.map(novoTopicoForm)
        topico.id = topicos.size.toLong() + 1
        topicos = topicos.plus(topico)
        return topicoViewMapper.map(topico)
    }

    fun atualizar(atualizacaoTopicoForm: AtualizacaoTopicoForm): TopicoView {
        val topico = obterTopicoPeloId(atualizacaoTopicoForm.id)
        val topicoAtualizado = Topico(
            id = atualizacaoTopicoForm.id,
            titulo = atualizacaoTopicoForm.titulo,
            mensagem = atualizacaoTopicoForm.mensagem,
            autor = topico.autor,
            curso = topico.curso,
            respostas = topico.respostas,
            status = topico.status,
            dataCriacao = topico.dataCriacao
        )
        topicos = topicos.minus(topico).plus(topicoAtualizado)
        return topicoViewMapper.map(topicoAtualizado)
    }

    fun deletar(id: Long) {
        val topico = obterTopicoPeloId(id)
        topicos = topicos.minus(topico)
    }

    private fun obterTopicoPeloId(id: Long): Topico {
        val topico = topicos.stream().filter { topico ->
            topico.id == id
        }.findFirst().orElseThrow {NotFoundException(notFoundMessage)}
        return topico
    }
}
package br.com.alura.forum.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class NovoTopicoForm(
    @field: NotEmpty (message = "Titulo não pode ser em branco")
    @field: Size(min = 5, max = 100, message = "Titulo deve ter entre 5 e 100 caracteres ")
    val titulo: String,
    @field: NotEmpty (message = "Mensagem não pode ser em branco")
    val mensagem: String,
    @field: NotNull (message = "Id do curso não pode ser nula")
    val idCurso: Long,
    @field: NotNull(message = "Id do aluno não pode ser nula")
    val idAutor: Long,
)

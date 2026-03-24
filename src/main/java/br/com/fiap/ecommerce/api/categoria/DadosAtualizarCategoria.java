package br.com.fiap.ecommerce.api.categoria;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DadosAtualizarCategoria(
        Long id,

        @NotBlank //Não aceita nulos ou espaços em branco
        @Size(min = 3, max = 60) //define tamanho mínimo e/ou máximo
        @Column(unique=true)
        String nome,

        @Size(max = 255)
        String descricao
) {
}

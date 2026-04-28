package br.com.fiap.ecommerce.api.produto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record DadosAtualizarProduto(
        @NotNull
        Long id,

        @Size(min=3,max=100)
        String nome,

        @Positive
        @Digits(integer = 10, fraction = 2) //8 números antes da vírgula e 2 depois, total 10
        BigDecimal preco,

        @Pattern(regexp="^\\S+$", message = "SKU não pode conter espaços em branco")
        String sku,

        @Size(max=255)
        String descricao,

        @PositiveOrZero
        Integer estoque,

        Long categoriaId
) {
}

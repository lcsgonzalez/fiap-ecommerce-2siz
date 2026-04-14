package br.com.fiap.ecommerce.api.produto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/*
   nome: obrigatório, 3-100 caracteres
   preco: obrigatorio, maior que 0
   sku: obrigatório e único sistema, sem espaços em branco
   descricao: opcional, até 255 caracteres
   estoque: obrigatório, inteiro>0
   categoria: obrigatório, deve existir no banco
   * */
public record DadosCadastroProduto(
        @NotBlank
        @Size(min=3,max=100)
        String nome,

        @NotNull
        @Positive
        @Digits(integer = 10, fraction = 2) //8 números antes da vírgula e 2 depois, total 10
        BigDecimal preco,

        @NotBlank
        //No regex:
        // ^ = início da string
        // $ = final da string
        // \\S = Qualquer caracter que não espaço
        // + um ou mais caracteres
        @Pattern(regexp="^\\S+$", message = "SKU não pode conter espaços em branco")
        String sku,

        @Size(max=255)
        String descricao,

        @NotNull
        @PositiveOrZero
        int estoque,

        @NotNull
        Long categoriaId
) {
}

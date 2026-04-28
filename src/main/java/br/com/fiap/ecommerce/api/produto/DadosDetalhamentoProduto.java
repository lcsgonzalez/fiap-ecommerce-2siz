package br.com.fiap.ecommerce.api.produto;

import java.math.BigDecimal;

public record DadosDetalhamentoProduto(
        Long id,
        String nome,
        String sku,
        String descricao,
        BigDecimal preco,
        Integer estoque,
        String nomeCategoria
) {
    public DadosDetalhamentoProduto(Produto produto) {
        this(
                produto.getId(),
                produto.getNome(),
                produto.getSku(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getEstoque(),
                produto.getCategoria().getNome()
        );
    }
}

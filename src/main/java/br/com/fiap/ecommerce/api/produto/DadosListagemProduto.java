package br.com.fiap.ecommerce.api.produto;

import java.math.BigDecimal;

public record DadosListagemProduto(
        String nome,
        BigDecimal preco,
        Integer estoque,
        String nomeCategoria
) {
    public DadosListagemProduto(Produto produto){
        this(
                produto.getNome(),
                produto.getPreco(),
                produto.getEstoque(),
                produto.getCategoria().getNome()
        );
    }
}

package br.com.fiap.ecommerce.api.produto;

import br.com.fiap.ecommerce.api.categoria.Categoria;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Table(name="produtos")
@Entity(name="Produto")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Produto {
    /*
    nome: obrigatório, 3-100 caracteres
    preco: obrigatorio, maior que 0
    sku: obrigatório e único sistema, sem espaços em branco
    descricao: opcional, até 255 caracteres
    estoque: obrigatório, inteiro>0
    categoria: obrigatório, deve existir no banco
    * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private BigDecimal preco;
    private String sku;
    private String descricao;
    private int estoque;
    private int ativo;

    @ManyToOne(fetch = FetchType.LAZY) //somente busca a categoria no BD, se for usar diretamente
    @JoinColumn(name = "categoria_id") //define a coluna de junção
    private Categoria categoria;

    public Produto(DadosCadastroProduto dados, Categoria categoria) {
        this.nome = dados.nome();
        this.preco = dados.preco();
        this.sku = dados.sku();
        this.descricao = dados.descricao();
        this.estoque = dados.estoque();
        this.categoria = categoria;
        this.ativo = 1;
    }

    public void excluirProduto() {
        this.ativo = 0;
    }

    public void atualizarProduto(DadosAtualizarProduto dados, Categoria categoria) {
        if (dados.nome() != null && !dados.nome().isBlank()) {
            this.nome = dados.nome();
        }
        if (dados.preco() != null) {
            this.preco = dados.preco();
        }
        if (dados.sku() != null && !dados.sku().isBlank()) {
            this.sku = dados.sku();
        }
        if (dados.descricao() != null && !dados.descricao().isBlank()) {
            this.descricao = dados.descricao();
        }
        if (dados.estoque()!=null) {
            this.estoque = dados.estoque();
        }
        if (categoria != null) {
            this.categoria = categoria;
        }
    }
}

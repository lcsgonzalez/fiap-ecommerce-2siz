package br.com.fiap.ecommerce.api.controller;

import br.com.fiap.ecommerce.api.categoria.Categoria;
import br.com.fiap.ecommerce.api.categoria.CategoriaRepository;
import br.com.fiap.ecommerce.api.produto.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("produtos")
public class ProdutoController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoProduto> cadastrarProduto(
            @RequestBody @Valid DadosCadastroProduto dados) {
        //1. Validar se categoria existe
        var categoria = categoriaRepository.findByIdAndAtivoTrue(dados.categoriaId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));
        //2. Validar se SKU não é repetido
        if(produtoRepository.existsBySku(dados.sku())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "SKU já cadastrado no sistema");
        }
        //3. Cadastrar produto
        Produto produto = new Produto(dados,categoria);
        produtoRepository.save(produto);
        //4. Devolver o produto criado
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new DadosDetalhamentoProduto(produto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoProduto> buscarProdutoPorId(@PathVariable Long id){
        var produto = produtoRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
        return ResponseEntity.ok(new DadosDetalhamentoProduto(produto));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemProduto>> listraProdutos(
            @PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
        var page = produtoRepository.findAllByAtivoTrue(paginacao)
                .map(DadosListagemProduto::new);
        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletarProduto(@PathVariable  Long id){
        var produto = produtoRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

        produto.excluirProduto();

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoProduto> atualizarProduto(
            @RequestBody @Valid DadosAtualizarProduto dados
    ){
        //1. Buscar produto e checar ativo
        var produto = produtoRepository.findByIdAndAtivoTrue(dados.id())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
        System.out.println('1');
        //2. Verificar se SKU é único
        if(produtoRepository.existsBySku(dados.sku())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "SKU já cadastrado no sistema");
        }
        System.out.println('2');
        //3. Validar se categoria existe
        Categoria categoria = null;
        if(dados.categoriaId()!=null) {
            categoria = categoriaRepository.findByIdAndAtivoTrue(dados.categoriaId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));
            System.out.println('3');
        }
        //4. Atualizar produto
        produto.atualizarProduto(dados,categoria);
        System.out.println('4');
        //5. Retornar poduto atualizado
        return ResponseEntity.ok(new DadosDetalhamentoProduto(produto));
    }

}
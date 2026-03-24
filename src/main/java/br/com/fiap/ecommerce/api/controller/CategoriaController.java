package br.com.fiap.ecommerce.api.controller;

import br.com.fiap.ecommerce.api.categoria.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //define que esta classe é um controller REST
@RequestMapping("categorias") //define o caminho que recebrá as requisições
public class CategoriaController {

    @Autowired //spring instancia o objeto para nós
    private CategoriaRepository categoriaRepository;

    @Transactional //rollback no banco em caso de erros
    @PostMapping //recebe as requisições do tipo POST
    //Recebe o conteúdo de Body e garante que é válido conforme as retrições do DTO
    public void cadastrarCategoria(@RequestBody @Valid DadosCadastroCategoria dados){
        categoriaRepository.save(new Categoria(dados));
    }

    @GetMapping //recebe as requisições do tipo GET
    //Importar Page e Pageable do spring
    public Page<DadosListagemCategoria> listarCategorias(@PageableDefault(size=10, sort={"nome"}) Pageable paginacao){
         return categoriaRepository.findAllByAtivoTrue(paginacao)
                 .map(DadosListagemCategoria::new);
    }

    @GetMapping("/{id}")
    public DadosDetalhamentoCategoria buscarPorId(@PathVariable Long id){
        var categoria = categoriaRepository.getReferenceById(id);
        return new DadosDetalhamentoCategoria(categoria);
    }

    @PutMapping
    @Transactional
    public void atualizarCategoria(@RequestBody @Valid DadosAtualizarCategoria dados){
        var categoria = categoriaRepository.getReferenceById(dados.id());
        categoria.atualizarCategoria(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deletarCategoria(@PathVariable Long id){
//        categoriaRepository.deleteById(id); //Deleta de verdade
        var categoria = categoriaRepository.getReferenceById(id);
        categoria.excluirCategoria();
    }
}

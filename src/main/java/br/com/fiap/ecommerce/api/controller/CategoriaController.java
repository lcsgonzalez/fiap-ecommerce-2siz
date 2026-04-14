package br.com.fiap.ecommerce.api.controller;

import br.com.fiap.ecommerce.api.categoria.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController //define que esta classe é um controller REST
@RequestMapping("categorias") //define o caminho que recebrá as requisições
public class CategoriaController {

    @Autowired //spring instancia o objeto para nós
    private CategoriaRepository categoriaRepository;

    @Transactional //rollback no banco em caso de erros
    @PostMapping //recebe as requisições do tipo POST
    //Recebe o conteúdo de Body e garante que é válido conforme as retrições do DTO
    public ResponseEntity cadastrarCategoria(@RequestBody @Valid DadosCadastroCategoria dados, UriComponentsBuilder uriBuilder){
        var categoria = new Categoria(dados); //cria uma categoria baseada nos dados do body
        categoriaRepository.save(categoria); //salva a categoria no BD

        //Cria um endereço de rota contendo o id da categoria recém-criada
        var uri = uriBuilder.path("/categorias/{id}").buildAndExpand(categoria.getId()).toUri();
        //devolve um objeto de response e a categoria criada como body
        return ResponseEntity.created(uri).body(new DadosDetalhamentoCategoria(categoria));
    }

    @GetMapping //recebe as requisições do tipo GET
    //Importar Page e Pageable do spring
    public ResponseEntity<Page<DadosListagemCategoria>> listarCategorias(@PageableDefault(size=10, sort={"nome"}) Pageable paginacao){
         var page = categoriaRepository.findAllByAtivoTrue(paginacao)
                 .map(DadosListagemCategoria::new);
         return ResponseEntity.ok(page); //retorno de status 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity buscarPorId(@PathVariable Long id){
        Categoria categoria = categoriaRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Categoria não existe"
                )); //retorna erro de categoria não existe, caso não exista ou esteja inativa
        return ResponseEntity.ok(new DadosDetalhamentoCategoria(categoria)); //retorno de status 200 OK
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizarCategoria(@RequestBody @Valid DadosAtualizarCategoria dados){
        var categoria = categoriaRepository.getReferenceById(dados.id());
        categoria.atualizarCategoria(dados);
        //retorna status 200 OK e todos os dados da categoria modificada
        return ResponseEntity.ok(new DadosDetalhamentoCategoria(categoria));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletarCategoria(@PathVariable Long id){
//        categoriaRepository.deleteById(id); //Deleta de verdade
        var categoria = categoriaRepository.getReferenceById(id);
        categoria.excluirCategoria();

        return ResponseEntity.noContent().build(); //retorna status 204 No content
    }
}

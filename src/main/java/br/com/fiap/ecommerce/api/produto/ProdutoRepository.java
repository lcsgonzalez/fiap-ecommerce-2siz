package br.com.fiap.ecommerce.api.produto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Page<Produto> findAllByAtivoTrue(Pageable paginacao);
    boolean existsBySku(String sku);

    Optional<Produto> findByIdAndAtivoTrue(Long id);
}

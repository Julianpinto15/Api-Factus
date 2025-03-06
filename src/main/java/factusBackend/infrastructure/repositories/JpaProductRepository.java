package factusBackend.infrastructure.repositories;

import factusBackend.domain.model.Product;
import factusBackend.domain.repositories.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaProductRepository extends JpaRepository<Product, Long>, ProductRepository {
    @Override
    Optional<Product> findByCode(String code);
}
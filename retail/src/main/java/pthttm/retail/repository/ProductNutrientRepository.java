package pthttm.retail.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pthttm.retail.model.ProductNutrient;

@Repository
public interface ProductNutrientRepository extends JpaRepository<ProductNutrient, Long> {
    Page<ProductNutrient> findByNutrientId(String nutrientId, Pageable pageable);
}

package pthttm.retail.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pthttm.retail.model.Nutrient;
import pthttm.retail.model.ProductNutrient;

@Repository
public interface NutrientRepository extends JpaRepository<Nutrient, String> {

}

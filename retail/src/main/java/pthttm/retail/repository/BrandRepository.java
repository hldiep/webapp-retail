package pthttm.retail.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pthttm.retail.model.Brand;

@Repository
public interface BrandRepository extends CrudRepository <Brand,String> {
}

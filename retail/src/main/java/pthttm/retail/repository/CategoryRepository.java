package pthttm.retail.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pthttm.retail.model.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category,String> {


}

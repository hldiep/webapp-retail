package pthttm.retail.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pthttm.retail.model.Unit;

@Repository
public interface UnitRepository extends CrudRepository<Unit,String> {
}

package pthttm.retail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pthttm.retail.model.Customer;
import pthttm.retail.model.OrderProduct;

import java.util.List;
@Repository
public interface OrderRepository extends JpaRepository<OrderProduct,String> {
    List<OrderProduct> findByPayStatus(String payStatus);
    List<OrderProduct> findByShipStatus(String shipStatus);
    List<OrderProduct> findByPayStatusAndShipStatus(String payStatus, String shipStatus);
    List<OrderProduct> findByCustomer(Customer customer);

    @Query(value = "EXEC getNextOrderProductSequence", nativeQuery = true)
    long getNextSequenceValue();
}

package pthttm.retail.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pthttm.retail.model.OrderItem;
import pthttm.retail.model.Product;

import java.util.List;
@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem,Integer> {
    @Query("SELECT oi.product.id, COUNT(oi.product.id) AS product_count " +
            "FROM OrderItem oi " +
            "GROUP BY oi.product.id " +
            "ORDER BY product_count DESC")
    List<Object[]> findBestSellingProducts(Pageable pageable);

    @Query("SELECT oi.product FROM OrderItem oi GROUP BY oi.product HAVING SUM(oi.quantity) >= 2")
    List<Product> findPopularProducts();
}

package pthttm.retail.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pthttm.retail.model.Brand;
import pthttm.retail.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, String> {
    @EntityGraph(attributePaths = {"category", "unit", "brand"})
    Iterable<Product> findAll();
    Product findProductById(String id);
    List<Product> findByQuantity(int quantity);

    List<Product> findByNameContaining(String name);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> findByNameContainingKeyword(@Param("keyword") String keyword);

    List<Product> findByIdContaining(String id);

    @Query("SELECT p FROM Product p WHERE p.category.id = (SELECT p2.category.id FROM Product p2 WHERE p2.id = :productId) AND p.id <> :productId")
    List<Product> findSimilarProducts(@Param("productId") String productId);

    List<Product> findAllByOrderByCreateAtDesc();

    List<Product> findAllByCategoryId(String categoryId, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.flag = false ORDER BY p.quantity DESC")
    List<Product> findTopHotProductsByQuantity();

    @Query("SELECT p FROM Product p " +
            "JOIN OrderItem oi ON p.id = oi.product.id " +
            "GROUP BY p.id " +
            "ORDER BY COUNT(oi) DESC")
    List<Product> findTop10BestSellingProducts();

    @Query("SELECT p FROM Product p WHERE p.flag = false ORDER BY p.createAt DESC")
    List<Product> findTop10MostRecentProducts();

    List<Product> findByCategory_Id(String categoryId);

    @Query(value = "EXEC getNextProductSequence", nativeQuery = true)
    Integer getNextSequenceValue();
}

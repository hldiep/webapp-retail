package pthttm.retail.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pthttm.retail.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Optional<Customer>  findCustomerByPhone(String phone);
    Optional<Customer> findCustomerByEmail(String email);
    Optional<Customer> findCustomerByEmailOrPhone(String email,String phone);
    boolean existsByPhone(String phone);
    boolean existsByEmail(String email);
    @Query("SELECT c FROM Customer c WHERE CONCAT(c.firstName, ' ', c.lastName) LIKE %:name%")
    List<Customer> findByName(@Param("name") String name);
}

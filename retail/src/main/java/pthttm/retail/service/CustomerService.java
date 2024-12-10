package pthttm.retail.service;

import pthttm.retail.model.Customer;
import pthttm.retail.model.Product;
import pthttm.retail.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomer(){
        return customerRepository.findAll();
    }

    public Customer getCustomerById(int customerId){
        return customerRepository.findById(customerId).orElse(null);
    }

    public Customer getByPhone(String phone){
        return customerRepository.findCustomerByPhone(phone).orElse(null);
    }

    public Customer getByEmail(String email){
        return customerRepository.findCustomerByEmail(email).orElse(null);
    }
    public Customer getByEmailOrPhone(String username){
        return customerRepository.findCustomerByEmailOrPhone(username,username).orElse(null);
    }

    public boolean existsEmail(String email) {
        return customerRepository.existsByEmail(email);
    }
    public boolean existsPhone(String phone) {
        return customerRepository.existsByPhone(phone);
    }
//    public Customer authentication(String phone, String email, String password){
//        if(phone!=null)
//            return customerRepository.findByPhoneAndPassword(phone,password).orElse(null);
//        if(email!=null)
//            return customerRepository.findByEmailAndPassword(email,password).orElse(null);
//        return null;
//    }

    public void addCustomer(Customer customer){
        customerRepository.save(customer);
    }
}

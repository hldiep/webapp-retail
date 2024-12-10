package pthttm.retail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pthttm.retail.model.Customer;
import pthttm.retail.model.CustomerDetails;
import pthttm.retail.repository.CustomerRepository;

@Service
public class CustomerDetailsService implements UserDetailsService {

    private final CustomerService  customerService;
    private final CustomerRepository customerRepository;

    public CustomerDetailsService(CustomerService customerService, CustomerRepository customerRepository) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerService.getByEmailOrPhone(username);
        if(customer==null)
            throw new UsernameNotFoundException("Sai số điện thoại hoặc email.");

        return new CustomerDetails(customer);
    }

    public Customer getAuthenticatedCustomer() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated())
                return null;

            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomerDetails) {
                Customer customer = ((CustomerDetails) principal).getCustomer();
                return customerRepository.findById(customer.getId()).orElse(null);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

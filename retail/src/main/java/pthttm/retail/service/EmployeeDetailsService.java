package pthttm.retail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pthttm.retail.model.Customer;
import pthttm.retail.model.Employee;
import pthttm.retail.model.EmployeeDetails;
import pthttm.retail.repository.CustomerRepository;
import pthttm.retail.repository.EmployeeRepository;

@Service
public class EmployeeDetailsService implements UserDetailsService {

    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;

    public EmployeeDetailsService(EmployeeService employeeService, EmployeeRepository employeeRepository) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = null;
        if(username.contains("@")){
            employee = employeeService.getByEmail(username);
        }else{
            employee = employeeService.getByPhone(username);
        }
        if (employee ==null)
            throw new UsernameNotFoundException("Sai số điện thoại hoặc email");

        return new EmployeeDetails(employee);
    }

    public Employee getAuthenticatedEmployee() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated())
                return null;

            Object principal = authentication.getPrincipal();
            if (principal instanceof EmployeeDetails) {
                Employee employee = ((EmployeeDetails) principal).getEmployee();
                return employeeRepository.findById(employee.getId()).orElse(null);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

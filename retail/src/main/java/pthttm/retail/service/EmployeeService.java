package pthttm.retail.service;

import org.springframework.stereotype.Service;
import pthttm.retail.model.Employee;
import pthttm.retail.repository.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;


    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee getEmployeeById(String id){
        return employeeRepository.findById(id).orElse(null);
    }

    public Employee getByPhone(String phone){
        return employeeRepository.findByPhone(phone).orElse(null);
    }

    public Employee getByEmail(String email){
        return employeeRepository.findByEmail(email).orElse(null);
    }

    public List<Employee> getAllByIdNot(String id){
        return employeeRepository.findAllByIdNot(id);
    }
}

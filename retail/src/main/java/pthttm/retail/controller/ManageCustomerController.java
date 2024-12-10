package pthttm.retail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pthttm.retail.model.Customer;
import pthttm.retail.model.OrderProduct;
import pthttm.retail.service.CustomerService;
import pthttm.retail.service.OrderService;

import java.util.List;

@Controller
public class ManageCustomerController {
    private final CustomerService customerService;
    private final OrderService orderService;
    @Autowired
    public ManageCustomerController(CustomerService customerService,OrderService orderService){
        this.customerService= customerService;
        this.orderService =orderService;
    }

    @GetMapping("manage/customer")
    public String getAccount(Model model){
        List<Customer> customers = customerService.getAllCustomer();
        model.addAttribute("customers",customers);
        return "manage/page-manage-account";
    }

    @GetMapping("manage/detail-customer/{id}")
    public String updateProduct(Model model,@PathVariable("id") String customerId){
        Customer customer = customerService.getCustomerById(Integer.parseInt(customerId));
        List<OrderProduct> orders = (List<OrderProduct>) customer.getOrders();
        model.addAttribute("customer",customer);
        model.addAttribute("orders",orders);
        return "manage/page-manage-detail-account";
    }
}

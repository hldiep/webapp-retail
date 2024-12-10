package pthttm.retail.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import pthttm.retail.model.*;
import pthttm.retail.service.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("cartItems")
public class CheckoutController {

	private final ProductService productService;

	private final OrderService orderService;

	private final CustomerDetailsService customerDetailsService;

	public CheckoutController(ProductService productService, OrderService orderService, CustomerDetailsService customerDetailsService) {
        this.productService = productService;
        this.orderService = orderService;
        this.customerDetailsService = customerDetailsService;
    }

	@GetMapping("customer/thanh-toan")
	public String thanhToan(Model model) {
		// Lấy giỏ hàng từ model (tự động Spring sẽ quản lý session)
		List<CartItem> cartItems = (List<CartItem>) model.getAttribute("cartItems");

		if (cartItems == null) {
			cartItems = new ArrayList<>();  // Nếu giỏ hàng trống thì tạo danh sách trống
			model.addAttribute("cartItems", cartItems);  // Lưu giỏ hàng vào model (Spring sẽ quản lý session)
			return "redirect:/customer/gio-hang";
		}

		// Tính tổng tiền giỏ hàng
		double grandTotal = cartItems.stream()
				.mapToDouble(item -> item.getPrice() * item.getQuantity())
				.sum();
		model.addAttribute("grandTotal", grandTotal);

		// Tạo một đối tượng hóa đơn mới để liên kết với form
		Invoice invoice = new Invoice();
		Customer customer = customerDetailsService.getAuthenticatedCustomer();
		if(customer!=null){
			invoice.setAddress(customer.getAddress());
			invoice.setName(customer.getName());
			invoice.setPhone(customer.getPhone());
		}

		model.addAttribute("invoice", invoice);

		return "thanh-toan";  // Chuyển về trang thanh toán
	}

	// Post mapping để xử lý thanh toán
	@PostMapping("customer/thanh-toan")
	public String placeOrder(@RequestParam String name,
							 @RequestParam String address, @RequestParam String phone,
							 @RequestParam String notes, @RequestParam double grandTotal,
							 Model model) {
		//Lấy thông tin khách hàng đã login
		Customer customer = customerDetailsService.getAuthenticatedCustomer();
		if(customer==null) return "error";
		//Tạo đơn hàng mới
		//Lấy thông tin trong cartItems để đưa vào OrderItems
		List<CartItem> cartItems = (List<CartItem>) model.getAttribute("cartItems");
		if (cartItems == null || cartItems.isEmpty()) {
			return "redirect:/customer/gio-hang";  //Nếu giỏ hàng trống, chuyển hướng về trang giỏ hàng
		}

		boolean createSuccess = createOrder(cartItems,address,customer);
		if(!createSuccess) return "error";

		// Xóa giỏ hàng sau khi thanh toán
		model.addAttribute("cartItems", new ArrayList<>()); // Giỏ hàng sẽ được reset sau khi thanh toán
		return "redirect:/customer/purchase-history";  // Chuyển hướng về trang chủ sau khi thanh toán
	}

	private boolean createOrder(List<CartItem> cartItems, String address, Customer customer) {

		if(cartItems==null || cartItems.isEmpty() || customer==null) return false;

		OrderProduct orderProduct = new OrderProduct();
		List<OrderItem> orderItems = new ArrayList<>();
		long totalCost = 0;

		// Lưu thông tin các sản phẩm trong đơn hàng
		for (CartItem cartItem : cartItems) {
			// Fetch the Product entity
			System.out.println(cartItem.getProductId());
			Product product = productService.getProductById(cartItem.getProductId());
			if (product == null) {
				System.err.println("Sản phẩm không tồn tại.");
				return false;
			}
			// Tạo OrderItem và thêm vào danh sách
			OrderItem orderItem = new OrderItem(product, cartItem.getQuantity());
			orderItems.add(orderItem);
			totalCost += orderItem.getTotalCost();
		}

		orderProduct.setCustomer(customer);
		orderProduct.setTotalCost(totalCost);
		orderProduct.setPayStatus("CH");
		orderProduct.setShipStatus("CB");
		if(address.isBlank())
			orderProduct.setAddress(customer.getAddress());
		else
			orderProduct.setAddress(address);

		try {
			orderService.addOrder(orderProduct,orderItems);
		} catch (Exception e) {
			System.err.println("Error occurred while saving the order: " + e.getMessage());
			return false;  // Handle failure in order creation
		}
		return true;
	}
}

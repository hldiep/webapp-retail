package pthttm.retail.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pthttm.retail.model.CartItem;
import pthttm.retail.service.CartItemService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
@Controller
//@RequestMapping("/gio-hang")
public class CartController {
    private final CartItemService cartItemService;

    public CartController(CartItemService cartItemService) {
        this.cartItemService=cartItemService;
    }

    @GetMapping("customer/gio-hang")
    public String getGioHang(HttpSession session, Model model) {
        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
        model.addAttribute("cartItems", cartItems);
        double grandTotal = cartItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
        model.addAttribute("grandTotal", grandTotal);
        int totalItems = cartItems.stream().mapToInt(CartItem::getQuantity).sum();
        model.addAttribute("totalItems", totalItems);
        return "gio-hang";
    }

    @GetMapping("customer/gio-hang/add")
    public String addItem(@RequestParam String productId,
                          @RequestParam String productName,
                          @RequestParam Double price,
                          @RequestParam Integer quantity,
                          HttpSession session) {
        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
        boolean exists = false;
        for (CartItem item : cartItems) {
            if (item.getProductId().equals(productId)) {
                item.setQuantity(item.getQuantity() + quantity);
                exists = true;
                break;
            }
        }
        if (!exists) {
            CartItem newItem = new CartItem(productId, productName, (long)price.doubleValue(), quantity);
            cartItems.add(newItem);
        }
        session.setAttribute("cartItems", cartItems);
        System.out.println("Đã thêm sản phẩm: " + productName + ", Số lượng: " + quantity);
        return "redirect:/customer/gio-hang"; // Chuyển hướng đến trang giỏ hàng
    }

    @PostMapping("customer/cart/update")
    public String updateCart(@RequestParam Map<String, String> quantities, HttpSession session) {
        // Cập nhật giỏ hàng
        @SuppressWarnings("unchecked")
        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
        if(cartItems==null) return "gio-hang";
        //List<CartItem> cartItems=null;
        for (Map.Entry<String, String> entry : quantities.entrySet()) {
            String productId = entry.getKey(); // Product ID
            int quantity = Integer.parseInt(entry.getValue());
            for (CartItem item : cartItems) {
                if (item.getProductId().equals(productId)) {
                    item.setQuantity(quantity);
                }
            }
        }
        //Xóa các bản ghi không có trong quantities
        cartItems.removeIf(item -> !quantities.containsKey(item.getProductId()));
        session.setAttribute("cartItems", cartItems);
        return "gio-hang";
    }
//    @PostMapping("/gio-hang/remove")
//    public String removeItem(@RequestParam String productId) {
//        cartItemService.removeItem(productId);
//        return "redirect:/gio-hang";
//    }

    @GetMapping("customer/gio-hang/remove")
    public String removeItem(@RequestParam String productId, HttpSession session){
        List<CartItem> cartItems=(List<CartItem>) session.getAttribute("cartItems");
        if(cartItems!=null){
            Iterator<CartItem> iterator=cartItems.iterator();
            while(iterator.hasNext()){
                CartItem item = iterator.next();
                if(item.getProductId().equals(productId)){
                    iterator.remove();
                    break;
                }
            }
        }
        session.setAttribute("cartItems", cartItems);
        return "redirect:/customer/gio-hang";
    }
}
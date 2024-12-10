package pthttm.retail.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pthttm.retail.model.OrderProduct;
import pthttm.retail.service.OrderService;

import java.util.Collections;
import java.util.Map;

@Controller
public class ManageDeliveryController {
    private final OrderService orderService;

    public ManageDeliveryController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("manage/delivery")
    public String getDelivery(Model model){
        model.addAttribute("orders",orderService.getAllOrder());
        return "manage/page-manage-delivery";
    }

    @GetMapping("manage/delivery/{id}")
    public String getDeliveryDetail(Model model,@PathVariable("id") String orderId){
        model.addAttribute("order",orderService.getOrderById(orderId));
        return "manage/page-manage-detail-delivery";
    }
    @PostMapping("/manage/update-payment-status/{orderId}")
    public ResponseEntity<?> updatePayStatus(@PathVariable String orderId,
                                             @RequestBody Map<String, String> body){
        String newPayStatus = body.get("payStatus");
        if (newPayStatus == null || newPayStatus.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid payment status.");
        }
        try {
            OrderProduct order = orderService.getOrderById(orderId);
            if (order == null) {
                return ResponseEntity.notFound().build();
            }
            order.setPayStatus(newPayStatus);
            orderService.saveOrder(order);
            return ResponseEntity.ok(Collections.singletonMap("success", true));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error updating payment status.");
        }
    }
    @PostMapping("/manage/update-shipping-status/{orderId}")
    public ResponseEntity<?> updateShipStatus(@PathVariable String orderId,
                                             @RequestBody Map<String, String> body){
        String newShipStatus = body.get("shipStatus");
        if (newShipStatus == null || newShipStatus.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid payment status.");
        }
        try {
            OrderProduct order = orderService.getOrderById(orderId);
            if (order == null) {
                return ResponseEntity.notFound().build();
            }
            order.setShipStatus(newShipStatus);
            orderService.saveOrder(order);
            return ResponseEntity.ok(Collections.singletonMap("success", true));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error updating payment status.");
        }
    }
}

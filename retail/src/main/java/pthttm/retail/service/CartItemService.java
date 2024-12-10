package pthttm.retail.service;

import org.springframework.stereotype.Service;
import pthttm.retail.model.CartItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CartItemService {
    private ArrayList<CartItem> cartItems = new ArrayList<>();
    public ArrayList<CartItem> getAllCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
    // Method thêm một sản phẩm vào giỏ hàng
    public void addCartItem(CartItem newCartItem) {
        cartItems.add(newCartItem);
    }
    public void addItem(CartItem item){
        for(CartItem cartItem:cartItems){
            if (cartItem.getProductId().equals(item.getProductId())) {
                cartItem.setQuantity(cartItem.getQuantity()+item.getQuantity());
                return;
            }
        }
        cartItems.add(item);
    }
    public void removeItem(String productId) {
        cartItems.removeIf(item -> item.getProductId().equals(productId));
    }
    public Long getGrandTotal() {
        double sum = cartItems.stream().mapToDouble(CartItem::getTotalAmount).sum();
        return Math.round(sum);
    }
}
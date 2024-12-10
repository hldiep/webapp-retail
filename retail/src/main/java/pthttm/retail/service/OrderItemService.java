package pthttm.retail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pthttm.retail.model.OrderItem;
import pthttm.retail.repository.OrderItemRepository;

@Service
public class OrderItemService {
    @Autowired
    OrderItemRepository orderItemRepository;

    public void save(OrderItem orderItem){
        orderItemRepository.save(orderItem);
    }
}

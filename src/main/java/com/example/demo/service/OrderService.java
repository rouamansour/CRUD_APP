package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order saveOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        System.out.println("Saved Order: " + savedOrder);
        return savedOrder;
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public Order updateOrder(Long id, Order orderDetails) {
        Order existingOrder = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("order not found with id " + id));

        existingOrder.setProduct(orderDetails.getProduct());
        existingOrder.setAmount(orderDetails.getAmount());
        existingOrder.setUser(orderDetails.getUser());

        return orderRepository.save(existingOrder);
    }
}

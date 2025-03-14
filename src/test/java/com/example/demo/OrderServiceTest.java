package com.example.demo;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private User user;

    @BeforeEach
    void setUp() {
        // Initialisation des mocks
        MockitoAnnotations.openMocks(this);

        user = new User(1L, "John", "john@example.com");
        order = new Order(1L, "Product 1", 100.0, user);
    }

    @Test
    void testGetAllOrders() {
        List<Order> orders = Arrays.asList(order);
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAllOrders();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Product 1", result.get(0).getProduct());
    }

    @Test
    void testGetOrderById() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Optional<Order> result = orderService.getOrderById(1L);

        assertTrue(result.isPresent());
        assertEquals("Product 1", result.get().getProduct());
    }

    @Test
    void testGetOrderByIdNotFound() {
        when(orderRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Order> result = orderService.getOrderById(2L);

        assertFalse(result.isPresent());
    }

    @Test
    void testDeleteOrder() {
        orderService.deleteOrder(1L);

        verify(orderRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdateOrder() {
        Order updatedOrder = new Order(1L, "Updated Product", 150.0, user);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(updatedOrder);

        Order result = orderService.updateOrder(1L, updatedOrder);

        assertNotNull(result);
        assertEquals("Updated Product", result.getProduct());
        assertEquals(150.0, result.getAmount());
    }

    @Test
    void testUpdateOrderNotFound() {
        Order updatedOrder = new Order(2L, "Updated Product", 150.0, user);
        when(orderRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.updateOrder(2L, updatedOrder);
        });

        assertEquals("order not found with id 2", exception.getMessage());
    }
}

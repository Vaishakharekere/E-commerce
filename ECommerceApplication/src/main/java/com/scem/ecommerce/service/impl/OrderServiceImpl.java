package com.scem.ecommerce.service.impl;

import com.scem.ecommerce.dao.OrderRepository;
import com.scem.ecommerce.entity.*;
import com.scem.ecommerce.entity.enums.OrderStatus;
import com.scem.ecommerce.exception.EmptyCartException;
import com.scem.ecommerce.exception.OrderNotFoundException;
import com.scem.ecommerce.service.CartService;
import com.scem.ecommerce.service.OrderService;
import com.scem.ecommerce.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OrderServiceImpl implements OrderService {
	  @Autowired
    private final OrderRepository orderRepo;
	  @Autowired
    private final CartService cartService;
	  @Autowired
    private final AuthUtil authUtil;
    
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);


  
    public OrderServiceImpl(OrderRepository orderRepo,
                            CartService cartService,
                            AuthUtil authUtil) {
        this.orderRepo = orderRepo;
        this.cartService = cartService;
        this.authUtil = authUtil;
    }

    /**  User Operations  **/

    @Override
    public Order placeOrderForCurrentUser() {
        User user = authUtil.getCurrentUser();
        Cart cart = cartService.getCartByUserId(user.getUserId());

        if (cart.getCartItems().isEmpty()) {
            logger.warn("User {} attempted to place an order with empty cart", user.getEmail()); 
            throw new EmptyCartException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(cart.getTotalPrice());
        order.setOrderStatus(OrderStatus.PLACED);

        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setDiscount(cartItem.getDiscount());
            orderItem.setOrderedProductPrice(cartItem.getProductPrice()); 
            order.getOrderItems().add(orderItem);
        }

        logger.info("Order placed with total price: {}", order.getTotalAmount());
        logger.info("User {} placed order {}", user.getEmail(), order.getId());
        return orderRepo.save(order);
    }


    @Override
    public List<Order> getOrdersForCurrentUser() {
        User user = authUtil.getCurrentUser();
        return ((Collection<Order>) orderRepo.findAll())
                .stream()
                .filter(o -> o.getUser().getUserId().equals(user.getUserId()))
                .collect(Collectors.toList());
    }

    @Override
    public void cancelOrderForCurrentUser(Long orderId) {
        User user = authUtil.getCurrentUser();
        Order order = orderRepo.findById(orderId)
                .filter(o -> o.getUser().getUserId().equals(user.getUserId()))
                .orElseThrow(() -> new OrderNotFoundException("Order not found for current user"));
        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepo.save(order);
    }

    @Override
    public Order getOrderByIdForCurrentUser(Long id) {
        User user = authUtil.getCurrentUser();
        return orderRepo.findById(id)
                .filter(o -> o.getUser().getUserId().equals(user.getUserId()))
                .orElseThrow(() -> new OrderNotFoundException("Order not found for current user"));
    }

    /**  Admin Operations **/

    @Override
    public List<Order> getAllOrders() {
        return (List<Order>) orderRepo.findAll();
    }

    @Override
    public Order updateOrderStatus(Long orderId, String status) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        order.setOrderStatus(OrderStatus.valueOf(status.toUpperCase()));
        logger.info("Admin updated order {} status to {}", orderId, status);
        return orderRepo.save(order);
    }

    @Override
    public Order getOrderByIdAdmin(Long orderId) {
        return orderRepo.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }
}

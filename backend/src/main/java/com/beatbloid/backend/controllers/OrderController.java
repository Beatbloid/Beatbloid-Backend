package com.beatbloid.backend.controllers;

import com.beatbloid.backend.models.OrderModel;
import com.beatbloid.backend.services.OrderService;
import com.beatbloid.backend.utils.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create/{clientId}")
    public ResponseEntity<ResponseWrapper<OrderModel>> createOrder(@PathVariable Long clientId, @RequestBody OrderModel order) {
        OrderModel createdOrder = orderService.createOrder(clientId, order);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Order placed successfully", createdOrder));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<ResponseWrapper<List<OrderModel>>> getOrdersByClient(@PathVariable Long clientId) {
        List<OrderModel> orders = orderService.getOrdersByClient(clientId);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Orders fetched successfully", orders));
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseWrapper<List<OrderModel>>> getAllOrders() {
        List<OrderModel> allOrders = orderService.getAllOrders();
        return ResponseEntity.ok(new ResponseWrapper<>(200, "All orders fetched successfully", allOrders));
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<ResponseWrapper<OrderModel>> updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        OrderModel updatedOrder = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Order status updated successfully", updatedOrder));
    }
}

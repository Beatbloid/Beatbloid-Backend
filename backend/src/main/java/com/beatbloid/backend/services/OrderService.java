package com.beatbloid.backend.services;

import com.beatbloid.backend.models.OrderModel;
import com.beatbloid.backend.models.UserModel;
import com.beatbloid.backend.repositories.OrderRepository;
import com.beatbloid.backend.repositories.ClientRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;

    public OrderService(OrderRepository orderRepository, ClientRepository clientRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
    }

    public OrderModel createOrder(Long clientId, OrderModel order) {
        ClientModel client = userRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Client not found"));

        order.setClientOrder(client);
        return orderRepository.save(order);
    }

    public List<OrderModel> getOrdersByClient(Long clientId) {
        return orderRepository.findByClientOrder_ClientId(clientId);
    }

    public List<OrderModel> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public OrderModel updateOrderStatus(Long orderId, String newStatus) {
        Optional<OrderModel> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            throw new RuntimeException("Order not found");
        }

        OrderModel order = orderOptional.get();
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }
}

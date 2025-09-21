package com.beatbloid.backend.repositories;

import com.beatbloid.backend.models.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Long> {
    List<OrderModel> findByClientOrder_ClientId(Long clientId);
}

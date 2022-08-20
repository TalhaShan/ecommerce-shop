package com.ecommerce.shop.repository;

import com.ecommerce.shop.model.DeliverOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliverRepository extends JpaRepository<DeliverOrder,Integer> {

    DeliverOrder findByTrackingId(String trackID);
}

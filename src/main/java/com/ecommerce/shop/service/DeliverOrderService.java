package com.ecommerce.shop.service;

import antlr.StringUtils;
import com.ecommerce.shop.exceptions.ProductNotExistException;
import com.ecommerce.shop.model.DeliverOrder;
import com.ecommerce.shop.model.User;
import com.ecommerce.shop.repository.DeliverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeliverOrderService {

    @Autowired
    DeliverRepository deliverRepository;

    public void scheduleDelivery(User user, Integer orderId) {
        DeliverOrder deliverOrder = new DeliverOrder();
        deliverOrder.setOrderId(orderId);
        deliverOrder.setTrackingId(String.valueOf(UUID.randomUUID()));
        deliverOrder.setUserId(user.getId());
        deliverRepository.save(deliverOrder);
    }

    public DeliverOrder getDetails(String trackingId) throws ProductNotExistException{

        DeliverOrder deliverOrder = deliverRepository.findByTrackingId(trackingId);
        if(deliverOrder!=null){
            return deliverOrder;
        }else {
            throw new ProductNotExistException(" id is invalid ");
        }

    }
}

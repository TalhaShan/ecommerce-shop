package com.ecommerce.shop.controller;

import com.ecommerce.shop.exceptions.AuthenticationFailException;
import com.ecommerce.shop.model.DeliverOrder;
import com.ecommerce.shop.model.Product;
import com.ecommerce.shop.model.User;
import com.ecommerce.shop.response.ApiResponse;
import com.ecommerce.shop.service.DeliverOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deliver")
public class DeliverController {


    @Autowired
    DeliverOrderService deliverOrderService;

    @GetMapping("/deliver-details")
    public ResponseEntity<DeliverOrder> getDetails(@RequestParam("trackingId") String trackingId)
            throws AuthenticationFailException {

        DeliverOrder deliverOrder = deliverOrderService.getDetails(trackingId);
        return new ResponseEntity<DeliverOrder>(deliverOrder, HttpStatus.OK);
    }
}

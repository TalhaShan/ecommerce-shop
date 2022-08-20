package com.ecommerce.shop.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterProductRequest {
    int categoryId;
    String color;
    String brand;


}

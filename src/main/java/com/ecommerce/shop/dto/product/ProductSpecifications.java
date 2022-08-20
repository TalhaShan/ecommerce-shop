package com.ecommerce.shop.dto.product;

import com.ecommerce.shop.model.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {
    public static Specification<Product> getProductColor(String color) {
        if (color == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.equal(root.get("color"), color);
        };
    }

    public static Specification<Product> getBrand(String brand) {
        if (brand == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.equal(root.get("brand"), brand);
        };
    }

    public static Specification<Product> getCategoryId(Integer id) {
        if (id == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.equal(root.get("category"), id);
        };
    }
}

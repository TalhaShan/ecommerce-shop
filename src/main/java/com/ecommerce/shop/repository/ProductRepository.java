package com.ecommerce.shop.repository;


import com.ecommerce.shop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    //Page<Product> findByCategoryAndBrandAndColor(Integer id,String brand, String color);

}

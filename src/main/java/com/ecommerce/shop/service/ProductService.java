package com.ecommerce.shop.service;


import com.ecommerce.shop.dto.product.FilterProductRequest;
import com.ecommerce.shop.dto.product.ProductDto;
import com.ecommerce.shop.dto.product.ProductSpecifications;
import com.ecommerce.shop.exceptions.ProductNotExistException;
import com.ecommerce.shop.model.Category;
import com.ecommerce.shop.model.Product;
import com.ecommerce.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<ProductDto> listProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = getDtoFromProduct(product);
            productDtos.add(productDto);
        }
        return productDtos;
    }

    public static ProductDto getDtoFromProduct(Product product) {
        ProductDto productDto = new ProductDto(product);
        return productDto;
    }

    public static Product getProductFromDto(ProductDto productDto, Category category) {
        Product product = new Product(productDto, category);
        return product;
    }

    public void addProduct(ProductDto productDto, Category category) {
        Product product = getProductFromDto(productDto, category);
        productRepository.save(product);
    }

    public void updateProduct(Integer productID, ProductDto productDto, Category category) {
        Product product = getProductFromDto(productDto, category);
        product.setId(productID);
        productRepository.save(product);
    }


    public Product getProductById(Integer productId) throws ProductNotExistException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (!optionalProduct.isPresent())
            throw new ProductNotExistException("Product id is invalid " + productId);
        return optionalProduct.get();
    }

    public List<Product> getFilterProduct(FilterProductRequest request) throws ProductNotExistException {

        Specification<Product> specs = null;

        try {

            specs = Specification.where(ProductSpecifications.getProductColor(request.getColor()))
                    .and(ProductSpecifications.getBrand(request.getBrand()))
                    .and(ProductSpecifications.getCategoryId(request.getCategoryId()));
        } catch (Exception exc) {
            throw new ProductNotExistException("Product id is invalid ");
        }

        List<Product> products = productRepository.findAll(specs);


        return products;
    }

    public Page<Product> getFilterProductWithPagination(FilterProductRequest request, int page, int size,String sortDir) throws ProductNotExistException {
        // TODO: 8/20/2022  can handle optional parmas case later
        Specification<Product> specs = null;
        Pageable p = PageRequest.of(page, size);
        try {

            specs = Specification.where(ProductSpecifications.getProductColor(request.getColor()))
                    .and(ProductSpecifications.getBrand(request.getBrand()))
                    .and(ProductSpecifications.getCategoryId(request.getCategoryId()));
        } catch (Exception exc) {
            throw new ProductNotExistException("Product id is invalid ");
        }

        Page<Product> products = productRepository.findAll(specs,p);


        return products;
    }
}
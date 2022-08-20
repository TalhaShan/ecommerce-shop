package com.ecommerce.shop.controller;

import com.ecommerce.shop.dto.product.FilterProductRequest;
import com.ecommerce.shop.dto.product.ProductDto;
import com.ecommerce.shop.model.Category;
import com.ecommerce.shop.model.Product;
import com.ecommerce.shop.response.ApiResponse;
import com.ecommerce.shop.service.CategoryService;
import com.ecommerce.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> body = productService.listProducts();
        return new ResponseEntity<List<ProductDto>>(body, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductDto productDto) {
        Optional<Category> optionalCategory = categoryService.readCategory(productDto.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category is invalid"), HttpStatus.CONFLICT);
        }
        Category category = optionalCategory.get();
        productService.addProduct(productDto, category);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product has been added"), HttpStatus.CREATED);
    }

    @PostMapping("/update/{productID}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productID") Integer productID, @RequestBody @Valid ProductDto productDto) {
        Optional<Category> optionalCategory = categoryService.readCategory(productDto.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category is invalid"), HttpStatus.CONFLICT);
        }
        Category category = optionalCategory.get();
        productService.updateProduct(productID, productDto, category);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product has been updated"), HttpStatus.OK);
    }

    @GetMapping("/filter-product")
    public ResponseEntity<List<Product>> getFilterProducts(@RequestBody FilterProductRequest productRequest) {
        List<Product> body = productService.getFilterProduct(productRequest);
        return new ResponseEntity<List<Product>>(body, HttpStatus.OK);
    }

    @GetMapping("/filter-product-limited")
    public ResponseEntity<Page<Product>> getFilterProductsWithPagination(@RequestBody FilterProductRequest productRequest,
                                                                         @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                         @RequestParam(value = "size", required = false, defaultValue = "0") int size,
                                                                         @RequestParam(value = "sortDir", required = false, defaultValue = "DESC") String sortDir) {
        Page<Product> body = productService.getFilterProductWithPagination(productRequest,page,size,sortDir);
        return new ResponseEntity<Page<Product>>(body, HttpStatus.OK);
    }
}
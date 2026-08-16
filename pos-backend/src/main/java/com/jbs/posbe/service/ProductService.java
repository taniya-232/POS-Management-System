package com.jbs.posbe.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jbs.posbe.dto.request.ProductPatchDto;
import com.jbs.posbe.dto.request.ProductRequestDto;
import com.jbs.posbe.entity.Product;

public interface ProductService {
	
	Product saveProduct(ProductRequestDto dto);
	Page<Product> getProduct(Pageable pageable);
    Product getProductById(Long productId);
    List<Product> getProductsByCompanyId(Long companyId);
    Product updateProduct(Long productId, ProductPatchDto dto);
    void deleteProduct(Long productId);
}

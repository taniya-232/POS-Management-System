package com.jbs.posbe.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jbs.posbe.dto.request.ProductPatchDto;
import com.jbs.posbe.dto.request.ProductRequestDto;
import com.jbs.posbe.entity.Company;
import com.jbs.posbe.entity.Product;
import com.jbs.posbe.entity.Unit;
import com.jbs.posbe.repository.CompanyRepository;
import com.jbs.posbe.repository.ProductRepository;
import com.jbs.posbe.repository.UnitRepository;
import com.jbs.posbe.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	private final CompanyRepository companyRepository;
	
	private final UnitRepository unitRepository;

	@Override
	public Product saveProduct(ProductRequestDto dto) {

		Company company = companyRepository.findById(dto.getCompanyId())
				.orElseThrow(() -> new RuntimeException("Company not found"));
		Unit unit = unitRepository.findById(dto.getUnitId())
				.orElseThrow(() -> new RuntimeException("Unit not found"));

		Product product = new Product();

		product.setCompany(company);
		product.setUnit(unit);
		product.setPname(dto.getPname());
		product.setHsn(dto.getHsn());
		product.setGst(dto.getGst());
		product.setUnitPrice(dto.getUnitPrice());
		product.setStock(dto.getStock());

		if (dto.getActive() != null) {
			product.setActive(dto.getActive());
		}

		return productRepository.save(product);
	}

	@Override
	public Page<Product> getProduct(Pageable pageable) {
		if (pageable == null) {
			throw new IllegalArgumentException("Pageable parameter cannot be null");
		}
		return productRepository.findAll(pageable);
	}

	@Override
	public Product getProductById(Long productId) {

		return productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
	}

	@Override
	public List<Product> getProductsByCompanyId(Long companyId) {

		return productRepository.findByCompanyCompanyId(companyId);
	}

	@Override
	public Product updateProduct(Long productId, ProductPatchDto dto) {

		Product existingProduct = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found"));

		if (dto.getPname() != null) {
			existingProduct.setPname(dto.getPname());
		}

		if (dto.getHsn() != null) {
			existingProduct.setHsn(dto.getHsn());
		}

		if (dto.getGst() != null) {
			existingProduct.setGst(dto.getGst());
		}

		if (dto.getUnitPrice() != null) {
			existingProduct.setUnitPrice(dto.getUnitPrice());
		}

		if (dto.getStock() != null) {
			existingProduct.setStock(dto.getStock());
		}

		if (dto.getActive() != null) {
			existingProduct.setActive(dto.getActive());
		}

		if (dto.getCompanyId() != null) {

			Company company = companyRepository.findById(dto.getCompanyId())
					.orElseThrow(() -> new RuntimeException("Company not found"));

			existingProduct.setCompany(company);
		}
		
		if (dto.getUnitId() != null) {

			Unit unit = unitRepository.findById(dto.getUnitId())
					.orElseThrow(() -> new RuntimeException("Unit not found"));
			
			existingProduct.setUnit(unit);
		}

		return productRepository.saveAndFlush(existingProduct);
	}

	@Override
	public void deleteProduct(Long productId) {

		Product product = getProductById(productId);

		productRepository.delete(product);
	}
}

package com.jbs.posbe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jbs.posbe.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	List<Product> findByCompanyCompanyId(Long companyId);
}

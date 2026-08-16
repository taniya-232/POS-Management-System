package com.jbs.posbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jbs.posbe.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}

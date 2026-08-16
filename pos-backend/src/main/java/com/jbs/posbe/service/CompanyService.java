package com.jbs.posbe.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jbs.posbe.dto.request.CompanyPatchDto;
import com.jbs.posbe.entity.Company;

public interface CompanyService {
	
	Company saveCompany(Company company);
	Page<Company> getCompany(Pageable pageable);
	Company getCompanyById(Long companyId);
	Company updateCompany(Long companyId, CompanyPatchDto dto);
	void deleteCompany(Long companyId);

}

package com.jbs.posbe.service.impl;

import org.springframework.stereotype.Service;

import com.jbs.posbe.service.CompanyService;
import com.jbs.posbe.repository.CompanyRepository;
import com.jbs.posbe.dto.request.CompanyPatchDto;
import com.jbs.posbe.entity.Company;

import org.springframework.data.domain.Page;

import lombok.RequiredArgsConstructor;

import com.jbs.posbe.exception.ResourceNotFoundException;
import com.jbs.posbe.exception.ResourceDeletedException;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

	private final CompanyRepository companyRepository;

	@Override
	public Company saveCompany(Company company) 	{
		if (company == null) {
			throw new IllegalArgumentException("Company cannot be null");
		}
		return companyRepository.save(company);
	}

	@Override
	public Page<Company> getCompany(
			org.springframework.data.domain.Pageable pageable) {
		if (pageable == null) {
			throw new IllegalArgumentException("Pageable parameter cannot be null");
		}
		return companyRepository.findAll(pageable);
	}

	@Override
	public Company getCompanyById(java.lang.Long companyId) {
		Company company = companyRepository.findById(companyId)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Company not found with ID: " + companyId
	                    )
	            );

	    // Soft delete validation
	    if (company.isDeleted()) {

	        throw new ResourceDeletedException(
	                "Company has already been deleted with ID: " + companyId
	        );
	    }

	    return company;
	}

	@Override
	public Company updateCompany(Long companyId, CompanyPatchDto dto) {

	    Company existingCompany = companyRepository.findById(companyId)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Company not found with ID: " + companyId
	                    )
	            );

	    // Soft delete validation
	    if (existingCompany.isDeleted()) {

	        throw new ResourceDeletedException(
	                "Company already deleted with ID: " + companyId
	        );
	    }

	    // ==========================================
	    // PATCH ONLY PROVIDED FIELDS
	    // ==========================================

	    if (dto.getCname() != null) {
	        existingCompany.setCname(dto.getCname());
	    }

	    if (dto.getCabbr() != null) {
	        existingCompany.setCabbr(dto.getCabbr());
	    }

	    if (dto.getActive() != null) {
	        existingCompany.setActive(dto.getActive());
	    }

	    return companyRepository.save(existingCompany);
	}

	@Override
	public void deleteCompany(Long companyId) {

	    Company company = companyRepository.findById(companyId)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Company not found with ID: " + companyId
	                    )
	            );

	    // Already deleted check
	    if (company.isDeleted()) {

	        throw new ResourceDeletedException(
	                "Company has already been deleted with ID: " + companyId
	        );
	    }

	    // Soft delete
	    company.setDeleted(true);

	    companyRepository.save(company);
	}

}

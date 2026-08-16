package com.jbs.posbe.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jbs.posbe.dto.request.VendorPatchDto;
import com.jbs.posbe.dto.request.VendorRequestDto;
import com.jbs.posbe.dto.response.VendorResponseDto;
import com.jbs.posbe.entity.Company;
import com.jbs.posbe.entity.Vendor;
import com.jbs.posbe.repository.CompanyRepository;
import com.jbs.posbe.repository.VendorRepository;
import com.jbs.posbe.service.VendorService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    
    private final CompanyRepository companyRepository;

    @Override
    public VendorResponseDto saveVendor(VendorRequestDto dto) {

    	List<Company> companies = companyRepository.findAllById(dto.getCompanyIds());

        Vendor vendor = new Vendor();

        vendor.setCompanies(companies);

        vendor.setVname(dto.getVname());

        if (dto.getActive() != null) {
            vendor.setActive(dto.getActive());
        }

        Vendor savedVendor = vendorRepository.save(vendor);

        return convertToDto(savedVendor);
    }

    @Override
    public Page<VendorResponseDto> getVendors(Pageable pageable) {
        if (pageable == null) {
            throw new IllegalArgumentException("Pageable cannot be null");
        }
        return vendorRepository.findAll(pageable).map(this::convertToDto);
    }

    @Override
    public VendorResponseDto getVendorById(Long vendorId) {
    	Vendor vendor =
    	        vendorRepository.findById(vendorId)
    	        .orElseThrow(() ->
    	                new RuntimeException(
    	                        "Vendor not found"));
    	return convertToDto(vendor);
    }

    @Override
    public VendorResponseDto updateVendor(Long vendorId, VendorPatchDto dto) {

        Vendor vendor = getVendorEntity(vendorId);

        if (dto.getCompanyIds() != null) {

            List<Company> companies = companyRepository
            		.findAllById(dto.getCompanyIds());
            vendor.setCompanies(companies);
        }

        if (dto.getVname() != null) {
            vendor.setVname(dto.getVname());
        }

        if (dto.getActive() != null) {
            vendor.setActive(dto.getActive());
        }

        Vendor updatedVendor =
                vendorRepository
                        .saveAndFlush(vendor);

        return convertToDto(updatedVendor);
    }

    @Override
    public void deleteVendor(Long vendorId) {
        Vendor vendor = getVendorEntity(vendorId);
        vendorRepository.delete(vendor);
    }
    
    private VendorResponseDto convertToDto(Vendor vendor) {

        VendorResponseDto dto = new VendorResponseDto();

        dto.setVendorId(vendor.getVendorId());
        dto.setVname(vendor.getVname());
        dto.setActive(vendor.isActive());
        dto.setCreatedAt(vendor.getCreatedAt());
        dto.setUpdatedAt(vendor.getUpdatedAt());
        dto.setCompanyIds(
                vendor.getCompanies()
                        .stream()
                        .map(Company::getCompanyId)
                        .toList()
        );

        return dto;
    }
    
    private Vendor getVendorEntity(Long vendorId) {

        return vendorRepository
                .findById(vendorId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Vendor not found"));
    }
}
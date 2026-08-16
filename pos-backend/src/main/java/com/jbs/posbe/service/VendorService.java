package com.jbs.posbe.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jbs.posbe.dto.request.VendorPatchDto;
import com.jbs.posbe.dto.request.VendorRequestDto;
import com.jbs.posbe.dto.response.VendorResponseDto;
import com.jbs.posbe.entity.Vendor;

public interface VendorService {

	VendorResponseDto saveVendor(VendorRequestDto vendorRequestDto);
    Page<VendorResponseDto> getVendors(Pageable pageable);
    VendorResponseDto getVendorById(Long vendorId);
    VendorResponseDto updateVendor( Long vendorId, VendorPatchDto dto);
    void deleteVendor(Long vendorId);
}
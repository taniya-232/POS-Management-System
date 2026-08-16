package com.jbs.posbe.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jbs.posbe.dto.request.PurchaseMasterRequestDto;
import com.jbs.posbe.dto.request.PurchaseSubRequestDto;
import com.jbs.posbe.dto.response.PurchaseMasterResponseDto;
import com.jbs.posbe.dto.response.PurchaseSubResponseDto;
import com.jbs.posbe.entity.Company;
import com.jbs.posbe.entity.Product;
import com.jbs.posbe.entity.PurchaseMaster;
import com.jbs.posbe.entity.PurchaseSub;
import com.jbs.posbe.entity.Vendor;
import com.jbs.posbe.repository.CompanyRepository;
import com.jbs.posbe.repository.ProductRepository;
import com.jbs.posbe.repository.PurchaseMasterRepository;
import com.jbs.posbe.repository.VendorRepository;
import com.jbs.posbe.service.PurchaseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseMasterRepository purchaseMasterRepository;

    private final VendorRepository vendorRepository;

    private final CompanyRepository companyRepository;

    private final ProductRepository productRepository;

    // ==========================================================
    // SAVE PURCHASE
    // ==========================================================

    @Override
    @Transactional
    public PurchaseMasterResponseDto savePurchase(
            PurchaseMasterRequestDto dto) {

        if (purchaseMasterRepository
                .existsByInvoiceNo(dto.getInvoiceNo())) {

            throw new RuntimeException(
                    "Invoice number already exists");
        }

        Vendor vendor = vendorRepository.findById(
                dto.getVendorId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Vendor not found"));

        Company company = companyRepository.findById(
                dto.getCompanyId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Company not found"));

        validateVendorCompany(
                vendor,
                company);

        PurchaseMaster purchaseMaster =
                new PurchaseMaster();

        purchaseMaster.setVendor(vendor);

        purchaseMaster.setCompany(company);

        purchaseMaster.setInvoiceNo(
                dto.getInvoiceNo());

        purchaseMaster.setInvoiceDate(
                dto.getInvoiceDate());

        purchaseMaster.setNetItemCount(
                dto.getNetItemCount());

        purchaseMaster.setNetGross(
                dto.getNetGross());

        purchaseMaster.setNetDiscount(
                dto.getNetDiscount());

        purchaseMaster.setTaxableAmount(
                dto.getTaxableAmount());

        purchaseMaster.setNetGstAmount(
                dto.getNetGstAmount());

        purchaseMaster.setNetPayableAmount(
                dto.getNetPayableAmount());

        purchaseMaster.setFinalAmount(
                dto.getFinalAmount());

        List<PurchaseSub> items =
                dto.getItems()
                        .stream()
                        .map(item ->
                                buildPurchaseSub(
                                        item,
                                        purchaseMaster))
                        .collect(Collectors.toList());

        purchaseMaster.setPurchaseItems(
                items);

        PurchaseMaster saved =
                purchaseMasterRepository
                        .save(purchaseMaster);

        return convertToDto(saved);
    }

    // ==========================================================
    // GET PURCHASE BY ID
    // ==========================================================

    @Override
    public PurchaseMasterResponseDto getPurchaseById(
            Long pmId) {

        PurchaseMaster purchaseMaster =
                getPurchaseEntity(pmId);

        return convertToDto(
                purchaseMaster);
    }

    // ==========================================================
    // GET ALL PURCHASES
    // ==========================================================

    @Override
    public Page<PurchaseMasterResponseDto> getPurchases(
            Pageable pageable) {

        return purchaseMasterRepository
                .findAll(pageable)
                .map(this::convertToDto);
    }

    // ==========================================================
    // GET PURCHASES BY VENDOR
    // ==========================================================

    @Override
    public Page<PurchaseMasterResponseDto>
    getPurchasesByVendor(
            Long vendorId,
            Pageable pageable) {

        return purchaseMasterRepository
                .findByVendorVendorId(
                        vendorId,
                        pageable)
                .map(this::convertToDto);
    }

    // ==========================================================
    // GET PURCHASES BY COMPANY
    // ==========================================================

    @Override
    public Page<PurchaseMasterResponseDto>
    getPurchasesByCompany(
            Long companyId,
            Pageable pageable) {

        return purchaseMasterRepository
                .findByCompanyCompanyId(
                        companyId,
                        pageable)
                .map(this::convertToDto);
    }

    // ==========================================================
    // DELETE PURCHASE
    // ==========================================================

    @Override
    @Transactional
    public void deletePurchase(
            Long pmId) {

        PurchaseMaster purchaseMaster =
                getPurchaseEntity(pmId);

        purchaseMaster.getPurchaseItems()
                .forEach(item -> {

                    Product product =
                            item.getProduct();

                    decreaseStock(
                            product,
                            item.getTotalQty());

                    productRepository
                            .save(product);
                });

        purchaseMasterRepository
                .delete(purchaseMaster);
    }

    // ==========================================================
    // PRIVATE METHODS
    // ==========================================================

    private PurchaseSub buildPurchaseSub(
            PurchaseSubRequestDto dto,
            PurchaseMaster purchaseMaster) {

        Product product =
                productRepository.findById(
                        dto.getProductId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found"));

        increaseStock(
                product,
                dto.getTotalQty());

        productRepository.save(product);

        PurchaseSub item =
                new PurchaseSub();

        item.setPurchaseMaster(
                purchaseMaster);

        item.setProduct(product);

        item.setMrp(dto.getMrp());

        item.setTotalQty(
                dto.getTotalQty());

        item.setGrossAmount(
                dto.getGrossAmount());

        item.setDiscountPer(
                dto.getDiscountPer());

        item.setDiscountAmount(
                dto.getDiscountAmount());

        item.setTaxableAmount(
                dto.getTaxableAmount());

        item.setGstAmount(
                dto.getGstAmount());

        item.setPayableAmount(
                dto.getPayableAmount());

        item.setSoldQty(0);

        return item;
    }

    private PurchaseMaster getPurchaseEntity(
            Long pmId) {

        return purchaseMasterRepository
                .findWithDetailsByPmId(pmId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Purchase not found"));
    }

    private void validateVendorCompany(
            Vendor vendor,
            Company company) {

        boolean valid =
                vendor.getCompanies()
                        .stream()
                        .anyMatch(c ->
                                c.getCompanyId()
                                        .equals(
                                                company.getCompanyId()));

        if (!valid) {

            throw new RuntimeException(
                    "Selected Vendor is not associated with selected Company");
        }
    }

    private void increaseStock(
            Product product,
            Integer qty) {

        product.setStock(
                product.getStock() + qty);
    }

    private void decreaseStock(
            Product product,
            Integer qty) {

        int newStock =
                product.getStock() - qty;

        if (newStock < 0) {

            throw new RuntimeException(
                    "Stock cannot become negative");
        }

        product.setStock(newStock);
    }

    // ==========================================================
    // DTO CONVERSION
    // ==========================================================

    private PurchaseMasterResponseDto convertToDto(
            PurchaseMaster purchaseMaster) {

        PurchaseMasterResponseDto dto =
                new PurchaseMasterResponseDto();

        dto.setPmId(
                purchaseMaster.getPmId());

        dto.setVendorId(
                purchaseMaster.getVendor()
                        .getVendorId());

        dto.setVendorName(
                purchaseMaster.getVendor()
                        .getVname());

        dto.setCompanyId(
                purchaseMaster.getCompany()
                        .getCompanyId());

        dto.setCompanyName(
                purchaseMaster.getCompany()
                        .getCname());

        dto.setInvoiceNo(
                purchaseMaster.getInvoiceNo());

        dto.setInvoiceDate(
                purchaseMaster.getInvoiceDate());

        dto.setNetItemCount(
                purchaseMaster.getNetItemCount());

        dto.setNetGross(
                purchaseMaster.getNetGross());

        dto.setNetDiscount(
                purchaseMaster.getNetDiscount());

        dto.setTaxableAmount(
                purchaseMaster.getTaxableAmount());

        dto.setNetGstAmount(
                purchaseMaster.getNetGstAmount());

        dto.setNetPayableAmount(
                purchaseMaster.getNetPayableAmount());

        dto.setFinalAmount(
                purchaseMaster.getFinalAmount());

        dto.setActive(
                purchaseMaster.isActive());

        dto.setCreatedAt(
                purchaseMaster.getCreatedAt());

        dto.setUpdatedAt(
                purchaseMaster.getUpdatedAt());

        dto.setItems(
                purchaseMaster
                        .getPurchaseItems()
                        .stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList()));

        return dto;
    }

    private PurchaseSubResponseDto convertToDto(
            PurchaseSub item) {

        PurchaseSubResponseDto dto =
                new PurchaseSubResponseDto();

        dto.setPsId(
                item.getPsId());

        dto.setProductId(
                item.getProduct()
                        .getProductId());

        dto.setProductName(
                item.getProduct()
                        .getPname());

        dto.setMrp(
                item.getMrp());

        dto.setTotalQty(
                item.getTotalQty());

        dto.setGrossAmount(
                item.getGrossAmount());

        dto.setDiscountPer(
                item.getDiscountPer());

        dto.setDiscountAmount(
                item.getDiscountAmount());

        dto.setTaxableAmount(
                item.getTaxableAmount());

        dto.setGstAmount(
                item.getGstAmount());

        dto.setPayableAmount(
                item.getPayableAmount());

        dto.setSoldQty(
                item.getSoldQty());

        return dto;
    }
}
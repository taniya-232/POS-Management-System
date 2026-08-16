package com.jbs.posbe.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jbs.posbe.dto.request.FinancialYearPatchDto;
import com.jbs.posbe.entity.FinancialYear;

public interface FinancialYearService {

    FinancialYear saveFinancialYear(FinancialYear financialYear);

    Page<FinancialYear> getFinancialYears(Pageable pageable);

    FinancialYear getFinancialYearById(Long fyId);

    FinancialYear updateFinancialYear(Long fyId, FinancialYearPatchDto dto);

    void deleteFinancialYear(Long fyId);
}
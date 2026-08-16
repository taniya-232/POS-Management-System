package com.jbs.posbe.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jbs.posbe.dto.request.FinancialYearPatchDto;
import com.jbs.posbe.entity.FinancialYear;
import com.jbs.posbe.repository.FinancialYearRepository;
import com.jbs.posbe.service.FinancialYearService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FinancialYearServiceImpl implements FinancialYearService {

    private final FinancialYearRepository financialYearRepository;

    @Override
    public FinancialYear saveFinancialYear(FinancialYear financialYear) {

        if (financialYearRepository.existsByFinancialcode(financialYear.getFinancialcode())) {
            throw new RuntimeException("Financial code already exists");
        }

        return financialYearRepository.save(financialYear);
    }

    @Override
    public Page<FinancialYear> getFinancialYears(Pageable pageable) {

        return financialYearRepository.findAll(pageable);
    }

    @Override
    public FinancialYear getFinancialYearById(Long fyId) {

        return financialYearRepository
                .findById(fyId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Financial Year not found"));
    }

    @Override
    public FinancialYear updateFinancialYear(Long fyId, FinancialYearPatchDto dto) {

        FinancialYear financialYear = getFinancialYearById(fyId);

        if (dto.getStartingdt() != null) {
            financialYear.setStartingdt(dto.getStartingdt());
        }

        if (dto.getEndingdt() != null) {
            financialYear.setEndingdt(dto.getEndingdt());
        }

        if (dto.getFinancialcode() != null) {
            financialYear.setFinancialcode(dto.getFinancialcode());
        }

        if (dto.getActive() != null) {
			financialYear.setActive(dto.getActive());
        }

        return financialYearRepository.saveAndFlush(financialYear);
    }

    @Override
    public void deleteFinancialYear(Long fyId) {

        FinancialYear financialYear = getFinancialYearById(fyId);
        financialYearRepository.delete(financialYear);
    }
}
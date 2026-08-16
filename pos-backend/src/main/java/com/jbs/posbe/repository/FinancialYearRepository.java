package com.jbs.posbe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jbs.posbe.entity.FinancialYear;

@Repository
public interface FinancialYearRepository extends JpaRepository<FinancialYear, Long> {

    Optional<FinancialYear> findByFinancialcode(String financialcode);

    boolean existsByFinancialcode(String financialcode);
}
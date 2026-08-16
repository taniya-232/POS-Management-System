package com.jbs.posbe.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jbs.posbe.dto.request.UnitPatchDto;
import com.jbs.posbe.entity.Unit;

public interface UnitService {
	
	Unit saveUnit(Unit company);
	Page<Unit> getUnit(Pageable pageable);
	Unit getUnitById(Long companyId);
	Unit updateUnit(Long companyId, UnitPatchDto dto);
	void deleteUnit(Long companyId);

}

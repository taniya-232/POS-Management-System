package com.jbs.posbe.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.jbs.posbe.dto.request.UnitPatchDto;
import com.jbs.posbe.entity.Unit;
import com.jbs.posbe.exception.ResourceDeletedException;
import com.jbs.posbe.exception.ResourceNotFoundException;
import com.jbs.posbe.repository.UnitRepository;
import com.jbs.posbe.service.UnitService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService{
	
	private final UnitRepository unitRepository;

	@Override
	public Unit saveUnit(Unit unit) {
		if (unit == null) {
			throw new IllegalArgumentException("Unit cannot be null");
		}
		return unitRepository.save(unit);
	}

	@Override
	public Page<Unit> getUnit(
			org.springframework.data.domain.Pageable pageable) {
		if (pageable == null) {
			throw new IllegalArgumentException("Pageable parameter cannot be null");
		}
		return unitRepository.findAll(pageable);
	}

	@Override
	public Unit getUnitById(Long unitId) {
		Unit unit = unitRepository.findById(unitId)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Unit not found with ID: " + unitId
	                    )
	            );

	    // Soft delete validation
	    if (unit.isDeleted()) {

	        throw new ResourceDeletedException(
	                "Unit has already been deleted with ID: " + unitId
	        );
	    }

	    return unit;
	}

	@Override
	public Unit updateUnit(Long unitId, UnitPatchDto dto) {

		Unit existingUnit = unitRepository.findById(unitId)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Unit not found with ID: " + unitId
	                    )
	            );

	    // Soft delete validation
	    if (existingUnit.isDeleted()) {

	        throw new ResourceDeletedException(
	                "Unit already deleted with ID: " + unitId
	        );
	    }

	    // ==========================================
	    // PATCH ONLY PROVIDED FIELDS
	    // ==========================================
	    if (dto.getUname() != null) {
	        existingUnit.setUname(dto.getUname());
	    }

	    if (dto.getUabbr() != null) {
	        existingUnit.setUabbr(dto.getUabbr());
	    }

	    if (dto.getActive() != null) {
	        existingUnit.setActive(dto.getActive());
	    }

	    return unitRepository.save(existingUnit);
	}

	@Override
	public void deleteUnit(Long unitId) {

		Unit unit = unitRepository.findById(unitId)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Unit not found with ID: " + unitId
	                    )
	            );

	    // Already deleted check
	    if (unit.isDeleted()) {

	        throw new ResourceDeletedException(
	                "Unit has already been deleted with ID: " + unitId
	        );
	    }

	    // Soft delete
	    unit.setDeleted(true);

	    unitRepository.save(unit);
	}

}

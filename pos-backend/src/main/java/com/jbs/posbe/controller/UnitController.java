package com.jbs.posbe.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jbs.posbe.dto.ManagedApiResponse;
import com.jbs.posbe.dto.request.UnitPatchDto;
import com.jbs.posbe.dto.request.UnitRequestDto;
import com.jbs.posbe.entity.Unit;
import com.jbs.posbe.service.UnitService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/units")
@RequiredArgsConstructor
public class UnitController {
	
private final UnitService unitService;
	
	// ---------------------------------------------------------------------
	@Operation(tags = "Units", summary = "Create a new unit", description = "Creates a new unit of measurement for products.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Unit created successfully"),
			@ApiResponse(responseCode = "500", description = "Error creating unit") })
	@PostMapping
	public ResponseEntity<ManagedApiResponse<Unit>> saveUnit(
			@Valid @RequestBody UnitRequestDto dto) {

		Unit unit = new Unit();

	    unit.setUname(dto.getUname());
	    unit.setUabbr(dto.getUabbr());
	    unit.setActive(dto.getActive());

	    Unit savedCompany =
	            unitService.saveUnit(unit);

	    ManagedApiResponse<Unit> response =
	            new ManagedApiResponse<>(
	                    HttpStatus.CREATED.value(),
	                    "Unit created successfully",
	                    savedCompany
	            );

	    return ResponseEntity.status(HttpStatus.CREATED)
	            .body(response);
    }
	
	// ---------------------------------------------------------------------
	@Operation(tags = "Units", summary = "List unitss", description = "Retrieves all companies.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Units retrieved successfully"),
			@ApiResponse(responseCode = "500", description = "Error retrieving units") })
	@GetMapping("/page")
	public ResponseEntity<ManagedApiResponse<Page<Unit>>> getUnits(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Unit> unitPage =
                unitService.getUnit(pageable);

        ManagedApiResponse<Page<Unit>> response =
                new ManagedApiResponse<>(
                        HttpStatus.OK.value(),
                        "Units retrieved successfully",
                        unitPage
                );

        return ResponseEntity.ok(response);
    }
	
	// ---------------------------------------------------------------------
	@Operation(tags = "Units", summary = "Get a unit", description = "Fetches a single unit by its ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Unit retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Unit not found") })
	@GetMapping("/{id}")
	public ResponseEntity<ManagedApiResponse<Unit>> getCompanyById(
            @PathVariable Long id) {

        Unit unit = unitService.getUnitById(id);

        ManagedApiResponse<Unit> response =
                new ManagedApiResponse<>(
                        HttpStatus.OK.value(),
                        "Unit retrieved successfully",
                        unit
                );

        return ResponseEntity.ok(response);
    }
	
	// ---------------------------------------------------------------------
	@Operation(tags = "Units", summary = "Update unit data", description = "Updates a unit details.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Unit updated successfully"),
		@ApiResponse(responseCode = "404", description = "Unit not found"),
		@ApiResponse(responseCode = "400", description = "Invalid request body") })
	@PatchMapping("/{unitId}")
	public ResponseEntity<ManagedApiResponse<Unit>> updateUnit(
            @PathVariable Long unitId,
            @Valid @RequestBody UnitPatchDto dto) {

		Unit updatedUnit =
	            unitService.updateUnit(unitId, dto);

	    ManagedApiResponse<Unit> response =
	            new ManagedApiResponse<>(
	                    HttpStatus.OK.value(),
	                    "Unit updated successfully",
	                    updatedUnit
	            );

	    return ResponseEntity.ok(response);
    }
	
	// ---------------------------------------------------------------------
	@Operation(
		tags = "Units",
		summary = "Delete a unit by ID",
		description = "Deletes a unit."
		)
		@ApiResponses(value = {
		    @ApiResponse(responseCode = "204",
		                 description = "Unit deleted successfully"),
		    @ApiResponse(responseCode = "404",
		                 description = "Unit not found"),

		    @ApiResponse(responseCode = "500",
		                 description = "Error deleting unit")
		})
	@DeleteMapping("/{unitId}")
	public ResponseEntity<ManagedApiResponse<Void>> deleteUnit(
            @PathVariable Long unitId) {

        unitService.deleteUnit(unitId);

        ManagedApiResponse<Void> response =
                new ManagedApiResponse<>(
                        HttpStatus.NO_CONTENT.value(),
                        "Unit deleted successfully",
                        null
                );

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(response);
    }

}

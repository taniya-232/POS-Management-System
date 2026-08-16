package com.jbs.posbe.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jbs.posbe.entity.Company;
import com.jbs.posbe.service.CompanyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.jbs.posbe.dto.ManagedApiResponse;
import com.jbs.posbe.dto.request.CompanyPatchDto;
import com.jbs.posbe.dto.request.CompanyRequestDto;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {
	
	private final CompanyService companyService;
	
	// ---------------------------------------------------------------------
	@Operation(tags = "Companies", summary = "Create a new company", description = "Creates a new company.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Company created successfully"),
			@ApiResponse(responseCode = "500", description = "Error creating company") })
	@PostMapping
	public ResponseEntity<ManagedApiResponse<Company>> saveCompany(
			@Valid @RequestBody CompanyRequestDto dto) {

		Company company = new Company();

	    company.setCname(dto.getCname());
	    company.setCabbr(dto.getCabbr());
	    company.setActive(dto.getActive());

	    Company savedCompany =
	            companyService.saveCompany(company);

	    ManagedApiResponse<Company> response =
	            new ManagedApiResponse<>(
	                    HttpStatus.CREATED.value(),
	                    "Company created successfully",
	                    savedCompany
	            );

	    return ResponseEntity.status(HttpStatus.CREATED)
	            .body(response);
    }
	
	// ---------------------------------------------------------------------
	@Operation(tags = "Companies", summary = "List companies", description = "Retrieves all companies.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Companies retrieved successfully"),
			@ApiResponse(responseCode = "500", description = "Error retrieving units") })
	@GetMapping("/page")
	public ResponseEntity<ManagedApiResponse<Page<Company>>> getCompanies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Company> companyPage =
                companyService.getCompany(pageable);

        ManagedApiResponse<Page<Company>> response =
                new ManagedApiResponse<>(
                        HttpStatus.OK.value(),
                        "Companies retrieved successfully",
                        companyPage
                );

        return ResponseEntity.ok(response);
    }
	
	// ---------------------------------------------------------------------
	@Operation(tags = "Companies", summary = "Get a company", description = "Fetches a single company by its ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Company retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Company not found") })
	@GetMapping("/{id}")
	public ResponseEntity<ManagedApiResponse<Company>> getCompanyById(
            @PathVariable Long id) {

        Company company =
                companyService.getCompanyById(id);

        ManagedApiResponse<Company> response =
                new ManagedApiResponse<>(
                        HttpStatus.OK.value(),
                        "Company retrieved successfully",
                        company
                );

        return ResponseEntity.ok(response);
    }
	
	// ---------------------------------------------------------------------
	@Operation(tags = "Companies", summary = "Update company data", description = "Updates a company details.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Company updated successfully"),
		@ApiResponse(responseCode = "404", description = "Company not found"),
		@ApiResponse(responseCode = "400", description = "Invalid request body") })
	@PatchMapping("/{companyId}")
	public ResponseEntity<ManagedApiResponse<Company>> updateCompany(
            @PathVariable Long companyId,
            @Valid @RequestBody CompanyPatchDto dto) {

		Company updatedCompany =
	            companyService.updateCompany(companyId, dto);

	    ManagedApiResponse<Company> response =
	            new ManagedApiResponse<>(
	                    HttpStatus.OK.value(),
	                    "Company updated successfully",
	                    updatedCompany
	            );

	    return ResponseEntity.ok(response);
    }
	
	// ---------------------------------------------------------------------
	@Operation(
		tags = "Companies",
		summary = "Delete a company by ID",
		description = "Deletes a company."
		)
		@ApiResponses(value = {
		    @ApiResponse(responseCode = "204",
		                 description = "Company deleted successfully"),
		    @ApiResponse(responseCode = "404",
		                 description = "Company not found"),

		    @ApiResponse(responseCode = "500",
		                 description = "Error deleting company")
		})
	@DeleteMapping("/{companyId}")
	public ResponseEntity<ManagedApiResponse<Void>> deleteCompany(
            @PathVariable Long companyId) {

        companyService.deleteCompany(companyId);

        ManagedApiResponse<Void> response =
                new ManagedApiResponse<>(
                        HttpStatus.NO_CONTENT.value(),
                        "Company deleted successfully",
                        null
                );

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(response);
    }

}

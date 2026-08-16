package com.jbs.posbe.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jbs.posbe.dto.ManagedApiResponse;
import com.jbs.posbe.dto.request.ProductPatchDto;
import com.jbs.posbe.dto.request.ProductRequestDto;
import com.jbs.posbe.entity.Product;
import com.jbs.posbe.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	// ---------------------------------------------------------------------
	@Operation(tags = "Products", summary = "Create a new product", description = "Creates a new product.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Product created successfully"),
			@ApiResponse(responseCode = "500", description = "Error creating product") })
	@PostMapping
	public ResponseEntity<ManagedApiResponse<Product>> saveProduct(@Valid @RequestBody ProductRequestDto dto) {

		Product savedProduct = productService.saveProduct(dto);

		ManagedApiResponse<Product> response = new ManagedApiResponse<>(HttpStatus.CREATED.value(),
				"Product created successfully", savedProduct);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// ---------------------------------------------------------------------
	@Operation(tags = "Products", summary = "List products", description = "Retrieves paginated products.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
			@ApiResponse(responseCode = "500", description = "Error retrieving products") })
	@GetMapping("/page")
	public ResponseEntity<ManagedApiResponse<Page<Product>>> getProducts(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		Pageable pageable = PageRequest.of(page, size);
		Page<Product> productPage = productService.getProduct(pageable);
		ManagedApiResponse<Page<Product>> response = new ManagedApiResponse<>(HttpStatus.OK.value(),
				"Products retrieved successfully", productPage);
		return ResponseEntity.ok(response);
	}

	// ---------------------------------------------------------------------
	@Operation(tags = "Products", summary = "Get product by ID", description = "Fetches a single product by its ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Product retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Product not found") })
	@GetMapping("/{productId}")
	public ResponseEntity<ManagedApiResponse<Product>> getProductById(@PathVariable Long productId) {

		Product product = productService.getProductById(productId);
		ManagedApiResponse<Product> response = new ManagedApiResponse<>(HttpStatus.OK.value(),
				"Product retrieved successfully", product);
		return ResponseEntity.ok(response);
	}

	// ---------------------------------------------------------------------
	@Operation(tags = "Products", summary = "Get products by company", description = "Retrieves all products belonging to a company.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Company not found") })
	@GetMapping("/company/{companyId}")
	public ResponseEntity<ManagedApiResponse<?>> getProductsByCompanyId(@PathVariable Long companyId) {

		ManagedApiResponse<?> response = new ManagedApiResponse<>(HttpStatus.OK.value(),
				"Products retrieved successfully", productService.getProductsByCompanyId(companyId));
		return ResponseEntity.ok(response);
	}

	// ---------------------------------------------------------------------
	@Operation(tags = "Products", summary = "Update product data", description = "Updates product details.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Product updated successfully"),
			@ApiResponse(responseCode = "404", description = "Product not found"),
			@ApiResponse(responseCode = "400", description = "Invalid request body") })
	@PatchMapping("/{productId}")
	public ResponseEntity<ManagedApiResponse<Product>> updateProduct(@PathVariable Long productId,
			@RequestBody ProductPatchDto dto) {

		Product updatedProduct = productService.updateProduct(productId, dto);

		ManagedApiResponse<Product> response = new ManagedApiResponse<>(HttpStatus.OK.value(),
				"Product updated successfully", updatedProduct);

		return ResponseEntity.ok(response);
	}

	// ---------------------------------------------------------------------
	@Operation(tags = "Products", summary = "Delete product by ID", description = "Deletes a product.")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Product not found"),
			@ApiResponse(responseCode = "500", description = "Error deleting product") })
	@DeleteMapping("/{productId}")
	public ResponseEntity<ManagedApiResponse<Void>> deleteProduct(@PathVariable Long productId) {

		productService.deleteProduct(productId);

		ManagedApiResponse<Void> response = new ManagedApiResponse<>(HttpStatus.NO_CONTENT.value(),
				"Product deleted successfully", null);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
	}

}
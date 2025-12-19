package com.malcolm.product;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.malcolm.product.dto.ProductRequest;
import com.malcolm.product.dto.ProductResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

	ProductResponse toResponse(Product product);

	Product toProduct(ProductRequest productRequest);

	void updateProduct(ProductRequest productRequest, @MappingTarget Product product);
}

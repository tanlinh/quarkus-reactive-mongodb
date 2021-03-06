package com.product.mapper;

import com.product.dto.ProductDTO;
import com.product.entity.Product;
import org.mapstruct.Mapper;


@Mapper(componentModel = "cdi")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    ProductDTO toDTO (Product user);
}

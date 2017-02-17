package br.com.alex.mapper;

import br.com.alex.dto.ProductDTO;
import br.com.alex.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mappings({@Mapping(source = "changeDate", dateFormat = "dd/MM/yyyy", target = "changeDate")})
    ProductDTO productToProductDTO(Product product);

    @Mappings({@Mapping(source = "changeDate", dateFormat = "dd/MM/yyyy", target = "changeDate")})
    Product productDTOToProduct(ProductDTO productDTO);

}

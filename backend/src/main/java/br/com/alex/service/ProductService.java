package br.com.alex.service;

import br.com.alex.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getProducts();

    ProductDTO getProductById(Long id);

}

package br.com.alex.service;

import br.com.alex.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getProducts();

    ProductDTO getProductById(Long id);

    ProductDTO saveProduct(ProductDTO productDTO);

    ProductDTO updateProduct(Long id, ProductDTO productDTO);

    boolean deleteProduct(Long id);
}

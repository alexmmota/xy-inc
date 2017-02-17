package br.com.alex.service.impl;

import br.com.alex.dto.ProductDTO;
import br.com.alex.exception.EntityNotFoundException;
import br.com.alex.mapper.ProductMapper;
import br.com.alex.model.Product;
import br.com.alex.repository.ProductRepository;
import br.com.alex.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductDTO> getProducts() {
        List<Product> products = (List<Product>) productRepository.findAll();
        if(products == null || products.size() == 0)
            throw new EntityNotFoundException("Lista de produtos está vazia!");
        return products.stream().map(p -> productMapper.productToProductDTO(p)).collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findOne(id);
        if(product == null)
            throw new EntityNotFoundException("Produto não encontrado!");
        return productMapper.productToProductDTO(product);
    }

}

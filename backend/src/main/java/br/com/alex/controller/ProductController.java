package br.com.alex.controller;

import br.com.alex.dto.ProductDTO;
import br.com.alex.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<ProductDTO> getProducts() {
        return productService.getProducts();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ProductDTO getProductsById(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }

}
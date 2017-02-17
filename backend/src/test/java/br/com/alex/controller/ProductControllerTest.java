package br.com.alex.controller;

import br.com.alex.dto.ProductDTO;
import br.com.alex.exception.EntityNotFoundException;
import br.com.alex.model.constraint.Category;
import br.com.alex.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void getProductsSuccessTest() throws Exception{
        when(productService.getProducts()).thenReturn(buildListProducts());
        mockMvc.perform(get("/products")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)))
                .andExpect(jsonPath("$[5].name", is("Product 6")));
    }

    @Test
    public void getProductsNoContentTest() throws Exception{
        when(productService.getProducts()).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/products")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getProductsByIdSuccessTest() throws Exception{
        when(productService.getProductById(1l)).thenReturn(buildProduct(false));
        mockMvc.perform(get("/products/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Product 1")))
                .andExpect(jsonPath("$.description", is("Description Product 1")))
                .andExpect(jsonPath("$.category", is("ELECTRONIC")))
                .andExpect(jsonPath("$.price", is(18.99)))
                .andExpect(jsonPath("$.changeDate", is("15/02/2017")));
    }

    @Test
    public void getProductByIdNoContentTest() throws Exception {
        when(productService.getProductById(1l)).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/products/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


    private ProductDTO buildProduct(boolean isNew) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(isNew ? null : 1l);
        productDTO.setName("Product 1");
        productDTO.setDescription("Description Product 1");
        productDTO.setCategory(Category.ELECTRONIC);
        productDTO.setChangeDate("15/02/2017");
        productDTO.setPrice(18.99);
        return productDTO;
    }

    private List<ProductDTO> buildListProducts() {
        List<ProductDTO> productList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            ProductDTO product = buildProduct(false);
            product.setId(Long.valueOf(i));
            product.setName("Product ".concat(String.valueOf(i)));
            productList.add(product);
        }
        return productList;
    }
}

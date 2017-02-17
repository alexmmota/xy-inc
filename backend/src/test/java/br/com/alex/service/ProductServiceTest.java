package br.com.alex.service;

import br.com.alex.dto.ProductDTO;
import br.com.alex.exception.EntityNotFoundException;
import br.com.alex.model.Product;
import br.com.alex.model.constraint.Category;
import br.com.alex.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

    @MockBean
    private ProductRepository productRepositoryMock;

    @Autowired
    private ProductService productService;

    @Test
    public void getAllProductsSuccessTest() {
        when(productRepositoryMock.findAll()).thenReturn(buildListProducts());
        List<ProductDTO> productList = productService.getProducts();
        assertThat(productList.size()).isEqualTo(10);
        assertThat(productList.get(0).getId()).isEqualTo(1l);
    }

    @Test (expected = EntityNotFoundException.class)
    public void getAllProductsEmptyListTest() {
        when(productRepositoryMock.findAll()).thenReturn(null);
        productService.getProducts();
    }


    private Product buildProduct(boolean hasId) {
        Product product = new Product();
        product.setId(hasId ? 1l : null);
        product.setName("Product 1");
        product.setDescription("Description Product 1");
        product.setCategory(Category.ELECTRONIC);
        product.setPrice(18.99);
        product.setChangeDate(Date.from(LocalDate.of(2017, Month.FEBRUARY, 15).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return product;
    }

    private ProductDTO buildProductDTO(boolean hasId) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(hasId ? 1l : null);
        productDTO.setName("Product 1");
        productDTO.setDescription("Description Product 1");
        productDTO.setCategory(Category.ELECTRONIC);
        productDTO.setChangeDate("15/02/2017");
        productDTO.setPrice(18.99);
        return productDTO;
    }

    private List<Product> buildListProducts() {
        List<Product> productList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Product product = buildProduct(true);
            product.setId(Long.valueOf(i));
            productList.add(product);
        }
        return productList;
    }

}
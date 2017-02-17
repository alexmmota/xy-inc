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

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;


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

    @Test
    public void getProductByIdSuccessTest() {
        when(productRepositoryMock.findOne(1l)).thenReturn(buildProduct(true));

        ProductDTO productDTO = productService.getProductById(1l);
        assertThat(productDTO.getId()).isEqualTo(1l);
        assertThat(productDTO.getName()).isEqualTo("Product 1");
        assertThat(productDTO.getDescription()).isEqualTo("Description Product 1");
        assertThat(productDTO.getCategory()).isEqualTo(Category.ELECTRONIC);
        assertThat(productDTO.getPrice()).isEqualTo(18.99);
    }

    @Test (expected = EntityNotFoundException.class)
    public void getProductByIdNotFoundTest() {
        when(productRepositoryMock.findOne(2l)).thenReturn(null);
        productService.getProductById(2l);
    }

    @Test
    public void saveNewProductSuccessTest() {
        when(productRepositoryMock.save(any(Product.class))).thenReturn(buildProduct(true));

        ProductDTO productDTO = productService.saveProduct(buildProductDTO(false));
        assertThat(productDTO.getId()).isEqualTo(1l);
        assertThat(productDTO.getName()).isEqualTo("Product 1");
        assertThat(productDTO.getDescription()).isEqualTo("Description Product 1");
        assertThat(productDTO.getCategory()).isEqualTo(Category.ELECTRONIC);
        assertThat(productDTO.getPrice()).isEqualTo(18.99);
    }

    @Test
    public void updateProductSuccessTest() {
        Product product = buildProduct(true);
        product.setName("Product 1 - alterado");

        ProductDTO productDTO = buildProductDTO(true);
        productDTO.setName("Product 1 - alterado");

        when(productRepositoryMock.findOne(1l)).thenReturn(buildProduct(true));
        when(productRepositoryMock.save(any(Product.class))).thenReturn(product);

        ProductDTO productDTO2 = productService.updateProduct(1l, productDTO);
        assertThat(productDTO2.getId()).isEqualTo(1l);
        assertThat(productDTO2.getName()).isEqualTo("Product 1 - alterado");
        assertThat(productDTO2.getDescription()).isEqualTo("Description Product 1");
        assertThat(productDTO2.getCategory()).isEqualTo(Category.ELECTRONIC);
        assertThat(productDTO2.getPrice()).isEqualTo(18.99);
    }

    @Test (expected = EntityNotFoundException.class)
    public void updateProductFailProductNotFoundTest() {
        ProductDTO productDTO = buildProductDTO(true);
        productDTO.setName("Product 1 - alterado");

        when(productRepositoryMock.findOne(1l)).thenReturn(null);

        productService.updateProduct(1l, productDTO);
    }

    @Test
    public void deleteProductSuccessTest() {
        when(productRepositoryMock.findOne(1l)).thenReturn(buildProduct(true));

        boolean result = productService.deleteProduct(1l);
        assertThat(result).isEqualTo(true);
    }

    @Test (expected = EntityNotFoundException.class)
    public void deleteProductNotFoundTest() {
        when(productRepositoryMock.findOne(2l)).thenReturn(null);
        productService.deleteProduct(2l);
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

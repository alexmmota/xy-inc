package br.com.alex.mapper;

import br.com.alex.dto.ProductDTO;
import br.com.alex.model.Product;
import br.com.alex.model.constraint.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductMapperTest {

    @Autowired
    private ProductMapper productMapper;

    @Test
    public void convertProductToProductDTOTest() {
        Product product = new Product();
        product.setId(1l);
        product.setName("Product 1");
        product.setDescription("Description Product 1");
        product.setCategory(Category.ELECTRONIC);
        product.setPrice(18.99);
        product.setChangeDate(Date.from(LocalDate.of(2017, Month.FEBRUARY, 15).atStartOfDay(ZoneId.systemDefault()).toInstant()));

        ProductDTO productDTO = productMapper.productToProductDTO(product);

        assertThat(productDTO.getId()).isEqualTo(1l);
        assertThat(productDTO.getName()).isEqualTo("Product 1");
        assertThat(productDTO.getDescription()).isEqualTo("Description Product 1");
        assertThat(productDTO.getCategory()).isEqualTo(Category.ELECTRONIC);
        assertThat(productDTO.getPrice()).isEqualTo(18.99);
        assertThat(productDTO.getChangeDate()).isEqualTo("15/02/2017");
    }

    @Test
    public void convertProductDTOToProductTest() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1l);
        productDTO.setName("Product DTO 1");
        productDTO.setDescription("Description Product DTO 1");
        productDTO.setCategory(Category.BOOKS);
        productDTO.setChangeDate("15/02/2017");
        productDTO.setPrice(9.99);

        Product product = productMapper.productDTOToProduct(productDTO);

        assertThat(product.getId()).isEqualTo(1l);
        assertThat(product.getName()).isEqualTo("Product DTO 1");
        assertThat(product.getDescription()).isEqualTo("Description Product DTO 1");
        assertThat(product.getCategory()).isEqualTo(Category.BOOKS);
        assertThat(product.getPrice()).isEqualTo(9.99);
        assertThat(product.getChangeDate()).isEqualTo(Date.from(LocalDate.of(2017, Month.FEBRUARY, 15).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

}
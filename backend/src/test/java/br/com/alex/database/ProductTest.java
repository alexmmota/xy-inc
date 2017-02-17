package br.com.alex.database;


import br.com.alex.dto.ProductDTO;
import br.com.alex.model.constraint.Category;
import br.com.alex.service.ProductService;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public class ProductTest {

    @Autowired
    private ProductService productService;

    @Test
    @DatabaseSetup(value="/META-INF/save/product_setup.xml", type= DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value= "/META-INF/clear_database.xml", type=DatabaseOperation.DELETE_ALL)
    @ExpectedDatabase(value="/META-INF/save/product_expected.xml", assertionMode= DatabaseAssertionMode.NON_STRICT)
    public void test1SaveNewProductSuccess() {
        ProductDTO product = new ProductDTO();
        product.setName("PRODUCT 1");
        product.setDescription("Descricao do produto 1");
        product.setCategory(Category.ELECTRONIC);
        product.setPrice(269.99);
        product.setChangeDate("15/02/2017");
        product = productService.saveProduct(product);
        assertEquals(Long.valueOf(1), product.getId());
    }

    @Test
    @DatabaseSetup(value="/META-INF/update/product_setup.xml", type= DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value= "/META-INF/clear_database.xml", type=DatabaseOperation.DELETE_ALL)
    @ExpectedDatabase(value="/META-INF/update/product_expected.xml", assertionMode= DatabaseAssertionMode.NON_STRICT)
    public void test2UpdateProductSuccess() {
        ProductDTO product = productService.getProductById(1l);
        product.setDescription("Descricao do produto 1 alterado");
        product.setPrice(299.00);
        product = productService.updateProduct(1l, product);
        assertEquals(Long.valueOf(1), product.getId());
        assertEquals(Double.valueOf(299.00), product.getPrice());
    }

    @Test
    @DatabaseSetup(value="/META-INF/delete/product_setup.xml", type= DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value= "/META-INF/clear_database.xml", type=DatabaseOperation.DELETE_ALL)
    @ExpectedDatabase(value="/META-INF/delete/product_expected.xml", assertionMode= DatabaseAssertionMode.NON_STRICT)
    public void test3DeleteProduct() {
        productService.deleteProduct(1l);
    }

}

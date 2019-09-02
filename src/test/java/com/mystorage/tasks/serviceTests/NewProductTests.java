package com.mystorage.tasks.serviceTests;

import com.mystorage.tasks.services.ProductService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class NewProductTests {

    private ProductService productService = new ProductService();

    @Before
    @After
    public void clear() {

        productService.clear();
    }

    @Test
    public void productSingleCreationTest() {

        String[] inputOne = new String[2];
        inputOne[0] = "NEWPRODUCT";
        inputOne[1] = "Product 1";

        String resultOne = productService.newProduct(inputOne);

        assertTrue(resultOne.equals("OK"));
        assertTrue(productService.getProducts().size() == 1);
        assertTrue(productService.getProducts().get(0).equals("Product 1"));
    }

    @Test
    public void productDoubleCreationTest() {

        String[] inputOne = new String[2];
        inputOne[0] = "NEWPRODUCT";
        inputOne[1] = "Product 1";

        String[] inputTwo = new String[2];
        inputTwo[0] = "NEWPRODUCT";
        inputTwo[1] = "Product 2";

        productService.newProduct(inputOne);
        String resultAgain = productService.newProduct(inputOne);

        assertTrue(resultAgain.equals("ERROR"));
        assertTrue(productService.getProducts().size() == 1);

        String resultTwo = productService.newProduct(inputTwo);

        assertTrue(resultTwo.equals("OK"));
        assertTrue(productService.getProducts().size() == 2);
        assertTrue(productService.getProducts().get(0).equals("Product 1"));
        assertTrue(productService.getProducts().get(1).equals("Product 2"));
    }

    @Test
    public void productEmptyCreationTest() {

        String[] inputOne = new String[2];
        inputOne[0] = "NEWPRODUCT";
        inputOne[1] = "";

        String resultOne = productService.newProduct(inputOne);

        assertTrue(resultOne.equals("ERROR"));
        assertTrue(productService.getProducts().size() == 0);
    }

    @Test
    public void faultyInputTest() {

        String[] inputOne = new String[1];
        inputOne[0] = "NEWPRODUCT";

        String resultOne = productService.newProduct(inputOne);

        assertTrue(resultOne.equals("ERROR"));
        assertTrue(productService.getProducts().size() == 0);

        String[] inputTwo = null;

        String resultTwo = productService.newProduct(inputOne);

        assertTrue(resultOne.equals("ERROR"));
        assertTrue(productService.getProducts().size() == 0);
    }
}

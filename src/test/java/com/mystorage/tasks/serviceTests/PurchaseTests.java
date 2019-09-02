package com.mystorage.tasks.serviceTests;

import com.mystorage.tasks.services.ProductService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PurchaseTests {

    private ProductService productService = new ProductService();

    @Before
    @After
    public void clear() {

        productService.clear();
    }

    @Test
    public void singlePurchaseTest() {

        String[] productOne = new String[2];
        productOne[0] = "NEWPRODUCT";
        productOne[1] = "Product 1";
        productService.newProduct(productOne);

        String[] inputOne = new String[5];
        inputOne[0] = "PURCHASE";
        inputOne[1] = "Product 1";
        inputOne[2] = "2";
        inputOne[3] = "1000";
        inputOne[4] = "01.01.2000";

        String resultOne = productService.purchase(inputOne);

        assertTrue(resultOne.equals("OK"));
        assertTrue(productService.getActualStorageEntries().size() == 1);
        assertTrue(productService.getActualStorageEntries().get(0).getProduct().equals("Product 1"));
    }

    @Test
    public void doublePurchaseTest() {

        String[] productOne = new String[2];
        productOne[0] = "NEWPRODUCT";
        productOne[1] = "Product 1";
        productService.newProduct(productOne);

        String[] productTwo = new String[2];
        productTwo[0] = "NEWPRODUCT";
        productTwo[1] = "Product 2";
        productService.newProduct(productTwo);

        String[] inputOne = new String[5];
        inputOne[0] = "PURCHASE";
        inputOne[1] = "Product 1";
        inputOne[2] = "2";
        inputOne[3] = "1000";
        inputOne[4] = "01.01.2000";

        String[] inputTwo = new String[5];
        inputTwo[0] = "PURCHASE";
        inputTwo[1] = "Product 2";
        inputTwo[2] = "2";
        inputTwo[3] = "2000";
        inputTwo[4] = "02.01.2000";

        String resultOne = productService.purchase(inputOne);
        String resultTwo = productService.purchase(inputTwo);

        assertTrue(resultOne.equals("OK"));
        assertTrue(resultTwo.equals("OK"));
        assertTrue(productService.getActualStorageEntries().size() == 2);
        assertTrue(productService.getActualStorageEntries().get(0).getProduct().equals("Product 1"));
        assertTrue(productService.getActualStorageEntries().get(1).getProduct().equals("Product 2"));
    }

    @Test
    public void missingProductPurchaseTest() {

        String[] productOne = new String[2];
        productOne[0] = "NEWPRODUCT";
        productOne[1] = "Product 1";
        productService.newProduct(productOne);

        String[] inputOne = new String[5];
        inputOne[0] = "PURCHASE";
        inputOne[1] = "Product 2";
        inputOne[2] = "2";
        inputOne[3] = "1000";
        inputOne[4] = "01.01.2000";

        String resultOne = productService.purchase(inputOne);

        assertTrue(resultOne.equals("ERROR"));
        assertTrue(productService.getActualStorageEntries().size() == 0);
    }

    @Test
    public void faultyInputPurchaseTest() {

        //absent field
        String[] productOne = new String[2];
        productOne[0] = "NEWPRODUCT";
        productOne[1] = "Product 1";
        productService.newProduct(productOne);

        String[] inputOne = new String[5];
        inputOne[0] = "PURCHASE";
        inputOne[1] = "Product 1";
        inputOne[2] = "2";
        inputOne[3] = "1000";

        String resultOne = productService.purchase(inputOne);

        assertTrue(resultOne.equals("ERROR"));
        assertTrue(productService.getActualStorageEntries().size() == 0);

        //wrong data type
        String[] inputTwo = new String[5];
        inputTwo[0] = "PURCHASE";
        inputTwo[1] = "Product 1";
        inputTwo[2] = "2";
        inputTwo[3] = "abc";
        inputTwo[4] = "01.01.2000";

        String resultTwo = productService.purchase(inputTwo);

        assertTrue(resultTwo.equals("ERROR"));
        assertTrue(productService.getActualStorageEntries().size() == 0);

        //negative price
        String[] inputThree = new String[5];
        inputThree[0] = "PURCHASE";
        inputThree[1] = "Product 1";
        inputThree[2] = "2";
        inputThree[3] = "-100";
        inputThree[4] = "01.01.2000";

        String resultThree = productService.purchase(inputThree);

        assertTrue(resultThree.equals("ERROR"));
        assertTrue(productService.getActualStorageEntries().size() == 0);
    }
}

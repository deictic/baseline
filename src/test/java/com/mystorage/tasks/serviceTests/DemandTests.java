package com.mystorage.tasks.serviceTests;

import com.mystorage.tasks.services.ProductService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DemandTests {

    private ProductService productService = new ProductService();

    @Before
    @After
    public void clear() {

        productService.clear();
    }

    @Test
    public void demandFromSingleLargeAmountTest() {

        //single entry is created and then demanded
        //stored amount exceeds demand
        String[] productOne = new String[2];
        productOne[0] = "NEWPRODUCT";
        productOne[1] = "Product 1";
        productService.newProduct(productOne);

        String[] purchaseOne = new String[5];
        purchaseOne[0] = "PURCHASE";
        purchaseOne[1] = "Product 1";
        purchaseOne[2] = "2";
        purchaseOne[3] = "1000";
        purchaseOne[4] = "01.01.2000";
        productService.purchase(purchaseOne);

        String[] demandOne = new String[5];
        demandOne[0] = "DEMAND";
        demandOne[1] = "Product 1";
        demandOne[2] = "1";
        demandOne[3] = "2000";
        demandOne[4] = "02.01.2000";

        String resultOne = productService.demand(demandOne);

        assertTrue(resultOne.equals("OK"));
        assertTrue(productService.getActualStorageEntries().size() == 1);
        assertTrue(productService.getActualStorageEntries().get(0).getAmount() == 1);

        assertTrue(productService.getSales().size() == 1);
        assertTrue(productService.getSales().get(0).getIncome() == 1000);
    }

    @Test
    public void demandFromEqualAmountTest() {

        //single entry is created and then demanded
        //mostly as above, but demand equals stored amount
        String[] productOne = new String[2];
        productOne[0] = "NEWPRODUCT";
        productOne[1] = "Product 1";
        productService.newProduct(productOne);

        String[] purchaseOne = new String[5];
        purchaseOne[0] = "PURCHASE";
        purchaseOne[1] = "Product 1";
        purchaseOne[2] = "1";
        purchaseOne[3] = "1000";
        purchaseOne[4] = "01.01.2000";
        productService.purchase(purchaseOne);

        String[] demandOne = new String[5];
        demandOne[0] = "DEMAND";
        demandOne[1] = "Product 1";
        demandOne[2] = "1";
        demandOne[3] = "2000";
        demandOne[4] = "02.01.2000";

        String resultOne = productService.demand(demandOne);

        assertTrue(resultOne.equals("OK"));
        assertTrue(productService.getActualStorageEntries().size() == 0);

        assertTrue(productService.getSales().size() == 1);
        assertTrue(productService.getSales().get(0).getIncome() == 1000);
    }

    @Test
    public void demandFromTwoSourcesWithoutExcessTest() {

        //two purchases that both get used precisely
        String[] productOne = new String[2];
        productOne[0] = "NEWPRODUCT";
        productOne[1] = "Product 1";
        productService.newProduct(productOne);

        String[] purchaseOne = new String[5];
        purchaseOne[0] = "PURCHASE";
        purchaseOne[1] = "Product 1";
        purchaseOne[2] = "1";
        purchaseOne[3] = "1000";
        purchaseOne[4] = "01.01.2000";
        productService.purchase(purchaseOne);

        String[] purchaseTwo = new String[5];
        purchaseTwo[0] = "PURCHASE";
        purchaseTwo[1] = "Product 1";
        purchaseTwo[2] = "2";
        purchaseTwo[3] = "2000";
        purchaseTwo[4] = "02.01.2000";
        productService.purchase(purchaseTwo);

        String[] demandOne = new String[5];
        demandOne[0] = "DEMAND";
        demandOne[1] = "Product 1";
        demandOne[2] = "3";
        demandOne[3] = "3000";
        demandOne[4] = "03.01.2000";

        String resultOne = productService.demand(demandOne);

        assertTrue(resultOne.equals("OK"));
        assertTrue(productService.getActualStorageEntries().size() == 0);

        assertTrue(productService.getSales().size() == 1);
        assertTrue(productService.getSales().get(0).getIncome() == 4000);
    }

    @Test
    public void demandFromTwoSourcesTest() {

        //two purchases that both get used with excess left
        String[] productOne = new String[2];
        productOne[0] = "NEWPRODUCT";
        productOne[1] = "Product 1";
        productService.newProduct(productOne);

        String[] purchaseOne = new String[5];
        purchaseOne[0] = "PURCHASE";
        purchaseOne[1] = "Product 1";
        purchaseOne[2] = "1";
        purchaseOne[3] = "1000";
        purchaseOne[4] = "01.01.2000";
        productService.purchase(purchaseOne);

        String[] purchaseTwo = new String[5];
        purchaseTwo[0] = "PURCHASE";
        purchaseTwo[1] = "Product 1";
        purchaseTwo[2] = "2";
        purchaseTwo[3] = "2000";
        purchaseTwo[4] = "02.01.2000";
        productService.purchase(purchaseTwo);

        String[] demandOne = new String[5];
        demandOne[0] = "DEMAND";
        demandOne[1] = "Product 1";
        demandOne[2] = "2";
        demandOne[3] = "3000";
        demandOne[4] = "03.01.2000";

        String resultOne = productService.demand(demandOne);

        assertTrue(resultOne.equals("OK"));
        assertTrue(productService.getActualStorageEntries().size() == 1);
        assertTrue(productService.getActualStorageEntries().get(0).getAmount() == 1);

        assertTrue(productService.getSales().size() == 1);
        assertTrue(productService.getSales().get(0).getIncome() == 3000);
    }

    @Test
    public void shortageTest() {

        //no purchases at all
        String[] demandOne = new String[5];
        demandOne[0] = "DEMAND";
        demandOne[1] = "Product 1";
        demandOne[2] = "2";
        demandOne[3] = "2000";
        demandOne[4] = "02.01.2000";

        String resultOne = productService.demand(demandOne);

        assertTrue(resultOne.equals("ERROR"));
        assertTrue(productService.getActualStorageEntries().size() == 0);
        assertTrue(productService.getSales().size() == 0);

        //purchase exists, but stored amount is not enough
        String[] productOne = new String[2];
        productOne[0] = "NEWPRODUCT";
        productOne[1] = "Product 1";
        productService.newProduct(productOne);

        String[] purchaseOne = new String[5];
        purchaseOne[0] = "PURCHASE";
        purchaseOne[1] = "Product 1";
        purchaseOne[2] = "1";
        purchaseOne[3] = "1000";
        purchaseOne[4] = "01.01.2000";
        productService.purchase(purchaseOne);

        String resultTwo = productService.demand(demandOne);

        assertTrue(resultTwo.equals("ERROR"));
        assertTrue(productService.getActualStorageEntries().size() == 1);
        assertTrue(productService.getSales().size() == 0);

        //purchases exists and the amount is enough, but dates don't connect
        String[] purchaseTwo = new String[5];
        purchaseTwo[0] = "PURCHASE";
        purchaseTwo[1] = "Product 1";
        purchaseTwo[2] = "1";
        purchaseTwo[3] = "1000";
        purchaseTwo[4] = "03.01.2000";
        productService.purchase(purchaseTwo);

        String resultThree = productService.demand(demandOne);

        assertTrue(resultThree.equals("ERROR"));
        assertTrue(productService.getActualStorageEntries().size() == 2);
        assertTrue(productService.getSales().size() == 0);
    }

    @Test
    public void faultyInputTest() {

        String[] productOne = new String[2];
        productOne[0] = "NEWPRODUCT";
        productOne[1] = "Product 1";
        productService.newProduct(productOne);

        String[] purchaseOne = new String[5];
        purchaseOne[0] = "PURCHASE";
        purchaseOne[1] = "Product 1";
        purchaseOne[2] = "2";
        purchaseOne[3] = "1000";
        purchaseOne[4] = "01.01.2000";
        productService.purchase(purchaseOne);

        String[] demandOne = new String[5];
        demandOne[0] = "DEMAND";
        demandOne[1] = "Product 1";
        demandOne[2] = "-1";
        demandOne[3] = "2000";
        demandOne[4] = "02.01.2000";

        String resultOne = productService.demand(demandOne);

        assertTrue(resultOne.equals("ERROR"));
        assertTrue(productService.getActualStorageEntries().size() == 1);
        assertTrue(productService.getSales().size() == 0);

        demandOne[2] = "1";
        demandOne[3] = "-2000";

        resultOne = productService.demand(demandOne);

        assertTrue(resultOne.equals("ERROR"));
        assertTrue(productService.getActualStorageEntries().size() == 1);
        assertTrue(productService.getSales().size() == 0);

        demandOne[3] = "2000";
        demandOne[1] = "";

        resultOne = productService.demand(demandOne);

        assertTrue(resultOne.equals("ERROR"));
        assertTrue(productService.getActualStorageEntries().size() == 1);
        assertTrue(productService.getSales().size() == 0);

        demandOne = null;

        resultOne = productService.demand(demandOne);

        assertTrue(resultOne.equals("ERROR"));
        assertTrue(productService.getActualStorageEntries().size() == 1);
        assertTrue(productService.getSales().size() == 0);
    }
}

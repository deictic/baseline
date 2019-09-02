package com.mystorage.tasks.serviceTests;

import com.mystorage.tasks.services.ProductService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SalesReportTests {

    private ProductService productService = new ProductService();

    @Before
    @After
    public void clear() {

        productService.clear();
    }

    @Test
    public void singleSaleReportTest() {

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
        productService.demand(demandOne);

        String[] reportOne = new String[3];
        reportOne[0] = "SALESREPORT";
        reportOne[1] = "Product 1";
        reportOne[2] = "03.01.2000";

        String resultOne = productService.salesReport(reportOne);

        assertTrue(resultOne.equals("1000"));
    }

    @Test
    public void doubleSaleReportTest() {

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
        productService.demand(demandOne);

        String[] demandTwo = new String[5];
        demandTwo[0] = "DEMAND";
        demandTwo[1] = "Product 1";
        demandTwo[2] = "1";
        demandTwo[3] = "4000";
        demandTwo[4] = "04.01.2000";
        productService.demand(demandTwo);

        String[] reportOne = new String[3];
        reportOne[0] = "SALESREPORT";
        reportOne[1] = "Product 1";
        reportOne[2] = "03.01.2000";

        String resultOne = productService.salesReport(reportOne);

        assertTrue(resultOne.equals("1000"));

        String[] reportTwo = new String[3];
        reportTwo[0] = "SALESREPORT";
        reportTwo[1] = "Product 1";
        reportTwo[2] = "05.01.2000";

        String resultTwo = productService.salesReport(reportTwo);

        assertTrue(resultTwo.equals("4000"));
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
        demandOne[2] = "1";
        demandOne[3] = "2000";
        demandOne[4] = "02.01.2000";
        productService.demand(demandOne);

        String[] reportOne = new String[3];
        reportOne[0] = "SALESREPORT";
        reportOne[1] = "Product 2";
        reportOne[2] = "03.01.2000";
        String resultOne = productService.salesReport(reportOne);
        assertTrue(resultOne.equals("ERROR"));

        reportOne[1] = "";
        resultOne = productService.salesReport(reportOne);
        assertTrue(resultOne.equals("ERROR"));

        reportOne[1] = "Product 1";
        reportOne[2] = "abc";
        resultOne = productService.salesReport(reportOne);
        assertTrue(resultOne.equals("ERROR"));

        reportOne[2] = "01.01.2000";
        resultOne = productService.salesReport(reportOne);
        assertTrue(resultOne.equals("ERROR"));
    }
}

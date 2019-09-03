package com.mystorage.tasks;

import com.mystorage.tasks.services.ProductService;

import java.util.Scanner;

public class App 
{
    public static void main( String[] args ) {

        Scanner scanner = new Scanner(System.in);

        ProductService productService = new ProductService();

        boolean exit = false;
        while (!exit) {
            System.out.println("Please input a request");

            String inputLine = scanner.nextLine();

            String requestResult = "";

            try {
                if (inputLine.equals("EXIT")) exit = true;
                else {
                    String[] request = inputLine.split(" ");

                    switch (request[0]) {
                        case "NEWPRODUCT":
                            requestResult = productService.newProduct(request);
                            break;
                        case "PURCHASE":
                            requestResult = productService.purchase(request);
                            break;
                        case "DEMAND":
                            requestResult = productService.demand(request);
                            break;
                        case "SALESREPORT":
                            requestResult = productService.salesReport(request);
                            break;
                        default:
                            requestResult = "ERROR";
                    }
                }
            } catch (Throwable t) { requestResult = "ERROR"; }

            System.out.println(requestResult);

        }
    }
}

package com.mystorage.tasks;

import com.mystorage.tasks.services.ProductService;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {

        Scanner scanner = new Scanner(System.in);

        ProductService productService = new ProductService();

        boolean exit = false;
        while (!exit) {
            System.out.println("Please input a request");

            String inputLine = scanner.nextLine();
            if (inputLine.equals("exit")) exit = true;
            else {
                String[] request = inputLine.split(" ");

                String requestResult;
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
                    case "SALSEREPORT":
                        requestResult = productService.salesReport(request);
                        break;
                    default:
                        requestResult = "ERROR";
                }

                System.out.println(requestResult);
            }

        }
    }
}

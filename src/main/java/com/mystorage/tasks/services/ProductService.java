package com.mystorage.tasks.services;

import com.mystorage.tasks.model.Sale;
import com.mystorage.tasks.model.StorageEntry;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductService {

    //Just a list of existing products
    private List<String> products = new ArrayList<>();

    //List of objects currently stored. Here we maintain only actual entries in order to simplify demand operation
    private List<StorageEntry> actual = new ArrayList<>();

    //List of successful sales. We keep it separately to simplify calculation of sales report
    private List<Sale> sales = new ArrayList<>();

    //Just a log of operations for the sake of logging. It may be useful to keep this thing around,
    //so we'll be able to check consistency
    private List<String> history = new ArrayList<>();

    public String newProduct(String[] request) {

        String result;

        try {
            String productName = request[1];
            if (!products.contains(productName)) {
                products.add(productName);
                result = "OK";
            } else result = "ERROR";
        } catch (Throwable t) {
            result = "ERROR";
        }

        return result;
    }

    public String purchase(String[] request) {

        String result;

        try {
            String productName = request[1];
            Long amount = Long.parseLong(request[2]);
            Long price = Long.parseLong(request[3]);
            String date = request[4];
            if (products.contains(productName)) {
                //calculate order
                List<StorageEntry> relevantEntries = actual.stream().filter(se -> se.getDate().equals(date))
                        .filter(se -> se.getProduct().equals(productName)).collect(Collectors.toList());
                Long order = relevantEntries.stream().map(StorageEntry::getOrder)
                        .min(Comparator.reverseOrder()).orElse(0L);
                //add entry
                actual.add(StorageEntry.builder()
                        .product(productName)
                        .amount(amount)
                        .price(price)
                        .date(date)
                        .order(order)
                        .build());
                result = "OK";
            } else result = "ERROR";
        } catch (Throwable t) {
            result = "ERROR";
        }

        return result;
    }

    public String demand(String[] request) {

        String result;

        try {
            String productName = request[1];
            Long amount = Long.parseLong(request[2]);
            Long price = Long.parseLong(request[3]);
            String date = request[4];
            StorageEntry demandEntry = StorageEntry.builder()
                    .product(productName).amount(amount).price(price).date(date).build();

            List<StorageEntry> productList = actual.stream()
                    .filter(se -> se.getProduct().equals(demandEntry.getProduct()))
                    .sorted(Comparator.comparing(StorageEntry::getDate)).collect(Collectors.toList());
            if (productList.stream().map(StorageEntry::getAmount).reduce(0L, (a, b) -> a + b)
                    >= demandEntry.getAmount() && demandEntry.getAmount() > 0 && demandEntry.getPrice() > 0) {
                Long demandAmount = demandEntry.getAmount();
                Long totalCost = 0L;
                while (demandAmount > 0) {
                    //logic of dealing with demand:
                    //select lowest order
                    //check which is greater - amount or demand
                    //if demand is greater, calc cost, lower demand then remove entry from productList and actual list
                    //otherwise calc cost, then lower actual amount by residual demand
                }
                result = "OK";
            } else result = "ERROR";
        } catch (Throwable t) {
            result = "ERROR";
        }

        return result;
    }

    public String salesReport(String[] request) {

        return null;
    }
}

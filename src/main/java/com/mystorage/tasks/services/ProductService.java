package com.mystorage.tasks.services;

import com.mystorage.tasks.dao.ProductDao;
import com.mystorage.tasks.model.Sale;
import com.mystorage.tasks.model.StorageEntry;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductService {

    private ProductDao productDao = new ProductDao();

    public String newProduct(String[] request) {

        String result;

        try {
            String productName = request[1];
            if (!productDao.productExists(productName)) {
                productDao.addProduct(productName);
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
            if (productDao.productExists(productName)) {
                //calculate order
                List<StorageEntry> relevantEntries = productDao
                        .getStorageEntryByProductAndDate(productName, date);
                Long order = relevantEntries.stream().map(StorageEntry::getOrder)
                        .min(Comparator.reverseOrder()).orElse(0L);
                //add entry
                productDao.addActualStorageEntry(StorageEntry.builder()
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

            List<StorageEntry> productList = productDao
                    .getStorageEntriesByProductSortedByDateAndOrder(demandEntry.getProduct());
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

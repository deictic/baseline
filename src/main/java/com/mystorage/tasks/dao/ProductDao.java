package com.mystorage.tasks.dao;

import com.mystorage.tasks.model.Sale;
import com.mystorage.tasks.model.StorageEntry;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDao {

    //Just a list of existing products
    private List<String> products = new ArrayList<>();

    //List of objects currently stored. Here we maintain only actual entries in order to simplify demand operation
    private List<StorageEntry> actual = new ArrayList<>();

    //List of successful sales. We keep it separately to simplify calculation of sales report
    private List<Sale> sales = new ArrayList<>();

    public Boolean productExists(String productName) {

        return products.contains(productName);
    }

    public void addProduct(String productName) {

        products.add(productName);
    }

    public List<StorageEntry> getStorageEntryByProductAndDate(String productName, String date) {

        return actual.stream().filter(se -> se.getDate().equals(date))
                .filter(se -> se.getProduct().equals(productName)).collect(Collectors.toList());
    }

    public void addActualStorageEntry(StorageEntry storageEntry) {

        actual.add(storageEntry);
    }

    public List<StorageEntry> getStorageEntriesByProductSortedByDateAndOrder(String productName) {

        return actual.stream()
                .filter(se -> se.getProduct().equals(productName))
                .sorted(Comparator.comparing(StorageEntry::getDate).thenComparing(StorageEntry::getOrder))
                .collect(Collectors.toList());
    }
}

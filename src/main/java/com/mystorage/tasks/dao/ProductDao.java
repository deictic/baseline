package com.mystorage.tasks.dao;

import com.mystorage.tasks.model.Sale;
import com.mystorage.tasks.model.StorageEntry;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
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

    public List<String> getProducts() {

        return products;
    }

    public List<StorageEntry> getStorageEntryByProductAndDate(String productName, Date date) {

        return actual.stream().filter(se -> se.getDate().equals(date))
                .filter(se -> se.getProduct().equals(productName)).collect(Collectors.toList());
    }

    public void addActualStorageEntry(StorageEntry storageEntry) {

        actual.add(storageEntry);
    }

    public void removeActualEntry(StorageEntry storageEntry) {

        actual.remove(storageEntry);
    }

    public List<StorageEntry> getStorageEntriesByProductAndDateSortedByDateAndOrder(String productName, Date date) {

        return actual.stream()
                .filter(se -> se.getProduct().equals(productName))
                .filter(se -> se.getDate().before(date))
                .sorted(Comparator.comparing(StorageEntry::getDate).thenComparing(StorageEntry::getOrder))
                .collect(Collectors.toList());
    }

    public void addSale(Sale sale) {

        sales.add(sale);
    }

    public List<Sale> findSalesByProductAndMaxDate(String productName, Date date) {

        return sales.stream().filter(s -> s.getDate().before(date))
                .filter(se -> se.getProduct().equals(productName)).collect(Collectors.toList());
    }

    public void clear() {

        products.clear();
        actual.clear();
        sales.clear();
    }

    public List<StorageEntry> getActualStorageEntries() {

        return actual;
    }

    public List<Sale> getSales() {

        return sales;
    }
}

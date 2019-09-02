package com.mystorage.tasks.services;

import com.mystorage.tasks.dao.ProductDao;
import com.mystorage.tasks.model.Sale;
import com.mystorage.tasks.model.StorageEntry;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ProductService {

    private ProductDao productDao = new ProductDao();

    public String newProduct(String[] request) {

        String result;

        try {
            String productName = request[1];
            if (!productDao.productExists(productName) && !productName.equals("")) {
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
            Date date = new SimpleDateFormat("dd.MM.yyyy").parse(request[4]);
            if (productDao.productExists(productName) && amount > 0 && price > 0) {
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
            Date date = new SimpleDateFormat("dd.MM.yyyy").parse(request[4]);
            StorageEntry demandEntry = StorageEntry.builder()
                    .product(productName).amount(amount).price(price).date(date).build();

            List<StorageEntry> productList = productDao
                    .getStorageEntriesByProductAndDateSortedByDateAndOrder(demandEntry.getProduct(),
                            demandEntry.getDate());
            if (productList.stream().map(StorageEntry::getAmount).reduce(0L, (a, b) -> a + b)
                    >= demandEntry.getAmount() && demandEntry.getAmount() > 0 && demandEntry.getPrice() > 0) {
                Long demandAmount = demandEntry.getAmount();
                Long totalCost = 0L;
                Long demandPrice = demandEntry.getPrice();
                while (demandAmount > 0) {

                    StorageEntry currentEntry = productList.stream().findFirst().orElse(null);
                    Long actualAmount = currentEntry.getAmount();
                    Long currentAmount;

                    if (demandAmount < actualAmount) {
                        currentAmount = demandAmount;
                        currentEntry.setAmount(actualAmount - demandAmount);
                        demandAmount = 0L;
                    } else {
                        currentAmount = actualAmount;
                        demandAmount -= actualAmount;
                        productDao.removeActualEntry(currentEntry);
                        productList.remove(currentEntry);
                    }

                    totalCost += currentAmount * (demandPrice - currentEntry.getPrice());
                }

                productDao.addSale(Sale.builder().product(demandEntry.getProduct()).date(demandEntry.getDate())
                        .income(totalCost).build());

                result = "OK";
            } else result = "ERROR";
        } catch (Throwable t) {
            result = "ERROR";
        }

        return result;
    }

    public String salesReport(String[] request) {

        String result;

        try {
            String productName = request[1];
            Date date = new SimpleDateFormat("dd.MM.yyyy").parse(request[2]);
            List<Sale> actualSales = productDao.findSalesByProductAndMaxDate(productName, date);

            if (!actualSales.isEmpty()) {
                result = actualSales.stream().map(Sale::getIncome).reduce(0L, (a, b) -> a + b).toString();
            } else result = "ERROR";
        } catch (Throwable t) {
            result = "ERROR";
        }

        return result;
    }

    public void clear() {

        productDao.clear();
    }

    public List<String> getProducts() {

        return productDao.getProducts();
    }

    public List<StorageEntry> getActualStorageEntries() {

        return productDao.getActualStorageEntries();
    }

    public List<Sale> getSales() {

        return productDao.getSales();
    }
}

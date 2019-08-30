package com.mystorage.tasks.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StorageEntry {

    private String product;

    private Long amount;

    private Long price;

    private String date;

    //for ordering purchases made during same day
    private Long order;
}

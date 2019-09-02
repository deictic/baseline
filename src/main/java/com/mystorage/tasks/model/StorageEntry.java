package com.mystorage.tasks.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class StorageEntry {

    private String product;

    private Long amount;

    private Long price;

    private Date date;

    //for ordering purchases made during same day
    private Long order;
}

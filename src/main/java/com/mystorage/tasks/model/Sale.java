package com.mystorage.tasks.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Sale {

    String product;

    Long income;

    Date date;
}

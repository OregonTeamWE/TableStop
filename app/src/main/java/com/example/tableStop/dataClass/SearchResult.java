package com.example.tableStop.dataClass;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchResult implements Serializable {
    public int total;
    public int offset;
    public ArrayList<ProductInfo> itemSummaries = new ArrayList<ProductInfo>();
}
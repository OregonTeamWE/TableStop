package com.example.tableStop.utils;

import android.net.Uri;
import android.util.Log;

import com.example.tableStop.dataClass.ProductInfo;
import com.example.tableStop.dataClass.SearchResult;
import com.google.gson.Gson;

public class SearchUtils {
    public final static String EBAY_SEARCH_URL = "https://api.ebay.com/buy/browse/v1/item_summary/search";

    private final static String categories = "233";

    public static String buildSearchURL(String name, int limit, int offset) {
        return Uri.parse(EBAY_SEARCH_URL).buildUpon()
                .appendQueryParameter("q", name)
                .appendQueryParameter("limit", Integer.toString(limit))
                .appendQueryParameter("offset", Integer.toString(offset))
                .appendQueryParameter("category_ids", categories)
                .toString();
    }

    static class EBAYSearchResults {
        int total;
        int limit;
        int offset;
        ResultItem[] itemSummaries;
    }

    static class ResultItem {
        String itemId;
        String title;
        Image image;
        Price price;
        String itemWebUrl;

        Image[] thumbnailImages;
        Image[] additionalImages;
    }

    static class Price {
        String value;
        String currency;
    }

    static class Image {
        String imageUrl;
    }

    public static SearchResult parseSearchResultJSON(String SearchResultJSON) {
        EBAYSearchResults results = new Gson().fromJson(SearchResultJSON, EBAYSearchResults.class);

        Log.d("Total result", String.valueOf(results.total));
        Log.d("Limit", String.valueOf(results.limit));
        Log.d("Offset", String.valueOf(results.offset));

        if (results.itemSummaries != null) {
            SearchResult data = new SearchResult();
            data.total = results.total;

            for (ResultItem listItem : results.itemSummaries) {
                ProductInfo productInfo = new ProductInfo();

                productInfo.itemId = listItem.itemId;
                productInfo.title = listItem.title;
//                productInfo.image = listItem.image.imageUrl;
                if (listItem.image != null) {
                    productInfo.image = listItem.image.imageUrl;
                } else if (listItem.thumbnailImages != null) {
                    productInfo.image = listItem.thumbnailImages[0].imageUrl;
                } else if (listItem.additionalImages != null) {
                    productInfo.image = listItem.additionalImages[0].imageUrl;
                } else {
                    // productInfo.image = null;
                }
                String currency = CurrencySign.convertTextToSign(listItem.price.currency);
                productInfo.price = currency + "" + listItem.price.value;
                productInfo.itemHref = listItem.itemWebUrl;

                data.itemSummaries.add(productInfo);
            }
            Log.d("SearchUtils: " ,"not null");
            return data;
        } else {
            Log.d("SearchUtils: " ,"null");
            return null;
        }
    }
}
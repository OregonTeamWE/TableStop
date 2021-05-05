package com.example.tableStop.utils;

import android.util.Log;

import com.example.tableStop.dataClass.ItemInfo;
import com.google.gson.Gson;

public class ItemUtils {
    private final static String EBAY_ITEM_URL = NetworkUtils.BaseUrl + "/buy/browse/v1/item/";

    public static String buildURL(String itemId) {
        String url = EBAY_ITEM_URL + itemId;
        return url;
    }

    public static class EbayItemResult {
        String itemId;
        String title;

        SearchUtils.Price price;
        ItemLocation itemLocation;
        SearchUtils.Image image;
        SearchUtils.Image[] additionalImages;

        String itemWebUrl;

        String condition;
        String brand;
        Seller seller;
    }

    public static class ItemLocation {
        String city;
        String stateOrProvince;
        String postalCode;
        String country;
    }

    public static class Seller {
        String username;
        String feedbackPercentage;
        String feedbackScore;
    }

    public static ItemInfo parseItemResultJSON(String resultJSON) {
        EbayItemResult result = new Gson().fromJson(resultJSON, EbayItemResult.class);

        ItemInfo itemInfo = new ItemInfo();

        itemInfo.itemId = result.itemId;
        itemInfo.title = result.title;

        // itemInfo.image = result.image.imageUrl;
        int additionalImagesCount = 0;
        if (result.additionalImages != null) {
            additionalImagesCount = result.additionalImages.length;
        }
        String[] images = new String[1+additionalImagesCount];
        images[0] = result.image.imageUrl;
        for (int i = 0; i < additionalImagesCount; i++) {
            images[1+i] = result.additionalImages[i].imageUrl;
        }
        itemInfo.images = images;

        itemInfo.url = result.itemWebUrl;

        Log.d("Condition", "is " + result.condition);
        Log.d("Json", "is " + resultJSON);
        Log.d("URL", "is " + result.itemWebUrl);

        String currency = CurrencySign.convertTextToSign(result.price.currency);
        itemInfo.price = currency + "" + result.price.value;

        itemInfo.seller = result.seller.username;

        itemInfo.condition = result.condition;
        itemInfo.brand = result.brand;

        return itemInfo;
    }
}
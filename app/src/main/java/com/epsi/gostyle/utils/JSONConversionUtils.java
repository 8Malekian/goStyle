package com.epsi.gostyle.utils;

import com.epsi.gostyle.model.Promotion;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class JSONConversionUtils {

    private Gson gson;
    private List<Promotion> listePromotions;

    public List<Promotion> jsonConversion(JSONArray jsonArray) {

        gson = new Gson();
        try {
            String errorCheck = jsonArray.get(0).toString();
            if (errorCheck.contains("error")) {
                return null;
            } else {
                Promotion[] test = gson.fromJson(String.valueOf(jsonArray), Promotion[].class);
                listePromotions = Arrays.asList(test);
                return listePromotions;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Clean json for cast
    public String CleanUp(String str) {
        String cleanStartBracket = str.replace("{", "");
        String cleanQuote = cleanStartBracket.replace("\"", " ");
        String cleanEndBracket = cleanQuote.replace("}", "");
        String removeFirstSpace = cleanEndBracket.substring(1);
        String strNew = removeFirstSpace.substring(0, removeFirstSpace.length() - 1);
        return strNew;
    }

    // Set valid boolean for promotion
    public List<Promotion> setValidePromotion(List<Promotion> listePromotions) {

        for (Promotion p : listePromotions) {

            if (new Date().after(p.getValidate_end_date())) {
                p.setIsValid(false);
            } else {
                p.setIsValid(true);
            }
        }
        return listePromotions;
    }
}

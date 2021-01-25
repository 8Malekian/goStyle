package com.epsi.gostyle;

import com.epsi.gostyle.model.Promotion;
import com.epsi.gostyle.utils.JSONConversionUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class JsonConversionTest {

    @Test
    public void JSONConverterExpectedSuccessToCreatedPromotionList() {
        ListePromos lp = new ListePromos();
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("id", "1");
            jsonObject1.put("discount", "-30%");
            jsonObject1.put("description", "sur les jeans et accessoires Element");
            jsonObject1.put("link", "https://www.elementbrand.fr/");
            jsonObject1.put("image_path", "image-vague.png");
            jsonObject1.put("validate_start_date", "2020-05-08");
            jsonObject1.put("validate_end_date", "2020-05-15");
            jsonObject1.put("code_id", "1");
            jsonObject1.put("created_at", "2020-05-07 13:47:49");
            jsonObject1.put("updated_at", "2020-05-27 17:12:24");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject2 = new JSONObject();
        try {
            jsonObject2.put("id", "2");
            jsonObject2.put("discount", "-40%");
            jsonObject2.put("description", "sur les accessoires Element");
            jsonObject2.put("link", "https://www.elementbrand.fr/");
            jsonObject2.put("image_path", "image-vague.png");
            jsonObject2.put("validate_start_date", "2020-05-27");
            jsonObject2.put("validate_end_date", "2020-05-30");
            jsonObject2.put("code_id", "1");
            jsonObject2.put("created_at", "2020-05-27 17:12:03");
            jsonObject2.put("updated_at", "2020-05-27 17:12:03");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject1);
        jsonArray.put(jsonObject2);

        List<Promotion> promotionList = new JSONConversionUtils().jsonConversion(jsonArray);

        Assert.assertTrue(promotionList.size() == 2);

        Assert.assertEquals(1, promotionList.get(0).getId());
        Assert.assertEquals(2, promotionList.get(1).getId());

        Assert.assertEquals("-30%", promotionList.get(0).getDiscount());
        Assert.assertEquals("-40%", promotionList.get(1).getDiscount());
    }

    @Test
    public void JSONConverterExpectedFailBecauseErrorIsRecivedByAPI() throws JSONException {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("error", "Code promo non connu");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject1);

        List<Promotion> promotionList = new JSONConversionUtils().jsonConversion(jsonArray);

        Assert.assertEquals(null, promotionList);

        String errorMessage = new JSONConversionUtils().CleanUp(jsonArray.get(0).toString());

        Assert.assertEquals("error : Code promo non connu", errorMessage);
    }
}

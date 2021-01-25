package com.epsi.gostyle;

import com.epsi.gostyle.model.Promotion;
import com.epsi.gostyle.utils.JSONConversionUtils;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ValidDatePromotionTest {

    @Test
    public void setvalidParamPromotionTest(){

        Date current =new Date();
        Date minusOne = new Date();
        Date plusOne= new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(plusOne);
        c.add(Calendar.DATE, 1);
        plusOne = c.getTime();
        c.setTime(minusOne);
        c.add(Calendar.DATE, -2);
        minusOne = c.getTime();


        Promotion promotionExpire= new Promotion("expiré","path",current,minusOne);
        Promotion promotionValid= new Promotion("valid","path",current,plusOne);

        List<Promotion> list = new ArrayList<>();
        list.add(promotionExpire);
        list.add(promotionValid);
        new JSONConversionUtils().setValidePromotion(list);
        Assert.assertFalse(list.get(0).getIsValid());
        Assert.assertTrue(list.get(1).getIsValid());
    }
}

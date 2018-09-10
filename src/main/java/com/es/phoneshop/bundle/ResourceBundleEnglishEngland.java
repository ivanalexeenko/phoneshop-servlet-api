package com.es.phoneshop.bundle;

import com.es.phoneshop.exception.EmptyFieldException;
import com.es.phoneshop.exception.LessEqualZeroAmountException;
import com.es.phoneshop.exception.NotNumberException;
import com.es.phoneshop.exception.ProductNotEnoughException;
import com.es.phoneshop.web.ProductDetailsPageServlet;

import java.util.ListResourceBundle;

public class ResourceBundleEnglishEngland extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return contents;
    }
    private static final Object[][] contents = {
            {"message.success", ProductDetailsPageServlet.SUCCESS_MESSAGE},
            {"message.not.number", NotNumberException.NOT_NUMBER_MESSAGE},
            {"message.empty.field", EmptyFieldException.EMPTY_FIELD_MESSAGE},
            {"message.less.equal.zero", LessEqualZeroAmountException.LESS_EQUAL_ZERO_AMOUNT_MESSAGE},
            {"header.success","Success!"},
            {"header.error","Error"},
            {"message.not.enough", ProductNotEnoughException.PRODUCT_NOT_ENOUGH_MESSAGE}
    };
}

package com.es.phoneshop.bundle;

import com.es.phoneshop.exception.EmptyFieldException;
import com.es.phoneshop.exception.LessEqualZeroAmountException;
import com.es.phoneshop.exception.NotNumberException;
import com.es.phoneshop.exception.ProductNotEnoughException;
import com.es.phoneshop.web.ProductDetailsPageServlet;

import java.util.ListResourceBundle;

public class ResourceBundleRussian extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return contents;
    }
    private static final Object[][] contents = {
            {"message.success", "Продукт успешно добавлен в корзину!"},
            {"message.not.number","Введенная строка не является числом, пожалуйста, попробуйте ввести число"},
            {"message.empty.field", "Поле для ввода пустое, пожалуйста, попробуйте ввести число"},
            {"message.less.equal.zero", "Количество должно быть положительным, пожалуйста, введите число больше 0"},
            {"header.success","Успех!"},
            {"header.error","Ошибка"},
            {"message.not.enough", "Извините, у нас нет такого количества данного товара на складе"}
    };
}

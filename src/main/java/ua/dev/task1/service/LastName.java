package ua.dev.task1.service;

import com.github.javafaker.Faker;
import ua.dev.task1.interfaces.IData;

public class LastName implements IData {
    @Override
    public String get() {
        return new Faker().name().lastName();
    }

    @Override
    public void parse(String str) {
        //...
    }
}

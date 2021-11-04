package ua.dev.task1.service;

import ua.dev.task1.interfaces.IData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class OneOf implements IData {
    private List<String> ones;

    @Override
    public String get() {
        return ones.get(new Random().nextInt(0, ones.size()));
    }

    @Override
    public void parse(String str) {
        ones = new ArrayList<>();
        str = str.substring(str.indexOf("(") + 1,str.indexOf(")"));
        String[] crap = { ","," ,"};
        for (String replace : crap){
            str = str.replace(replace," ").trim();
        }
        while (str.contains("  ")) {
            str = str.replace("  ", " ");
        }
        String[] values = str.split("'");
        for (var item:values) {
            if(item.length() >= 2) {
                ones.add(item);
            }
        }
    }

}

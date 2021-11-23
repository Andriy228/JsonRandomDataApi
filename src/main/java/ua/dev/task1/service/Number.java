package ua.dev.task1.service;

import ua.dev.task1.interfaces.IData;

import java.util.Random;

public class Number implements IData {
    private int from;
    private int to;

    @Override
    public String get() {
        Random random = new Random();
        int number = random.nextInt(from,to);
        return number + "";
    }

    @Override
    public void parse(String str) {
        str = GetParams.get(str);
        String[] crap = { ","," ,"};
        for (String replace : crap){
            str = str.replace(replace," ").trim();
        }
        while (str.contains("  ")) {
            str = str.replace("  ", " ");
        }
        String[] strings = str.split(" ");
        from = Integer.parseInt(strings[0]);
        to = Integer.parseInt(strings[1]);
    }
}

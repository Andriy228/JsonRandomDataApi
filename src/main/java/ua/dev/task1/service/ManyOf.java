package ua.dev.task1.service;

import ua.dev.task1.interfaces.IData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ManyOf implements IData {
    private List<String> tags;
    private int from;
    private int to;


    @Override
    public String get() {
        Random random = new Random();
        int size = random.nextInt(from,to);
        String str = "";
        List<String> newTags = new ArrayList<>();
        for (int i = 0;i < size;i++){
            newTags.add(tags.get(random.nextInt(0, tags.size())));
        }
        for (int i = 1;i<=newTags.size();i++){
            if(i< newTags.size())
            str+=newTags.get(i)+",";
            else {
                str+= newTags.get(i-1);
            }
        }
        return str;
    }

    @Override
    public void parse(String str) {
      setArray(str);
      setRange(str);
    }
    private void setArray(String str){
        str = str.substring(str.indexOf("[") + 1,str.indexOf("]"));
        String[] crap = { ","," ,"};
        for (String replace : crap){
            str = str.replace(replace," ").trim();
        }
        while (str.contains("  ")) {
            str = str.replace("  ", " ");
        }
        String[] strings = str.split("'");
        tags = new ArrayList<>();
        for (var item: strings) {
            if(item.length()>=2)
            tags.add(item);
        }
    }
    private void setRange(String str){
        str = str.substring(str.indexOf("(")+1,str.indexOf(")"));
        str = str.replace(str.substring(str.indexOf("["),str.indexOf("]")+1),"");
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

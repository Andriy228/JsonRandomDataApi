package ua.dev.task1.service;

import com.fasterxml.jackson.databind.JsonNode;
import ua.dev.task1.interfaces.IData;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public class Date implements IData {
    private String from;
    private String to;

    @Override
    public String get() {
        Long minDay = LocalDate.parse(from).toEpochDay();
        Long maxDay = LocalDate.parse(to).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);

        return randomDate.toString();
    }

    @Override
    public  void parse(String str) throws URISyntaxException, IOException {
        str = str.substring(str.indexOf("(") + 1,str.indexOf(")"));
        String[] strings = str.split(", ");
        if(strings[0].contains("'")) {
            from = subStr(strings[0]);
        }
        else if(strings[0].toCharArray()[0] == '#'){
               from = hrefToJsonKey(strings[0].replace("#",""));
        }
        else if(strings[0].contains("now")){
            from = getNowDate();
        }
        if(strings[1].contains("'")){
            to = subStr(strings[1]);
        }
        else if(strings[1].toCharArray()[0] == '#'){
                to = hrefToJsonKey(strings[1].replace("#",""));
        }
        else if(strings[1].contains("now")){
            to = getNowDate();
        }
    }

    private String hrefToJsonKey(String str) throws URISyntaxException, IOException {
        WorkWithJson workWithJson = new WorkWithJson();
        JsonNode node = workWithJson.getRoot().path(str);

        return node.textValue();
    }
    private String getNowDate(){
        return LocalDate.now().toString();
    }
    private String subStr(String str){
        return str.substring(str.indexOf("'")+1,str.indexOf("'",1));
    }
}

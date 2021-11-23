package ua.dev.task1.service;

public class GetParams {
    public static String get(String str){
        return str.substring(str.indexOf("(")+1,str.indexOf(")"));
    }
}

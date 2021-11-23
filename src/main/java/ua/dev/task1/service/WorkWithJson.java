package ua.dev.task1.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.*;
import ua.dev.task1.config.JSONConfig;
import ua.dev.task1.interfaces.IData;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Map.Entry;

public class WorkWithJson {
    private JSONConfig jsonConfig;
    private Iterator<Entry<String, JsonNode>> iterator;
    private ObjectMapper mapper;
    JsonNode nodeJson;
    IData[] objects;

    public WorkWithJson(){
        mapper = new ObjectMapper();
        nodeJson = mapper.createObjectNode();
        objects = new IData[]{new FirstName(),new LastName(),new OneOf(),new Number(),new Date(),new ManyOf()};
        jsonConfig = new JSONConfig("jsonFiles/newStructure.json");
        try {
            iterator = getRoot().fields();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonNode getRoot() throws URISyntaxException, IOException {
        return jsonConfig.getRootNode();
    }

    private String replacing(JsonNode node) throws URISyntaxException, IOException {
        if(check(node)) {
          return replaceNode(node.textValue());
        }
        else{
           return node.textValue();
        }
    }

    private String replaceNode(String value) throws URISyntaxException, IOException {
        String s = parseMethod(value);
        for (var item:objects) {
            if(s.toLowerCase(Locale.ROOT).equals(item.getClass().getSimpleName().toLowerCase(Locale.ROOT))){
                item.parse(value);
                value = item.get();
            }
        }
        return value;
    }

    private String parseMethod(String value){
        return value.substring(value.indexOf('@') + 1,value.indexOf('('));
    }

    public void copy(Iterator<Entry<String, JsonNode>> iterator){
        while (iterator.hasNext()){
            Entry<String, JsonNode> nextNode = iterator.next();
            ((ObjectNode) nodeJson).put(nextNode.getKey(),nextNode.getValue());
        }
    }

    public void changeDate(JsonNode parent,String fieldName,String newValue) {
        if (parent.has(fieldName) && check(parent.path(fieldName))) {
            ((ObjectNode) parent).put(fieldName, newValue);
        }
        if(parent.isArray()){
            for(int i = 0;i< parent.size();i++){
                changeDate(parent.get(i),fieldName,newValue);
            }
        }
        for (JsonNode child : parent) {
            changeDate(child, fieldName, newValue);
        }
    }

    public void putData(Iterator<Entry<String, JsonNode>> iterator) throws URISyntaxException, IOException {
        while (iterator.hasNext()){
            Entry<String, JsonNode> node = iterator.next();
            if(node.getValue().isArray()){
                for (int i = 0;i < node.getValue().size();i++){
                    putData(node.getValue().get(i).fields());
                }
            }
            else if(node.getValue().isObject()){
                putData(node.getValue().fields());
            }
            else {
                changeDate(nodeJson,node.getKey(),replacing(node.getValue()));
            }
        }
    }

    private boolean check(JsonNode node){
        if(node.isTextual() && node.textValue().toCharArray()[0] == '@')
            return true;
        else
            return false;
    }

    public Iterator<Entry<String, JsonNode>> getIterator() {
        return iterator;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public JsonNode getNodeJson() {
        return nodeJson;
    }
}

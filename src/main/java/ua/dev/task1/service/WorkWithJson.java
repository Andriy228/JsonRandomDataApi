package ua.dev.task1.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import ua.dev.task1.config.JSONConfig;
import ua.dev.task1.interfaces.IData;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.Map.Entry;

public class WorkWithJson {
    private JSONConfig jsonConfig;
    private Iterator<Entry<String, JsonNode>> iterator;
    private ObjectMapper mapper;
    JsonNode nodeJson;
    IData[] objects;

    {
        mapper = new ObjectMapper();
        nodeJson = mapper.createObjectNode();
        objects = new IData[]{new FirstName(),new LastName(),new OneOf(),new Number(),new Date(),new ManyOf()};
        jsonConfig = new JSONConfig("jsonFiles/structure.json");
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
    public void passage(Iterator<Entry<String, JsonNode>> iterator) throws URISyntaxException, IOException {
        this.iterator = iterator;
        while (iterator.hasNext()){
            Entry<String, JsonNode> node = iterator.next();
            if(!node.getValue().isObject()){
                if(check(node.getValue())) {
                    replaceNode(node);
                }
                else{
                    ((ObjectNode) nodeJson).put(node.getKey(), node.getValue());
                }
            }
            else {
                passage(node.getValue().fields());
            }
        }
    }
    private void replaceNode(Entry<String, JsonNode> node) throws URISyntaxException, IOException {
        String s = parseMethod(node);
        for (var item:objects) {
            if(s.toLowerCase(Locale.ROOT).equals(item.getClass().getSimpleName().toLowerCase(Locale.ROOT))){
                item.parse(node.getValue().textValue());
                ((ObjectNode) nodeJson).put(node.getKey(), item.get());
            }
        }
    }
    private String parseMethod(Entry<String, JsonNode> node){
        String str = node.getValue().textValue();
        return str.substring(str.indexOf('@') + 1,str.indexOf('('));
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

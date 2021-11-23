package ua.dev.task1;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ua.dev.task1.interfaces.IData;
import ua.dev.task1.service.*;
import ua.dev.task1.service.Number;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.BiConsumer;

import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException {
        WorkWithJson workWithJson = new WorkWithJson();
        workWithJson.copy(workWithJson.getIterator());
        workWithJson.putData(workWithJson.getNodeJson().fields());
        workWithJson.getMapper().writeValue(new File("file.json"),workWithJson.getNodeJson());
    }
}

package ua.dev.task1.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class JSONConfig {
    private JsonNode rootNode;
    private ClassLoader cldr;
    private URL path;
    private String src;

    public JSONConfig(String src){
        this.src = src;
        cldr = this.getClass().getClassLoader();
        path = cldr.getResource(src);
    }

    private File getFile() throws URISyntaxException {
        return new File(path.toURI());
    }

    public JsonNode getRootNode() throws URISyntaxException, IOException {
        JSONConfig config = new JSONConfig(src);
        ObjectMapper mapper = new ObjectMapper();
        rootNode = mapper.readTree(config.getFile());

        return rootNode;
    }
}

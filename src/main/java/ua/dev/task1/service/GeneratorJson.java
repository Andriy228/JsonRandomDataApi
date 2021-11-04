package ua.dev.task1.service;

import ua.dev.task1.interfaces.IData;

import java.io.IOException;
import java.net.URISyntaxException;

public class GeneratorJson implements IData {
    private int count;

    @Override
    public String get() {
        return null;
    }

    @Override
    public void parse(String str) throws URISyntaxException, IOException {
        count = Integer.parseInt(str);
    }
}

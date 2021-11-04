package ua.dev.task1.interfaces;

import java.io.IOException;
import java.net.URISyntaxException;

public interface IData {
    String get();
    void parse(String str) throws URISyntaxException, IOException;
}

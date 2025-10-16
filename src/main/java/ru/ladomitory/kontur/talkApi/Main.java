package ru.ladomitory.kontur.talkApi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    static void main() {
        CentralManager manager = new CentralManager();
        manager.start();
    }
}

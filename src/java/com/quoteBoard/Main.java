package com.quoteBoard;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        QuoteApplication application = new QuoteApplication();
        try {
            application.run();
        } catch (IOException ignore) {}
    }
}
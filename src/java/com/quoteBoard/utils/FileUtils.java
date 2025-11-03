package com.quoteBoard.utils;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    private static Path getPath(Long id) {
        return Path.of("db", "wiseSaying", id + ".json");
    }

    private static Path getPath() {
        return Path.of("db", "wiseSaying");
    }

    public static Long getMaxNumber() {
        Path path = Path.of("db", "wiseSaying", "lastId.txt");
        try {
            return Long.parseLong(Files.readString(path));
        } catch (Exception e) {
            putMaxNumber(1L);
        }
        return 1L;
    }
    public static void putMaxNumber(Long number) {
        Path path = Path.of("db", "wiseSaying", "lastId.txt");
        try {
            Files.writeString(path, number.toString());
        } catch (Exception ignore) { }
    }

    public static void save(Long id, String content) throws IOException {
        Path path = getPath(id);
        try {
            Files.writeString(path, content);
        } catch (NoSuchFileException e) {
            Files.createDirectories(path.getParent());
            Files.writeString(path,content);
        }
    }

    public static void saveBuild(String content) {
        Path path = Path.of("db", "wiseSaying", "data.json");
        try {
            Files.writeString(path, content);
        } catch (Exception ignore) {
        }
    }

    public static void delete(Long id) {
        try {
            Files.delete(getPath(id));
        } catch (NoSuchFileException e) {
            throw new IllegalArgumentException();
        } catch (IOException ignore) {}
    }

    public static String readOne(Long id) {
        Path path = getPath(id);
        try {
            return Files.readString(path);
        } catch (NoSuchFileException e) {
            throw new IllegalArgumentException();
        } catch (IOException ignore) {
            return null;
        }
    }

    public static List<String> readAll() {
        File dir = new File("db/wiseSaying");
        List<String> result = new ArrayList<>();
        if(!dir.exists()) return result;

        File[] files = dir.listFiles();
        if(files == null) return result;

        for(File file : files) {
            if(file.isFile() && file.getName().endsWith(".json") && !file.getName().equals("data.json")) {
                try {
                    String content = Files.readString(file.toPath());
                    result.add(content);
                } catch (IOException ignore) { }
            }
        }

        return result;
    }
}

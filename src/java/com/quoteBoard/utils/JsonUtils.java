package com.quoteBoard.utils;


import com.quoteBoard.entity.Quote;

import java.util.List;

public class JsonUtils {

    public static String entityToJson(Quote quote) {
        return String.format("""
                {
                    "id": %d,
                    "content": "%s",
                    "author": "%s"
                }
            """, quote.getId(), quote.getQuote(), quote.getWriter());
    }

    public static String build(List<String> jsons) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for(String json : jsons) {
            json = json.trim();
            sb.append("\t\t").append(json).append(",").append("\n");
        }
        if(!jsons.isEmpty()) {
            int lastCommaIdx = sb.lastIndexOf(",");
            if(lastCommaIdx != -1) sb.deleteCharAt(lastCommaIdx);
        }
        sb.append("\n").append("]");

        return sb.toString();
    }

    public static Quote jsonToEntity(String json) {
        List<String> lines = json.lines().toList();
        long id = -1L; String content = null; String author = null;

        for(String line : lines) {
            line = line.trim();
            if(line.startsWith("\"id\"")) {
                String value = line.split(":")[1].replace(",", "").trim();
                id = Long.parseLong(value);
            } else if(line.startsWith("\"content\"")) {
                content = line.split(":")[1]
                        .replace(",", "")
                        .replace("\"", "");
            } else if(line.startsWith("\"author\"")) {
                author = line.split(":")[1]
                        .replace(",", "")
                        .replace("\"", "");
            }
        }
        return new Quote(id, content, author);
    }
}

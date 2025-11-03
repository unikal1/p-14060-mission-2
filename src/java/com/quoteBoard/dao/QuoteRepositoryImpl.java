package com.quoteBoard.dao;

import com.quoteBoard.entity.Quote;
import com.quoteBoard.utils.FileUtils;
import com.quoteBoard.utils.JsonUtils;

import java.io.IOException;
import java.util.*;

/**
 * QuoteRepository 구현체
 * 로컬 메모리를 통해 데이터를 저장 및 관리한다.
 */
public class QuoteRepositoryImpl implements QuoteRepository {


    private Long cnt = 1L;

    public QuoteRepositoryImpl() {
        cnt = FileUtils.getMaxNumber();
    }

    @Override
    public Quote create(Quote quote) {
        quote.setId(cnt++);
        String json = JsonUtils.entityToJson(quote);
        try {
            FileUtils.save(quote.getId(), json);
            FileUtils.putMaxNumber(cnt);
        } catch (IOException ignore) { }

        return quote;
    }

    @Override
    public List<Quote> findAll() {
        return FileUtils.readAll().stream().map(JsonUtils::jsonToEntity).toList();
    }

    @Override
    public Optional<Quote> find(Long id) {
        try {
            String json = FileUtils.readOne(id);
            if(json == null) return Optional.empty();
            return Optional.of(JsonUtils.jsonToEntity(json));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        FileUtils.delete(id);
    }

    @Override
    public void update(Long id, String word, String writer) {
        String json = JsonUtils.entityToJson(new Quote(id, word, writer));
        try {
            FileUtils.save(id, json);
        } catch (IOException ignore) { }
    }

    @Override
    public void build() {
        String buildFile = JsonUtils.build(FileUtils.readAll());
        FileUtils.saveBuild(buildFile);
    }
}

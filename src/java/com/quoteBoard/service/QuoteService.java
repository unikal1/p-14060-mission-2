package com.quoteBoard.service;


import com.quoteBoard.dao.QuoteRepository;
import com.quoteBoard.dao.QuoteRepositoryImpl;
import com.quoteBoard.dto.CreateQuoteDto;
import com.quoteBoard.dto.ResponseQuoteDto;
import com.quoteBoard.dto.UpdateQuoteDto;
import com.quoteBoard.entity.Quote;

import java.util.List;
import java.util.Optional;

public class QuoteService {

    private final QuoteRepository quoteRepository = new QuoteRepositoryImpl();

    /**
     * 생성을 위한 서비스 메서드. repository 에 저장하기 위한 엔티티를 조립
     * @param dto 저장할 데이터
     * @return 만들어진 데이터의 id
     */
    public Long create(CreateQuoteDto dto) {
        Quote quote = new Quote(dto.quote(), dto.writer());
        quoteRepository.create(quote);

        return quote.getId();
    }

    /**
     * 현재 저장된 명언 리스트 검색 및 반환
     * @return 저장된 명언 리스트
     */
    public List<ResponseQuoteDto> getList() {
        List<Quote> quotes = quoteRepository.findAll();

        return quotes.stream()
                .map(entity ->
                        new ResponseQuoteDto(entity.getId(), entity.getWriter(), entity.getQuote()))
                .toList();
    }

    public ResponseQuoteDto get(Long id) {
        Optional<Quote> quote = quoteRepository.find(id);
        if(quote.isEmpty()) return new ResponseQuoteDto(-1L, null, null);
        else return new ResponseQuoteDto(id, quote.get().getWriter(), quote.get().getQuote());
    }

    public void delete(Long id) {
        quoteRepository.delete(id);
    }

    public void update(UpdateQuoteDto dto) {
        quoteRepository.update(dto.id(), dto.word(), dto.writer());

    }

    public void build() {
        quoteRepository.build();
    }
}

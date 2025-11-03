package com.quoteBoard.dto;

/**
 * 응답을 위한 명언 데이터
 * @param id 명언 아이디
 * @param writer 명언작가
 * @param word 명언
 */
public record ResponseQuoteDto (
        Long id, String writer, String word
) { }

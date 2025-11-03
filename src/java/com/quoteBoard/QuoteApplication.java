package com.quoteBoard;


import com.quoteBoard.dto.Command;
import com.quoteBoard.dto.CreateQuoteDto;
import com.quoteBoard.dto.ResponseQuoteDto;
import com.quoteBoard.dto.UpdateQuoteDto;
import com.quoteBoard.service.QuoteService;
import com.quoteBoard.utils.UIUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuoteApplication {
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private final QuoteService quoteService = new QuoteService();

    public void run() throws IOException {
        System.out.println(UIUtils.title());

        while(true) {
            System.out.print(UIUtils.cmdPrefix());
            String cmd = br.readLine();

            if(cmd.equals(Command.TERMINATE.label())) {   //종료
                break;
            } else if(cmd.equals(Command.REGISTER.label())) {   //등록
                System.out.print("명언 : ");
                String word = br.readLine();
                System.out.print("작가 : ");
                String writer = br.readLine();

                CreateQuoteDto dto = new CreateQuoteDto(word, writer);
                Long id = quoteService.create(dto);

                System.out.println(id + "번 명언이 등록되었습니다.");
            } else if(cmd.equals(Command.LIST.label())) {   //목록
                System.out.println("번호 / 작가 / 명언");
                System.out.println("----------------------");
                List<ResponseQuoteDto> data = quoteService.getList();
                for(ResponseQuoteDto dto : data) {
                    System.out.println(dto.id() + " / " + dto.writer() + " / " + dto.word());
                }
            } else if(cmd.startsWith(Command.DELETE.label())) {
                String value = cmd.substring(Command.DELETE.label().length());

                Long id = idParser(value);
                if(id == -1L) System.out.println("유효하지 않은 템플릿입니다.");
                try {
                    quoteService.delete(id);
                    System.out.println(id + "번 명언이 삭제되었습니다.");
                } catch (IllegalArgumentException e) {
                    System.out.println(id + "번 명언은 존재하지 않습니다.");
                }
            } else if(cmd.startsWith(Command.UPDATE.label())) {
                String value = cmd.substring(Command.UPDATE.label().length());
                Long id = idParser(value);
                if(id == -1L) System.out.println("유효하지 않은 템플릿입니다.");
                ResponseQuoteDto oldQuote = quoteService.get(id);
                if(oldQuote.id() == -1) {
                    System.out.println(id + "번 명언은 존재하지 않습니다.");
                    continue;
                }
                System.out.println("명언(기존) : " + oldQuote.word());
                System.out.print("명언 : ");
                String word = br.readLine();
                System.out.println("작가(기존) : " + oldQuote.writer());
                System.out.print("작가 : ");
                String writer = br.readLine();

                quoteService.update(new UpdateQuoteDto(id, word, writer));
            } else if(cmd.equals(Command.BUILD.label())) {
                quoteService.build();
                System.out.println("data.json 파일의 내용이 갱신되었습니다.");
            }
        }
    }

    private Long idParser(String cmd) {
        Pattern pattern = Pattern.compile("\\?id=(\\d+)");
        Matcher matcher = pattern.matcher(cmd);
        if(matcher.find()) {
            return Long.parseLong(matcher.group(1));
        }
        return -1L;
    }
}

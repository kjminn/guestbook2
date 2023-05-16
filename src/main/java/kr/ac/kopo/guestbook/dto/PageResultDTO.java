package kr.ac.kopo.guestbook.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, EN> {
    private List<DTO> dtoList;
    private int totalPage;
    private int page;//현재페이지번호
    private int size;//목록 크기

    private  int start, end;//한 화면의 시작번호, 끝번호

    private  boolean prev, next;
    private  List<Integer> pageList;

    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn){
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    private  void makePageList(Pageable pageable){
        this.page = pageable.getPageNumber() + 1;
        int tempEnd = (int)(Math.ceil(page/10.0)) * 10;
        start = tempEnd -9;

        prev = start > 1;
        end = totalPage > tempEnd ? tempEnd: totalPage;
        next = totalPage > tempEnd;
        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }

}

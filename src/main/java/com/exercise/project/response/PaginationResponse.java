package com.exercise.project.response;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PaginationResponse<T>
{
    private Integer size;
    private Integer currentPage;
    private Integer nextPage;
    private Integer previousPage;
    private Long totalElements;
    private Integer totalPages;
    private Object content;

    public PaginationResponse(Page<T> page, Object data)
    {
        size = page.getSize();
        currentPage = page.getNumber();
        nextPage = page.isLast() ? page.getTotalPages() : this.currentPage + 1;
        previousPage = page.isFirst() ? 0 : this.currentPage - 1;
        totalElements = page.getTotalElements();
        totalPages = page.getTotalPages();
        this.content = data;
    }
}

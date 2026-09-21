package org.ong.dryforest.shared.search;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class SearchResult<T> {

    private List<T> content;
    private long totalElements;
    private int totalPages;
    private int pageNumber;
    private int pageSize;
    private boolean hasNext;
    private boolean hasPrevious;

    public <R> SearchResult<R> map(Function<T, R> mapper) {
        List<R> mapped = content.stream()
                .map(mapper)
                .collect(Collectors.toList());
        return new SearchResult<>(
                mapped,
                totalElements,
                totalPages,
                pageNumber,
                pageSize,
                hasNext,
                hasPrevious
        );
    }
}
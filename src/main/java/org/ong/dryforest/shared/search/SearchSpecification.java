package org.ong.dryforest.shared.search;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public final class SearchSpecification {

    private SearchSpecification() {
    }

    public static <T> Specification<T> from(Object filters) {
        return (root, query, cb) -> SearchEngine.build(filters, cb, root);
    }

    public static <T> SearchResult<T> filterBy(
            JpaSpecificationExecutor<T> repository,
            AbstractSearchQuery request
    ) {
        var sortBy = request.resolveSortBy();
        var asc = request.resolveAscending();

        var sort = Sort.by(
                asc ? Sort.Direction.ASC : Sort.Direction.DESC,
                sortBy
        );

        var pageable = PageRequest.of(
                request.resolvePageNumber() - 1,
                request.resolvePageSize(),
                sort
        );

        var page = repository.findAll(from(request), pageable);

        return new SearchResult<>(
                page.getContent(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber() + 1,
                page.getSize(),
                page.hasNext(),
                page.hasPrevious()
        );
    }
}
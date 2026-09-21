package org.ong.dryforest.shared.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.ong.dryforest.shared.exceptions.ApiException;
import org.ong.dryforest.shared.exceptions.ErrorCode;
import org.ong.dryforest.shared.sort.SortEngine;
import org.springframework.http.HttpStatus;

import static org.ong.dryforest.shared.constants.QueryConstants.DEFAULT_PAGE_NUMBER;
import static org.ong.dryforest.shared.constants.QueryConstants.DEFAULT_PAGE_SIZE;


@Setter
@Getter
@AllArgsConstructor
public abstract class AbstractSearchQuery {
    protected Integer pageNumber;
    protected Integer pageSize;
    protected String sortBy;
    protected Boolean ascending;

    protected abstract Class<?> getEntityClass();

    public String resolveSortBy() {
        var sortableFields = SortEngine.build(getEntityClass());
        if (sortBy == null || sortBy.isBlank()) {
            return sortableFields.getKey();
        }

        if (!sortableFields.getValue().contains(sortBy)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_SORT_FIELD, "Invalid sortBy: " + sortBy + ", allowed: " + sortableFields.getValue());
        }

        return sortBy;
    }

    public int resolvePageNumber() {
        return pageNumber != null ? pageNumber : DEFAULT_PAGE_NUMBER;
    }

    public int resolvePageSize() {
        return pageSize != null ? pageSize : DEFAULT_PAGE_SIZE;
    }

    public boolean resolveAscending() {
        return ascending != null ? ascending : true;
    }
}
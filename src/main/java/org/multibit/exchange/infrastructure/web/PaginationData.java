package org.multibit.exchange.infrastructure.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


import static com.google.common.base.Preconditions.checkArgument;

/**
 * <p>Value object to provide pagination data to Freemarker macros</p>
 *
 * @since 0.0.1
 *
 */
@JsonPropertyOrder({"pageNumber","pageCount","itemCount","pageSize","sortField","sortDirection"})
public class PaginationData {

  public enum SortDirection {
    ASCENDING,
    DESCENDING
  }

  private final int pageNumber;
  private final int pageSize;
  private final long itemCount;
  private final long pageCount;
  private final SortDirection sortDirection;
  private final String sortField;

  @JsonCreator
  public PaginationData(
    @JsonProperty("pageNumber")
    int pageNumber,
    @JsonProperty("pageSize")
    int pageSize,
    @JsonProperty("itemCount")
    long itemCount,
    @JsonProperty("sortDirection")
    SortDirection sortDirection,
    @JsonProperty("sortField")
    String sortField) {

    checkArgument(pageSize > 0, "pageSize must be greater than 0");
    checkArgument(itemCount >= 0, "itemCount must be positive");
    checkArgument(pageNumber >= 0, "pageNumber must be positive");
    pageCount = calculatePageCount(itemCount, pageSize);

    // must be strictly less than because pageNumber is 0 based
    checkArgument((pageCount == 0 && pageNumber == 0) || pageNumber < pageCount, "pageNumber must be less than pageCount");

    this.pageNumber = pageNumber;
    this.pageSize = pageSize;
    this.itemCount = itemCount;
    this.sortDirection = sortDirection;
    this.sortField = sortField;
  }

  public int getPageNumber() {
    return pageNumber;
  }

  public int getPageSize() {
    return pageSize;
  }

  public long getItemCount() {
    return itemCount;
  }

  @JsonProperty("pageCount")
  public long getPageCount() {
    return pageCount;
  }

  public SortDirection getSortDirection() {
    return sortDirection;
  }

  public String getSortField() {
    return sortField;
  }

  private long calculatePageCount(long itemCount, int pageSize) {
    if (itemCount % pageSize == 0) {
      return itemCount / pageSize;
    } else {
      return (itemCount / pageSize) + 1;
    }
  }
}

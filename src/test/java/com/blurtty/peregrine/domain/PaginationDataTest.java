package com.blurtty.peregrine.domain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.blurtty.peregrine.infrastructure.dropwizard.common.PaginationData;


import static org.junit.Assert.assertEquals;

public class PaginationDataTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void test_CreatePaginationData_PageSizeZero() {
    // Arrange
    int badPageSize = 0;
    int somePageNumber = 5;
    long someNumberOfItems = 5L;

    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("pageSize must be greater than 0");

    // Act
    new PaginationData(somePageNumber, badPageSize, someNumberOfItems, PaginationData.SortDirection.ASCENDING, "sort_field");

  }

  @Test
  public void test_CreatePaginationData_PageNumberNegative() {
    // Arrange
    int somePageSize = 10;
    int badPageNumber = -5;
    long someNumberOfItems = 5L;

    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("pageNumber must be positive");

    // Act
    new PaginationData(badPageNumber, somePageSize, someNumberOfItems, PaginationData.SortDirection.ASCENDING, "sort_field");

  }

  @Test
  public void test_CreatePaginationData_NumberOfItemsNegative() {
    // Arrange
    int somePageSize = 10;
    int somePageNumber = 5;
    long badNumberOfItems = -5L;

    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("itemCount must be positive");

    // Act
    new PaginationData(somePageNumber, somePageSize, badNumberOfItems, PaginationData.SortDirection.ASCENDING, "sort_field");

  }

  @Test
  public void test_getPageCount_ZeroItems_TenPerPage() {
    // Arrange
    int pageSize = 10;
    long itemCount = 0;
    long expectedPageCount = 0;
    PaginationData paginationData = getPaginationDataWithPageSizeAndItemCount(pageSize, itemCount);

    // Act
    long actualPageCount = paginationData.getPageCount();

    // Assert
    assertEquals(expectedPageCount, actualPageCount);
  }

  @Test
  public void test_getPageCount_ZeroItems_OnePerPage() {
    // Arrange
    int pageSize = 1;
    long itemCount = 0;
    long expectedPageCount = 0;
    PaginationData paginationData = getPaginationDataWithPageSizeAndItemCount(pageSize, itemCount);

    // Act
    long actualPageCount = paginationData.getPageCount();

    // Assert
    assertEquals(expectedPageCount, actualPageCount);
  }

  @Test
  public void test_getPageCount_OneItem_OnePerPage() {
    // Arrange
    int pageSize = 1;
    long itemCount = 1;
    long expectedPageCount = 1;
    PaginationData paginationData = getPaginationDataWithPageSizeAndItemCount(pageSize, itemCount);

    // Act
    long actualPageCount = paginationData.getPageCount();

    // Assert
    assertEquals(expectedPageCount, actualPageCount);
  }

  @Test
  public void test_getPageCount_OneItem_TenPerPage() {
    // Arrange
    int pageSize = 1;
    long itemCount = 1;
    long expectedPageCount = 1;
    PaginationData paginationData = getPaginationDataWithPageSizeAndItemCount(pageSize, itemCount);

    // Act
    long actualPageCount = paginationData.getPageCount();

    // Assert
    assertEquals(expectedPageCount, actualPageCount);
  }

  @Test
  public void test_getPageCount_ThreeItems_OnePerPage() {
    // Arrange
    int pageSize = 1;
    long itemCount = 3;
    long expectedPageCount = 3;
    PaginationData paginationData = getPaginationDataWithPageSizeAndItemCount(pageSize, itemCount);

    // Act
    long actualPageCount = paginationData.getPageCount();

    // Assert
    assertEquals(expectedPageCount, actualPageCount);
  }

  @Test
  public void test_getPageCount_ThreeItems_TwoPerPage() {
    // Arrange
    int pageSize = 2;
    long itemCount = 3;
    long expectedPageCount = 2;
    PaginationData paginationData = getPaginationDataWithPageSizeAndItemCount(pageSize, itemCount);

    // Act
    long actualPageCount = paginationData.getPageCount();

    // Assert
    assertEquals(expectedPageCount, actualPageCount);
  }

  @Test
  public void test_getPageCount_ThreeItems_ThreePerPage() {
    // Arrange
    int pageSize = 3;
    long itemCount = 3;
    long expectedPageCount = 1;
    PaginationData paginationData = getPaginationDataWithPageSizeAndItemCount(pageSize, itemCount);

    // Act
    long actualPageCount = paginationData.getPageCount();

    // Assert
    assertEquals(expectedPageCount, actualPageCount);
  }

  @Test
  public void test_getPageCount_ThreeItems_FourPerPage() {
    // Arrange
    int pageSize = 4;
    long itemCount = 3;
    long expectedPageCount = 1;
    PaginationData paginationData = getPaginationDataWithPageSizeAndItemCount(pageSize, itemCount);

    // Act
    long actualPageCount = paginationData.getPageCount();

    // Assert
    assertEquals(expectedPageCount, actualPageCount);
  }

  private PaginationData getPaginationDataWithPageSizeAndItemCount(int pageSize, long itemCount) {
    return new PaginationData(0, pageSize, itemCount, PaginationData.SortDirection.ASCENDING, "sort_field");
  }

}
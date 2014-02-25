package org.multibit.exchange.domain.model;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class SideTest {

  @Test
  public void onlyTwoSides() {

    // Assert
    assertThat(Side.values().length).isEqualTo(2);
  }
}

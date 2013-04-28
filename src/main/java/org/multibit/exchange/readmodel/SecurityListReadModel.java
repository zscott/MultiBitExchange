package org.multibit.exchange.readmodel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

/**
 * <p>A ReadModel for representing a list of securities:</p>
 *
 * @since 0.0.1
 *         
 */
@JsonPropertyOrder({"count","securities"})
public class SecurityListReadModel {

  private final List<SecurityReadModel> securities;

  @JsonCreator
  public SecurityListReadModel(
      @JsonProperty("securities") List<SecurityReadModel> securities) {
    this.securities = securities;
  }

  @JsonProperty
  public List<SecurityReadModel> getSecurities() {
    return securities;
  }

  @JsonProperty
  public int getCount() {
    return securities.size();
  }
}

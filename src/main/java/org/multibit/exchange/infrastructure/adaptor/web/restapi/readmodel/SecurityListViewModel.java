package org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

/**
 * <p>A ReadModel for representing a list of currency pairs:</p>
 *
 * @since 0.0.1
 *  
 */
@JsonPropertyOrder({"count", "pairs"})
public class SecurityListViewModel {

  private final List<CurrencyPairReadModel> pairs;

  @JsonCreator
  public SecurityListViewModel(
          @JsonProperty("pairs") List<CurrencyPairReadModel> pairs) {
    this.pairs = pairs;
  }

  @JsonProperty
  public List<CurrencyPairReadModel> getPairs() {
    return pairs;
  }

  @JsonProperty
  public int getCount() {
    return pairs.size();
  }
}

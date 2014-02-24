package org.multibit.exchange.infrastructure.adaptor.api.readmodel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.multibit.common.Entity;

/**
 * <p>ReadModel to provide the following to the application:</p>
 * <ul>
 * <li>A read-only representation of a security</li>
 * </ul>
 *
 * @since 0.0.1
 *  
 */
@JsonPropertyOrder({"exchangeId", "ticker", "baseCurrency", "counterCurrency"})
public class CurrencyPairReadModel implements Entity<String> {

  @JsonProperty("_id")
  private String _id;
  private String exchangeId;
  private String ticker;
  private String baseCurrency;
  private String counterCurrency;

  @JsonCreator
  public CurrencyPairReadModel() {
  }

  public CurrencyPairReadModel(
      String _id,
      String exchangeId,
      String ticker,
      String baseCurrency,
      String counterCurrency) {
    this._id = _id;
    this.exchangeId = exchangeId;
    this.ticker = ticker;
    this.baseCurrency = baseCurrency;
    this.counterCurrency = counterCurrency;
  }

  @JsonIgnore
  public String getId() {
    return _id;
  }

  @Override
  public void setId(String id) {
    this._id = id;
  }

  public String getTicker() {
    return ticker;
  }

  public String getExchangeId() {
    return exchangeId;
  }

  public String getBaseCurrency() {
    return baseCurrency;
  }

  public String getCounterCurrency() {
    return counterCurrency;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CurrencyPairReadModel that = (CurrencyPairReadModel) o;

    if (!counterCurrency.equals(that.counterCurrency)) return false;
    if (!baseCurrency.equals(that.baseCurrency)) return false;
    if (!ticker.equals(that.ticker)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = ticker.hashCode();
    result = 31 * result + baseCurrency.hashCode();
    result = 31 * result + counterCurrency.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "CurrencyPairReadModel{" +
        "_id='" + _id + '\'' +
        ", ticker='" + ticker + '\'' +
        ", exchangeId='" + exchangeId + '\'' +
        ", baseCurrency='" + baseCurrency + '\'' +
        ", counterCurrency='" + counterCurrency + '\'' +
        '}';
  }
}

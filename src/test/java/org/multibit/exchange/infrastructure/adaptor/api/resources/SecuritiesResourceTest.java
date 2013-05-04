package org.multibit.exchange.infrastructure.adaptor.api.resources;

import java.util.List;
import org.junit.Test;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.SecurityListReadModel;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.SecurityReadModel;


import static org.fest.assertions.api.Assertions.assertThat;

public class SecuritiesResourceTest extends BaseResourceTest {

  @Test
  public void testAddSecurity() {
    // Arrange
    final SecurityDescriptor securityDescriptor = createValidSecurityDescriptor();

    // Act
    securitiesResource.addSecurity(getExchangeIdName(), securityDescriptor);

    // Assert
    assertCreateSecurityCalled(exchangeService, securityDescriptor);
  }

  @Test
  public void testGetSecurities() {
    // Arrange
    final int expectedCount = 0;

    // Act
    SecurityListReadModel securityListReadModel = securitiesResource.getSecurities(getExchangeIdName());

    // Assert
    assertThat(securityListReadModel).isNotNull();
    List<SecurityReadModel> securities = securityListReadModel.getSecurities();
    assertThat(securities).isNotNull();
    assertThat(securities.size()).isEqualTo(expectedCount);
  }

}

package org.multibit.exchange.infrastructure.adaptor.web.app.view;

import com.yammer.dropwizard.views.View;

public class AppView extends View {

  public AppView() {
    super("/views/ftl/app/index.ftl");
  }
}

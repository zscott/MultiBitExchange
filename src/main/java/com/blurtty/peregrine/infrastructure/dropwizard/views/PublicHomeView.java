package com.blurtty.peregrine.infrastructure.dropwizard.views;

import com.google.common.base.Preconditions;

import com.blurtty.peregrine.infrastructure.dropwizard.common.HomeModel;

/**
 * <p>View to provide the following to market:</p>
 * <ul>
 * <li>Representation provided by a Freemarker template with a given common</li>
 * </ul>
 * <p>Presenting the view in this manner assists with Freemarker debugging</p>
 *
 * @since 0.0.1
 */
public class PublicHomeView extends PublicFreemarkerView<HomeModel> {

  /**
   * @param templateName The template name (no leading "/")
   * @param model The backing common for the view
   */
  public PublicHomeView(String templateName, HomeModel model) {
    super(templateName, model);
    Preconditions.checkArgument(templateName.startsWith("common"));
  }
}

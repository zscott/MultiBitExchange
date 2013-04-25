package org.multibit.exchange.infrastructure.guice.annotation;

import com.google.inject.BindingAnnotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;


import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <p>Annotation to provide the following to Guice & {@link: BaseResource}:</p>
 * <ul>
 * <li>Ability to inject the default locale</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
@Documented
@BindingAnnotation
public @interface DefaultLocale {}

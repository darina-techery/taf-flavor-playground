package utils.annotations;

import data.Platform;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark tests
 * which are applicable to certain platforms only by design.
 * <br/>
 * To mark the tests which are temporarily disabled,
 * use annotation {@link SkipOn}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RunOn {
	Platform[] platforms() default {};
}

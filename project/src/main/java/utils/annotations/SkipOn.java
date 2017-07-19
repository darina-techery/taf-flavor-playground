package utils.annotations;

import data.Platform;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation marks temporarily disabled tests
 * which must be enabled back after the blocking jiraIssue is fixed
 * <br/>
 * To mark tests restricted to platform by design,
 * use {@link RunOn}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface SkipOn {

	/**
	 * @return list of platforms for which the test is disabled now
	 */
	Platform[] platforms() default {};

	/**
	 * @return jira ticket for the issue blocking this test
	 */
	String jiraIssue();


	/**
	 * @return explanation of reason why the test is disabled
	 */
	String reason() default "";
}

package utils.log;

public interface CommonLogMessages {
	static String formatMessage(String message, String... parameters){
		String result = message.replace("{}", "%s");
		return String.format(result, parameters);
	}

	/**
	 * Example: verify response code is 204 (successfully fulfilled)\n
	 * \tAssert.assertThat(messageWhenNotEqual("response code"), responseCode, is(204))
	 * @param name - asserted value description
	 * @return an error message for failed "equals" assertion,
	 * when actual value is different from expected
	 */
	default String messageWhenNotEqual(String name, String... parameters){
		return String.format("[%s] mismatch",
				formatMessage(name, parameters));
	}

	/**
	 * Example: verify response code is not 400 (bad request)\n
	 * \tAssert.assertThat(messageWhenEqual("response code"), responseCode, is(not(400)))
	 * @param name - asserted value description
	 * @return an error message for failed "not equals" assertion
	 * when actual value is same as (not) expected
	 */
	default String messageWhenEqual(String name, String... parameters){
		return String.format("[%s] was expected different, but same found",
				formatMessage(name, parameters));
	}

	/**
	 * Example: verify menu is shown\n
	 * \tAssert.assertThat(messageWhenNotDisplayed("main menu"), is())
	 * @param elementName - tested element description
	 * @return an error message for case when element was not displayed
	 */
	default String messageWhenNotDisplayed(String elementName, String... parameters){
		return String.format("[%s] should be displayed, but was not found",
				formatMessage(elementName, parameters));
	}

	/**
	 * Example: verify menu is hidden\n
	 * \tAssert.assertThat(messageWhenDisplayed("main menu"), is())
	 * @param elementName - tested element description
	 * @return an error message for case when element was displayed
	 * when not expected
	 */
	default String messageWhenDisplayed(String elementName, String... parameters){
		return String.format("[%s] was visible, but should be hidden",
				formatMessage(elementName, parameters));
	}

	/**
	 * @return current methodWithReturn name
	 */
	default String getMethodName(){
		int depthCountForPreviousMessage = 1;
		return getMethodName(depthCountForPreviousMessage);
	}

	/**
	 * @param depthCount - number of parent methods in hierarchy to skip
	 * @return parent methodWithReturn name.
	 */
	default String getMethodName(int depthCount){
		int startingStackIndexCount = 2;
		depthCount += startingStackIndexCount;
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		return stackTraceElements[depthCount].getMethodName();
	}

	/**
	 * @param element: stack trace element
	 * @return calculate name
	 */
	default String getMethodName(StackTraceElement element) {
		return element.getMethodName();
	}


	/**
	 * @param element: stack trace element
	 * @return clickable link for logging: contains filename and line number
	 */
	default String getFileName(StackTraceElement element) {
		return String.format(".(%s:%d)",
				element.getFileName(),
				element.getLineNumber());
	}

	default String messageForCalledAt(StackTraceElement e){
		return String.format("For %s in %s", getMethodName(e), getFileName(e));
	}
}

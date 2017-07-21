package com.techery.dtat.utils.log;

public interface CommonLogMessages {
	/**
	 * Example: verify menu is shown\n
	 * \tAssert.assertThat(messageWhenNotDisplayed("main menu"), is())
	 * @param elementName - tested element description
	 * @return an error message for case when element was not displayed
	 */
	default String messageWhenNotDisplayed(String elementName){
		return String.format("[%s] should be displayed, but was not found", elementName);
	}

	/**
	 * Example: verify menu is hidden\n
	 * \tAssert.assertThat(messageWhenDisplayed("main menu"), is())
	 * @param elementName - tested element description
	 * @return an error message for case when element was displayed
	 * when not expected
	 */
	default String messageWhenDisplayed(String elementName){
		return String.format("[%s] was visible, but should be hidden", elementName);
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

	default String getErrorDescription(Throwable t) {
		StringBuilder textBuilder = new StringBuilder(t.getMessage());
		for (StackTraceElement ste : t.getStackTrace()) {
			textBuilder.append("\n\t").append("at ").append(ste.toString());
		}
		return textBuilder.toString();
	}
}

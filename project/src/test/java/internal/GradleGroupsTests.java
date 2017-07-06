package internal;

import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Step;

import java.lang.reflect.Method;

public class GradleGroupsTests {
	@Test(groups = "A")
	public void testA() {
		test("A");
	}

	@Test(groups = "B")
	public void testB() {
		test("B");
	}

	@Test
	public void testNoGroup() {
		test("No group");
	}

	@Step("Test '{0}'")
	public void test(String arg){
		System.out.println(arg);
	}

	@AfterClass(alwaysRun = true)
	public void logExecutedTests(ITestContext testContext){
		ITestNGMethod[] methods = testContext.getAllTestMethods();
		for (ITestNGMethod method : methods) {
			if (method.isTest()) {
				Method m = method.getConstructorOrMethod().getMethod();
				System.out.println("Method "+m.getName()+" was called "+ method.getCurrentInvocationCount()+" times.");
			}
		}
	}

}

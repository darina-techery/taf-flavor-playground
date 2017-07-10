package utils.waiters;

import data.Configuration;
import driver.HasDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class ByWait<R> extends BaseWait<By, R> implements HasDriver {
	private WebElement parent;

	final Function<By, MobileElement> singleElementSearch =
			(locator) -> getSearchContext().findElement(locator);
	final Function<By, List<MobileElement>> multiElementSearch =
			(locator) -> getSearchContext().findElements(locator);
	final Predicate<By> elementFound =
			(locator) -> !getSearchContext().findElements(locator).isEmpty();

	public ByWait() {
		super();
		addIgnorableException(NoSuchElementException.class);

	}

	@Nullable
	@Override
	public R go() {
		if (!Configuration.isAndroid()
				&& preconditionWithoutTestableObject == null
				&& preconditionWithTestableObject == null
				&& testableObject != null){
			setDefaultPrecondition();
		}
		return super.go();
	}

	@Override
	protected String describeTestableObject() {
		return testableObject == null ? "" : testableObject.toString();
	}

	public ByWait<R> parent(WebElement parent) {
		this.parent = parent;
		return this;
	}

	public ByWait<R> findAndCalculate(Function<MobileElement, R> operation) {
		this.calculate(by -> {
			MobileElement element = this.singleElementSearch.apply(testableObject);
			return operation.apply(element);
		});
		return this;
	}

	public ByWait<R> findAndExecute(Consumer<MobileElement> operation) {
		this.execute(by -> {
			MobileElement element = this.singleElementSearch.apply(testableObject);
			operation.accept(element);
		});
		return this;
	}

	public ByWait<R> findAllAndCalculate(Function<List<MobileElement>, R> operation) {
		this.calculate(by -> {
			List<MobileElement> elements = this.multiElementSearch.apply(testableObject);
			return operation.apply(elements);
		});
		return this;
	}

	public ByWait<R> findAllAndExecute(Consumer<List<MobileElement>> operation) {
		this.execute(by -> {
			List<MobileElement> elements = this.multiElementSearch.apply(testableObject);
			operation.accept(elements);
		});
		return this;
	}

	@Override
	protected void setDefaultPrecondition() {
		when(elementFound);
	}

	private SearchContext getSearchContext(){
		return (parent != null) ? parent : getDriver();
	}

}

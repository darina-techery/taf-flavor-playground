# Table of contents
1. [Build](#build)
2. [Execution](#execution)
3. [Project Structure](#project-structure)
    1. [Screens - Actions - Steps - Tests](#screens-actions-steps-tests)
        1. [Screen](#screen)
        2. [Actions](#actions)
        3. [Steps](#steps)
        4. [Tests](#tests)
            1. [Metadata in tests](#metadata-in-tests)
            2. [Assertions](#assertions)
    2. [Driver](#driver)
    3. [Waiters](#waiters)
        1. [Predefined actions: Waiter](#predefined-actions-waiter)
        2. [Custom actions: AnyWait<T,R>](#custom-actions-any-wait)
        

## Build <a name="build"/>
Each time you add new Actions class (see Actions section below), the project has to be rebuild by Gradle to generate missing classes.
To rebuild the project, execute command 

`./gradlew clean compileJava compileTestJava`

Simply: if your project says it cannot find some files, just execute this command first.
In any other case it's okay to use default Build option in IDE.


## Execution <a name="execution"/>
To run certain test or test class from IDE, it's ok to use default Run option.

To execute tests from command line, run command

`./gradlew clean compileJava compileTestJava test`

Optional parameters (passed via JVM args, starting from -D):
* `suite` to specify .xml file in src/test/resources/suites. 
<br/>**Default:** suite.xml
<br/>**Example:**`./gradlew clean compileJava compileTestJava test -Dsuite=internal-suite.xml`
* `groups` to specify test groups included in test run.
<br/>__Default:__ none (all tests will be included)
<br/>__Example:__`./gradlew clean compileJava compileTestJava test -Dgroups=A,B`
* `excludeGroups` to specify test groups excluded from test run.
<br/>__Default:__ none (no tests will be excluded)
<br/>__Example:__`./gradlew clean compileJava compileTestJava test -Dgroups=A`

If group name contains spaces, enclose argument in quotes.
<br/>__Example:__`./gradlew clean compileJava compileTestJava test "-Dgroups=group with spaces,A" -Dsuite=internal-suite.xml`

## Project Structure <a name="project-structure"/>
### Screens - Actions - Steps - Tests <a name="screens-actions-steps-tests"/>
#### Screen <a name="screen"/>
Screen contains UI mapping for the whole screen (like DreamTripsScreen) or its part (like NavigationMenu).
1. Screen classes should extend `BaseUiModule`, which contains basic PageObject compilation instructions.
2. Screen classes contain locators for Android and iOS.
3. Element names should have prefixes to reflect element type: _btn_ for button, _lbl_ for label, _txt_ for editable text etc.
4. Locator hints:
```java
//__Element__:
    @AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc='Menu Opened']")
    @iOSFindBy(accessibility = "menu_more")
    public MobileElement menuButton;
    
//__List__:
    @iOSFindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeButton")
    public List<MobileElement> buttons;
    
//__Path to element through its parent__: find alertView first, then find its child button Logout
    @HowToUseLocators(iOSAutomation = LocatorGroupStrategy.CHAIN)
    @iOSFindBy(accessibility = "alertView")
    @iOSFindBy(accessibility = "Logout")
    public MobileElement btnLogout;
```
5. Use xpath as last resort, it is not only slow, but also unstable on iOS. If possible, 
choose accessibility strategy for iOS.
6. (iOS) If some element has no accessibilityId, communicate with QA Automation team to create 
a task for adding it.
#### Actions <a name="actions"/>
Actions contain interactions with UI. 
Each interaction is 1 simple user action, which consists of several operations with UI elements 
(e.g. submit login credentials contains *setText* for login field, *setText* for password field, *click* for submit button).
1. Actions classes may extend BaseUiActions class to have method waitForScreen(), or extend nothing.
2. If actions are different for Android and iOS, this class (SomeActionsClass) must be abstract.
Implement different actions for iOS and Android in child classes named DroidSomeActionsClass and IOSSomeActionsClass.
```java
public abstract class SomeActions extends BaseUiActions {
    //declare screen for this set of actions
    protected SomeScreen someScreen = new SomeScreen()
    
    //this method is same for Android and iOS
    public void confirmRequest() {
        new Waiter().check(someScreen.chkAccept); //see Waiters section below
        someScreen.btnSubmit.click();
    }
    
    //this method is different for Android and iOS
    public abstract void changeMode();
}

public class IOSSomeActions extends SomeActions {
    private SomeContextMenu someContextMenu;
    @Override 
    public void changeMode() {
        someScreen.btnContextMenu.click();
        someContextMenu.modeSwitcher.click();
    }
}

public class DroidSomeActions extends SomeActions {
    @Override 
    public void changeMode() {
        someScreen.btnSwitchMode.click();
    }
}
```
3. __Note:__ device-specific Actions classes (IOS- and Droid-) should be named __strictly__ with IOS- and Droid- prefixes. 
Otherwise, code generation will fail. 

#### Steps <a name="steps"/>
Steps aggregate Actions to form sequences of actions based on business logic. They can basically be described as "User does something".

Example: User logs in with valid credentials.
<br/>Action 1: submit login credentials
<br/>Action 2: abort test if Login screen remains visible.
<br/>Action 3: dismiss pop-ups

1. Steps class may not extend anything if not needed.
2. Constructor for Steps class should be marked with `@UseActions` annotation.
3. All Actions involved in Steps should be passed as arguments to constructor:
```java
public class DreamTripsSteps {
    private final DreamTripsActions tripsListActions;
    private final NavigationActions navigationActions;
    private final DreamTripDetailsActions tripDetailsActions;

    
    @UseActions
    //--All actions are generated by Dagger, you don't have to call new() for them. 
    public DreamTripsSteps(DreamTripsActions tripsListActions,
                           NavigationActions navigationActions,
                           DreamTripDetailsActions tripDetailsActions){
        this.tripsListActions = tripsListActions;
        this.navigationActions = navigationActions;
        this.tripDetailsActions = tripDetailsActions;
    }
}
```
4. Add `@Step` annotation to describe steps in Allure report
```java
@Step("Go to Dream Trips screen")
public void openDreamTripsScreen() {
    navigationActions.selectMenuItem(MenuItem.DREAM_TRIPS);
    tripsListActions.waitForScreen();
}
```
5. `@Step` annotation can include arguments from methods referred to as '{0}', '{1}' etc.
```java
@Step("Open Trip by name '{0}'")
public void openTripByName(String tripName) {...}
```

#### Tests <a name="tests"/>
Tests contain test methods with metadata (references to Jira tickets, test groups, enable/disable flags etc.)
Tests operate Test Steps, which are accessible by `getStepsComponent().***Steps();`

**NOTE**: DO NOT create Steps via `steps = new ***Steps()`, because Steps are constructed by 
Dagger.

There are 3 base classes for tests:
* `BaseTest` provides access to test steps. Use it for basic internal self-tests.
```java
public class MySimpleTest extends BaseTest {
	MySimpleSteps steps = getStepsComponent().mySimpleSteps();
	@Test
	public void test(){
		steps.runSimpleScenarios();
		steps.executeSimpleAssert();
	}
}
```
* `BaseTestWithDriver` also includes AppiumDriver management. Every test in a class based on 
`BaseTestWithDriver` will start from login screen.
Use `BaseTestWithDriver` for test classes with tests about login.
```java
public final class LoginTests extends BaseTestWithDriver implements LogProvider {

	private LoginSteps loginSteps = getStepsComponent().loginSteps();
	private NavigationSteps navigationSteps = getStepsComponent().navigationSteps();

	@Test
	public void loginToApp() {
		//application is already launched, login screen shown
		loginSteps.loginIfRequired(defaultUser);
		navigationSteps.assertLandingPageLoaded();
	}
}
```
* `BaseTestAfterLogin` also includes login operation. Every test in a class based on 
`BaseTestAfterLogin` will start from landing page.
```java
public final class LogoutTests extends BaseTestAfterLogin implements LogProvider {

	private LoginSteps loginSteps = getStepsComponent().loginSteps();
	private NavigationSteps navigationSteps = getStepsComponent().navigationSteps();

	@Test
	public void logoutFromApp() {
		//application is launched, user is logged in, landing page is shown
		navigationSteps.logoutUser();
		Assert.assertThat("Login screen should be active after logout from application.",loginSteps.isScreenActive());
	}
}
```
##### Metadata in tests <a name="metadata-in-tests"/>
Add annotations to provide useful information about your tests.
1. `@Test(groups = {...})` - list all groups this test belongs to.
2. `@RunOn(platforms = {Platform.IPHONE, Platform.ANDROID_PHONE})` 
- use this annotation, if test is designed only for certain platforms (e.g. there is no such screen
on another platform). 
If the test is marked with `@RunOn`, it will only be executed for platforms from given list. 
It will not affect local runs via IDE, only command-line runs from Gradle (local and on CI).
3. `@SkipOn(platforms = {Platform.ANY}, jiraIssue="link to Jira issue blocking it%")` 
- use this annotation when test is blocked by some bug. It will not let the test be executed 
on listed platforms. This annotation should be removed when the issue is fixed. 
It will not affect local runs via IDE, only command-line runs from Gradle (local and on CI).
4. `@TestCaseId("link to test in TestRail")` contains reference to this test in TestRail.
5. `@Issue("link to Jira ticket")` contains reference to task in Jira (it can be a task for 
QA Automation team or a bug which introduced the necessity of this test).
6. `@Test(enabled = false)` - add this argument to *temporarily* disable test for local execution.
Do not commit disabled tests.

Example:
```java
@Test
@TestCaseId("https://techery.testrail.net/index.php?/cases/view/213520")
@Issue("https://techery.atlassian.net/browse/DTAUT-421")
//suppose this test is restricted to Android only by design
@RunOn(platforms = {Platform.ANDROID_PHONE, Platform.ANDROID_TABLET})
//suppose this page crashes on tablets
@SkipOn(platforms = {Platform.ANDROID_TABLET}, issue = ".../DTAUT-xxx")
public void checkTripDetails() throws IOException {
    dreamTripsSteps.openDreamTripsScreen();
    dreamTripsSteps.openPredefinedTripByName(expectedTripName);
    dreamTripsSteps.assertAllTripDetailsAreDisplayed(expectedTrip);
}
```

##### Assertions <a name="assertions"/>
1. Use method Assert.assertThat from project (utils.runner), not hamcrest or testng package, because 
our method also takes screenshot for Allure when assertion fails.
2. All assertion messages have to follow the form "Something should be this way (or NOT this way)", 
e.g. "Dream Trips screen should be visible/should NOT be visible".
3. Multiple assertions can be written in separate assert statements only if the 1st one (if fails) blocks the 2nd one.
Example: assert that user is not null, assert that username is John (if user == null, we cannot check username).
4. If multiple assertions are of the same rank (e.g. check title, date and price of the trip), it is better to build a list of differences and assert it is empty.
```java
import utils.runner.Assert;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;
//{...}
    List&lt;String&gt; differences = new ArrayList&lt;&gt;();
    for (String s: expectedTripDetails.keySet()) {
        if (!actualTripDetails.containsKey(s)) {
            differences.add("Property "+s+" was not found in actual trip details, expected "+actualTripDetails.get(s));
        } else if (!actualTripDetails.get(s).equals(expectedTripDetails.get(s))) {
            differences.add("Property "+s+" mismatch: expected "+expectedTripDetails.get(s)+", but found "+actualTripDetails.get(s));
        }
    }
    Assert.assertThat("Actual trip details match expected ones", differences, is(empty())); 
```
5. Do not use Assert outside assertion points in the end of the test. 
If you want to check that some action had expected result
(e.g. you clicked 'menu' -> menu was opened), use another construction:
```java
if (!menu.isDisplayed()){
    throw new FailedTestException("Menu wasn't shown after menu button was clicked.");
}
```
This will abort the test without assertion error.

### Driver <a name="driver"/>
Driver instance is always accessible via `DriverProvider.get()`

### Waiters <a name="waiters"/>
Waiters are blocks of actions executed one by one either until success, or until timeout.

Example: to set text into a text field, we have to click this field, erase its contents 
and send a key sequence for a new value.

For most cases you can use a predefined set of actions defined in Waiter class.
 
#### Predefined actions: Waiter <a name="predefined-actions-waiter"/>
Create a new Waiter instance with argument to specify timeout, or without it.
```java
Waiter wait = new Waiter() //default timeout
Waiter wait = new Waiter(Duration.ofSeconds(3)) //abort actions if 3 seconds if no success yet.
```
Use predefined actions and requests for MobileElements and By's:
* click
* set text 
* get text
* get attribute
* is element displayed?
* wait until element is displayed (the same as isDisplayed, added for better readability)
* are are elements displayed? (for the list of locators/elements)
* is element absent?
* clear text contents in an editable field
* does element exist?
* does any element contain required text?
* check/uncheck a checkbox
* find element (or find all elements) for By argument
* get count for By (how many elements are found by locator) 
* etc. 

If you have to perform more complex chain of actions 
(e.g. refresh the page until the proper element is shown), use AnyWait class
which is highly configurable.

#### Custom actions: AnyWait<T,R> <a name="custom-actions-any-wait"/>

Waiters succeed if all actions were executed without exceptions, and condition until() (if provided)
is satisfied. Example: read text from the element until it is not null.

If actions chain fails, Waiter pauses and then executes all sequence from beginning (if time is not out yet).
  
Either test object, or result, or both of them can be null. 
In this case, type will be AnyWait&lt;Void, Void&gt;, because we take nothing and return nothing.

Waiters are parameterized: base class is AnyWait&lt;T, R&gt;, where T is for test object, R is for result.

Details:
1. If our AnyWait accepts a button to click and returns nothing, T will be MobileElement (for button),
R will be Void (we do not return anything) -> AnyWait<MobileElement, Void>
2. If our AnyWait tries to read some label withing given time, T will be Void (no parameter required), 
R will be String (we return found text) -> AnyWait<Void, String>
3. We specify our test object (parameter) using with() method.
```java
MobileElement button = someScreen.myButton;
AnyWait&lt;MobileElement, Void&gt; wait = new AnyWait&lt;&gt;();
wait.with(button);
```
4. We operate with test object (or without it) using 2 methods:
    1. `calculate()` when we want to calculate result;
    2. `execute()` when we simply want to perform some action (no result).
5. Calculate() and execute() methods accept lambdas as arguments. 
(Lambda is simply "do this with any object I give you" command).
```java
//calculate resulting value without parameter in 1 step (square brackets {} and 'return' not required):
wait.calculate(()->screen.lblTitle.getText());
//calculate resulting value without parameter in more than 1 step:
wait.calculate(()->{
    screen.refresh();
    return screen.lblTitle.getText());
});
//calculate resulting value with parameter:
wait.calculate(button -> button.getText());
//execute action (do not calculate anything) with parameter:
wait.execute(button->button.click());
//execute action (do not calculate anything) without parameter:
wait.execute(() -> screen.refresh());
```
6. You cannot use both `calculate()` and `execute()` methods, just 1 of them.
7. Optional method `when()` determines 
when the app is ready to proceed with `execute()` or `calculate()` 
and accepts test object T as parameter (or nothing for no parameter). It returns true or false.
```java
//with parameter
wait.when(button -> button.isDisplayed());
//without parameter
wait.when(()-> screen.getTitle().equals("My Profile");)
```

8. Optional method `until()` determines if `calculate()` or `execute()` was successful. 
It accepts result R as parameter (or nothing if no result calculated). It returns true or false.
```java
//with result
wait.until(text -> text.contains(username));
//without result
wait.until(() -> screen.title.isDisplayed());
```
9. Optional method `describe()` adds a text line to debug output to describe what this wait is doing.
 Example: `wait.describe("Reload the page until title contains new username")`
10. Optional method `duration()` receives Duration parameter and lets you set a timeout for the operation 
which is different from default.
11. Optional method `failOnTimeout(true/false)` lets you choose is the test has to be aborted if Wait fails.
 Default: false (proceed on failure).
12. Optional method `addIgnorableException` from interface `IgnoresExceptions` lets
 you add an exception which will not abort the test, but fail just current attempt. Waiter will pause 
 and try again soon.
13. Method `go()` starts the execution, once you are all set.
14. Method `isSuccess()` returns true, if Wait managed to perform all actions successfully,
and false otherwise (if the goal was not reached until timeout).

AnyWait example:
```java
//accept no parameter, return nothing
AnyWait<Void, Void> activityWait = new AnyWait<>();
//execute 2 actions: scroll down and then gather all texts from visible part of the page
//add found texts to collection textsFromTripDetails (declared above).
activityWait.execute(()->{
    SwipeHelper.scrollDown();
    addVisibleTextsFromTripDetails(textsFromTripDetails);
});
//stop when button "Post Comment" is displayed.
activityWait.until(() -> dreamTripsDetailsScreen.btnPostComment.isDisplayed());
//do not fail if NoSuchElementException is thrown 
(if we haven't reached the button "Post Comment" yet).
activityWait.addIgnorableException(NoSuchElementException.class);
//add a description
activityWait.describe("Wait until all texts collected from Trip Details");
//the whole operation should be aborted if not completed within 5 minutes
activityWait.duration(Duration.ofMinutes(5));
//go!
activityWait.go();
//check if the operation was a success (until() method returned true).
if (!activityWait.isSuccess()) {
    throw new FailedTestException("FAILED to scroll Details page down until Post Comment button is displayed. " +
            "Swipe might be a problem.");
}
```

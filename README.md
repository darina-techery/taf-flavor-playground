# taf-flavor-playground
## Build
Each time you add new Actions class, the project has to be rebuild by Gradle to generate missing classes.
To rebuild the project, execute command 

<code>./gradlew clean compileJava compileTestJava test</code>


## Execution
To execute tests, run command

<code>./gradlew clean compileJava compileTestJava test</code>

Optional parameters (passed via JVM args, starting from -D):
* <code>suite</code> to specify .xml file in src/test/resources/suites. 
<br/><b>Default:</b> suite.xml
<br/><b>Example:</b><code>./gradlew clean compileJava compileTestJava test -Dsuite=internal-suite.xml</code>
* <code>groups</code> to specify test groups included in test run.
<br/><b>Default:</b> none (all tests will be included)
<br/><b>Example:</b><code>./gradlew clean compileJava compileTestJava test -Dgroups=A,B</code>
* <code>excludeGroups</code> to specify test groups excluded from test run.
<br/><b>Default:</b> none (no tests will be excluded)
<br/><b>Example:</b><code>./gradlew clean compileJava compileTestJava test -Dgroups=A</code>

If group name contains spaces, enclose argument in quotes.
<br/><b>Example:</b><code>./gradlew clean compileJava compileTestJava test "-Dgroups=group with spaces,A" -Dsuite=internal-suite.xml</code>


## Structure
### Screens - Actions - Steps - Tests
#### Screen
Screen contains UI mapping
1. Screen classes should extend <code>BaseUiModule</code>, which contains basic PageObject compilation instructions.
2. Screen classes contain locators for Android and iOS.
3. Element names should have prefixes to reflect element type: _btn_ for button, _lbl_ for label, _txt_ for editable text etc.
4. Locator hints:
<pre><code>
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
    </code></pre>
5. Use xpath as last resort, it is not only slow, but also unstable on iOS. If possible, choose accessibility strategy for iOS.
6. If some element has no accessibilityId, communicate with QA Automation team to create a task for adding it.
#### Actions 
Actions contain interactions with UI. Each interaction is 1 simple user action (e.g. submit login credentials contains *setText* for login field, *setText* for password field, *click* for submit button).
1. Actions classes should extend BaseUiActions class.
2. If actions are different for Android and iOS, this class (SomeActionsClass) should be abstract.Implement different actions for iOS and Android in child classes named DroidSomeActionsClass and IOSSomeActionsClass.
<pre><code>
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
    </code></pre>
3. __Note:__ device-specific Actions classes (IOS- and Droid-) should be named __strictly__ with IOS- and Droid- prefixes. 
Otherwise, code generation will fail. 

#### Steps
Steps aggregate Actions to form sequences of actions based on business logic. They can basically be described as "User does something".

Example: User logs in with valid credentials.
<br/>Action 1: submit login credentials
<br/>Action 2: abort test if Login screen remains visible.
<br/>Action 3: dismiss pop-ups

1. Steps class may not extend anything if not needed.
2. Constructor for Steps class should be marked with <code>@UseActions</code> annotation.
3. All Actions involved in Steps should be passed as arguments to constructor:
<pre><code>
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
</code></pre>
4. Add <code>@Step</code> annotation to describe steps in Allure report
<pre><code>
@Step("Go to Dream Trips screen")
public void openDreamTripsScreen(){
    navigationActions.selectMenuItem(MenuItem.DREAM_TRIPS);
    tripsListActions.waitForScreen();
}
</code></pre>
5. <code>@Step</code> annotation can include arguments from methods referred to as '{0}', '{1}' etc.
<pre><code>
@Step("Open Trip by name '{0}'")
public void openTripByName(String tripName) {...}
</code></pre>

### Assertions
1. Use method Assert.assertThat from project (utils.runner), not hamcrest or testng package, because 
our method also takes screenshot for Allure when assertion fails.
2. All assertion messages have to follow the form "Something should be this way (or NOT this way)", 
e.g. "Dream Trips screen should be visible/should NOT be visible".
3. Multiple assertions can be written in separate assert statements only if the 1st one (if fails) blocks the 2nd one.
Example: assert that user is not null, assert that username is John (if user == null, we cannot check username).
4. If multiple assertions are of the same rank (e.g. check title, date and price of the trip), it is better to build a list of differences and assert it is empty.
<pre><code>
import utils.runner.Assert;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;
{...}
    List&lt;String&gt; differences = new ArrayList&lt;&gt;();
    for (String s: expectedTripDetails.keySet()) {
        if (!actualTripDetails.containsKey(s)) {
            differences.add("Property "+s+" was not found in actual trip details, expected "+actualTripDetails.get(s));
        } else if (!actualTripDetails.get(s).equals(expectedTripDetails.get(s))) {
            differences.add("Property "+s+" mismatch: expected "+expectedTripDetails.get(s)+", but found "+actualTripDetails.get(s));
        }
    }
    Assert.assertThat("Actual trip details match expected ones", differences, is(empty())); 
</code></pre>

### Driver
Driver instance is always accessible via <code>DriverProvider.get()</code>

### Waiters
Waiters are blocks of actions executed one by one either until success, or until timeout.

For most cases you can use a predefined set of actions defined in Waiter class.
 
#### Waiter
Create a new Waiter instance with argument to specify timeout, or without it.
<pre><code>
Waiter wait = new Waiter() //default timeout
Waiter wait = new Waiter(Duration.ofSeconds(3)) //abort actions if 3 seconds if no success yet.
</code></pre>
Use predefines actions for MobileElements and By's:
* click
* set text
* check/uncheck a checkbox
* find or findAll for By
* getCount for By (how many elements are found by locator) 
* get data: is element displayed, is it absent, get its attribute
* etc. 

If you have to perform more complex chain of actions 
(e.g. refresh the page until the proper element is shown, use AnyWait class
which is highly configurable.)

####AnyWait<T,R>

Waiters are parameterized: base class is AnyWait&lt;T, R&gt;, where T is for test object, R is for result.

Waiters can ignore certain exceptions (e.g. NoSuchElementException).
  
Either test object, or result, or both of them can be null. 
In this case, type will be AnyWait&lt;Void, Void&gt;, because we take nothing and return nothing.

AnyWait example:
<pre><code>
//accept no parameter, return nothing
AnyWait<Void, Void> activityWait = new AnyWait<>();
//execute 2 actions: scroll down and then gather all texts from visible part of the page
activityWait.execute(()->{
    SwipeHelper.scrollDown();
    addVisibleTextsFromTripDetails(textsFromTripDetails);
});
//stop when button "Post Comment" is displayed.
activityWait.until(() -> dreamTripsDetailsScreen.btnPostComment.isDisplayed());
//do not fail if NoSuchElementException is thrown (if we haven't reached the button "Post Comment" yet).
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
</code></pre>

Details:
1. If our AnyWait accepts a button to click and returns nothing, T will be MobileElement (for button),
R will be Void (we do not return anything) -> AnyWait<MobileElement, Void>
2. If our AnyWait tries to read some label withing given time, T will be Void (no parameter required), 
R will be String (we return found text) -> AnyWait<Void, String>
3. We specify our test object (parameter) using with() method.
<pre><code>
MobileElement button = someScreen.myButton;
AnyWait&lt;MobileElement, Void&gt; wait = new AnyWait&lt;&gt;();
wait.with(button);
</code></pre>
4. We operate with test object (or without it) using 2 methods:
    1. <code>calculate()</code> when we want to calculate result;
    2. <code>execute()</code> when we simply want to perform some action (no result).
5. Calculate() and execute() methods accept lambdas as arguments. 
(Lambda is simply "do this with any object I give you" command).
<pre><code>
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
</code></pre>
6. You cannot use both <code>calculate()</code> and <code>execute()</code> methods, just 1 of them.
7. Optional method <code>when()</code> determines 
when the app is ready to proceed with <code>execute()</code> or <code>calculate()</code> 
and accepts test object T as parameter (or nothing for no parameter). It returns true or false.
<pre><code>
//with parameter
wait.when(button -> button.isDisplayed());
//without parameter
wait.when(()-> screen.getTitle().equals("My Profile");)
</code></pre>

8. Optional method <code>until()</code> determines if <code>calculate()</code> or <code>execute()</code> was successful. 
It accepts result R as parameter (or nothing if no result calculated). It returns true or false.
<pre><code>
//with result
wait.until(text -> text.contains(username));
//without result
wait.until(() -> screen.title.isDisplayed());
</code></pre>
9. Optional method <code>describe()</code> adds a text line to debug output to describe what this wait is doing.
 Example: <code>wait.describe("Reload the page until title contains new username")</code>
10. Optional method <code>duration()</code> receives Duration parameter and lets you set a timeout for the operation 
which is different from default.
11. Method <code>go()</code> starts the execution, once you are all set.


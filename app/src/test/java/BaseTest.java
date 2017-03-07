import actions.modules.DroidActionModule;
import actions.modules.IPhoneActionModule;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import steps.CalculateSteps;

public abstract class BaseTest {

    private MainComponent component;

    protected MainComponent getComponent() {
        if (component == null) {
            initDagger();
        }
        return component;
    };

    private void initDagger() {
        System.out.println("init Dagger");
        String config = "ios";
//        String config = "droid";
        component = create(config);
    }

    private MainComponent create(String arg) {
        return DaggerMainComponent.builder()
                .actionModuleWrapper(
                        new ActionModuleWrapper(arg.equals("ios")
                                ? new IPhoneActionModule()
                                : new DroidActionModule()))
                .build();
    }

}

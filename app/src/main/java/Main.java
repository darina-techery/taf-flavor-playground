import actions.DroidActionModule;
import actions.IPhoneActionModule;
import tests.CalculateTests;

public class Main {

    public static void main(String... args) {
        MainComponent component = create(args[0]);

        CalculateTests tests = new CalculateTests(component.calculateSteps());
        tests.testCalculateCommonWay();
    }

    private static MainComponent create(String arg) {
        return DaggerMainComponent.builder()
                .daggerActionModule(
                        new ActionModuleWrapper(arg.equals("ios") ? new IPhoneActionModule() : new DroidActionModule()))
                .build();
    }


}

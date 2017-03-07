import actions.modules.DroidActionModule;
import actions.modules.IPhoneActionModule;
import tests.CalculateTests;

public class Main {

    public static void main(String... args) {
    	if (args.length == 0) return;
        MainComponent component = create(args[0]);

        CalculateTests tests = new CalculateTests(component.calculateSteps());
        tests.testCalculateCommonWay();
    }

    private static MainComponent create(String arg) {
        return DaggerMainComponent.builder()
		        .actionModuleWrapper(
                        new ActionModuleWrapper(arg.equals("ios")
                                ? new IPhoneActionModule()
                                : new DroidActionModule()))
                .build();
    }


}

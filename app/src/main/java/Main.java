import tests.CalculateTests;

public class Main {

    public static void main(String... args) {
        MainComponent component = DaggerMainComponent.create();
        CalculateTests tests = new CalculateTests(component.calculateSteps());
        tests.testCalculateCommonWay();
    }

}

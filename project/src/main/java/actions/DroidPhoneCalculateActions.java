package actions;

import org.apache.logging.log4j.Logger;
import utils.LogProvider;

public final class DroidPhoneCalculateActions
		extends CalculateActions
		implements LogProvider {

    final Logger log = getLogger();

    @Override
    public int twoAndTwo() {
        log.info("Two plus two (droid phone)");
        return calculatePage.two + calculatePage.two;
    }

    @Override
    public int twoPlusThree() {
        log.info("Two plus three (droid phone)");
        return super.twoPlusThree();
    }
}

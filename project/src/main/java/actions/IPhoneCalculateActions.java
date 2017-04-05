package actions;

import org.apache.logging.log4j.Logger;
import utils.LogProvider;

public final class IPhoneCalculateActions extends CalculateActions implements LogProvider {

    private final Logger log = getLogger();

    @Override
    public int twoAndTwo() {
        log.info("Test: Two multiplied by two (iPhone)");
        return calculatePage.two * calculatePage.two;
    }

    @Override
    public int twoPlusThree() {
	    log.info("Test: Two plus three (iPhone)");
        return super.twoPlusThree();
    }
}

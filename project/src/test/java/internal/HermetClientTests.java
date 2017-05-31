package internal;

import base.BaseTest;
import data.Configuration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import rest.api.hermet.HermetSessionManager;
import utils.runner.Assert;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

public class HermetClientTests extends BaseTest {

	private String hermetSessionId;
	private final String commonApiUrl = Configuration.getParameters().apiURL;

	@BeforeClass
	public void setupHermetSession(){
		hermetSessionId = HermetSessionManager.getId(commonApiUrl);
	}

	@Test
	public void hermetSessionIdRemainsConstantForTargetUrl() {
		String sessionId = HermetSessionManager.getId(commonApiUrl);
		Assert.assertThat("Hermet session id should remain constant between tests",
				sessionId, is(hermetSessionId));
	}

	@Test
	public void hermetSessionIdIsDifferentForAnotherTargetUrl() {
		String anotherTargetUrl = "www.sometesturl.com";
		String sessionId = HermetSessionManager.getId(anotherTargetUrl);
		Assert.assertThat("Hermet session id should remain constant between tests",
				sessionId, is(not(hermetSessionId)));
	}
}

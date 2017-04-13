package actions;

public class DroidLoginActions extends LoginActions {

	DriverActions driverActions = new DroidDriverActions();

	@Override
	public void setLogin(String username) {
		super.setLogin(username);
		driverActions.hideKeyboard();
	}

	@Override
	public void setPassword(String password) {
		super.setPassword(password);
		driverActions.hideKeyboard();
	}

	@Override
	public void waitForScreen() {
/*
		DriverWait<String> mainActivityWait = DriverWait.getActivityNameUntilMatches("MainActivity");
		if (!mainActivityWait.isSuccess()){
			String actualActivityName = mainActivityWait.result();
			throw new FailedTestException("Cannot load main activity: {} found instead", actualActivityName);
		}
 */
	}


}

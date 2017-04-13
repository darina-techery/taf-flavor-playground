package archive;

public class Screens {
//		<T extends BaseScreen> implements Supplier<T>
// {
//
//	private final Supplier<T> supplier;
//	private Supplier<T> current;
//
//	private Screens(Supplier<T> supplier) {
//		this.supplier = supplier;
//		this.current = () -> swapper();
//	}

//	public static <T extends BaseScreen> Screens<T> create(Class<T> screenClass) {
//		return new Screens<>(() -> {
//			AppiumDriver<MobileElement> driver = DriverProvider.get();
////                FieldDecorator decorator = new AppiumFieldDecorator(driver);
//			T screen = PageFactory.initElements(driver, screenClass);
//			return screen;
//		});
//	}
//
//	private synchronized T swapper()
//	{
//		if(!Factory.class.isInstance(current))
//		{
//			T obj = supplier.get();
//			current = new Factory<T>(obj);
//		}
//		return current.get();
//	}
//
//
//	class Factory<T> implements Supplier<T>
//	{
//		Factory(T obj)
//		{
//			this.obj = obj;
//		}
//
//		public T get()
//		{
//			return obj;
//		}
//
//		private T obj;
//	}
//
//	@Override
//	public T get() {
//		return null;
//	}

}

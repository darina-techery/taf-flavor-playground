package archive;

public final class ScreenFactory {
	private ScreenFactory() {

	}

//	public static <T extends BaseScreen> T getScreen(Class<T> screenClass) {
//
//	}
//
//
//
//	private <T extends BaseScreen> Supplier<T> provideScreen(Class<T> screenClass) {
//		return new Supplier<T>() {
//			@Override
//			public T get() {
//				AppiumDriver<MobileElement> driver = DriverProvider.get();
////                FieldDecorator decorator = new AppiumFieldDecorator(driver);
//                T page = PageFactory.initElements(driver, screenClass);
//                return page;
////        PageFactory.initElements(decorator, this);
//			}
//		};
//	}

//	public static <T extends BaseScreen> T getScreen(Class<T> screenClass) {
//		T screen =
//				(T) Proxy.newProxyInstance(screenClass.getClassLoader(),
//						new Class [] {screenClass},
//						new PageInvocationHandler(screenClass));
//		return screen;
//	}

//	public

}
//
//@FunctionalInterface
//interface LazyPage<BasePage> extends Supplier<BasePage> {
//	abstract class PageCache {
//		private volatile static Map<Integer, Object> instances = new HashMap<>();
//		private static synchronized Object getInstance(int instanceId, Supplier<Object> create) {
//
//			Object instance = instances.get(instanceId);
//			if (instance == null) {
//				synchronized (PageCache.class) {
//					instance = instances.get(instanceId);
//					if (instance == null) {
//						instance = create.get();
//						instances.put(instanceId, instance);
//					}
//				}
//			}
//			return instance;
//		}
//	}
//
//	@Override
//	default BasePage get() {
//		return (BasePage) PageCache.getInstance(this.hashCode(), () -> init());
//	}
//
//	BasePage init();
//}

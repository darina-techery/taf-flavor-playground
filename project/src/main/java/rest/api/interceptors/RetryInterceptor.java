package rest.api.interceptors;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import utils.exceptions.FailedTestException;
import utils.exceptions.FailedWaitAttemptException;
import utils.waiters.AnyWait;

import java.io.IOException;
import java.time.Duration;
import java.util.Objects;

public class RetryInterceptor implements Interceptor {
	@Override
	public Response intercept(Chain chain) throws IOException {
		final Request request = chain.request();
		final String requestDescription = String.format("[%s] request to %s",
				request.method(), request.url());
		final String errorMsg = "Failed to process " + requestDescription;
		final AnyWait<Request, Response> wait = new AnyWait<>();
		wait.duration(Duration.ofMinutes(3));
		wait.with(chain.request());
		wait.calculate(r -> {
			Response response;
			try {
				response = chain.proceed(r);
			} catch (IOException e) {
				throw new FailedWaitAttemptException(errorMsg + ": " + e.getMessage());
			}
			return response;
		});
		wait.until(Objects::nonNull);
		wait.go();
		wait.describe("Execute request "+requestDescription+" and retry if remote server is not responding");
		if (!wait.isSuccess()) {
			final Throwable lastError = wait.getLastError();
			throw new FailedTestException(lastError == null
					? errorMsg
					: errorMsg, lastError);
		}
		return wait.result();
	}
}

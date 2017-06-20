package rest.api.interceptors;

import okhttp3.*;
import okio.Buffer;
import okio.BufferedSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.Charset;

public class APILoggingInterceptor implements Interceptor {
	private static final Charset UTF8 = Charset.forName("UTF-8");
	private Logger log = LogManager.getLogger();

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();
		logRequestAsCurlCommand(request);

		final Response response = chain.proceed(request);
		logResponse(response);
		return response;
	}

	private void logRequestAsCurlCommand(Request request) throws IOException {
		boolean compressed = false;
		StringBuilder curlCmd = new StringBuilder("curl -i -X ")
				.append(request.method()).append(" '")
				.append(request.url()).append("'");

		Headers headers = request.headers();
		for (int i = 0, count = headers.size(); i < count; i++) {
			String name = headers.name(i);
			String value = headers.value(i);
			if ("Accept-Encoding".equalsIgnoreCase(name) && "gzip".equalsIgnoreCase(value)) {
				compressed = true;
			}
			curlCmd.append(" \\\n -H ").append("\"").append(name).append(": ").append(value).append("\"");
		}

		curlCmd.append(" \\\n");
		RequestBody requestBody = request.body();
		if (requestBody != null) {
			Buffer buffer = new Buffer();
			requestBody.writeTo(buffer);
			Charset charset = UTF8;
			MediaType contentType = requestBody.contentType();
			if (contentType != null) {
				charset = contentType.charset(UTF8);
			}
			// try to keep to a single line and use a subshell to preserve any line breaks
			curlCmd.append(" -d '").append(buffer.readString(charset).replace("\n", "\\n")).append("'");
		}

		curlCmd.append((compressed) ? " --compressed " : " ");

		log.debug("\n< {} REQUEST:\n{}", request.method(), curlCmd.toString());
	}

	private void logResponse(Response response) throws IOException {
		String label = response.isSuccessful() ? "SUCCESS" : "FAILURE";
		//Copy existing response body content, because string() method can only be called once
		ResponseBody body = response.body();
		String bodyContent = null;
		if (body != null) {
			BufferedSource source = body.source();
			source.request(Long.MAX_VALUE); // Buffer the entire body.
			bodyContent = source.buffer().clone().readString(Charset.forName("UTF-8"));
		}

		log.debug("\n> {}:\n\t{}\n\tBody: {}", label, response.toString(),
				bodyContent == null || bodyContent.isEmpty() ? "-empty-" : bodyContent);
	}
}

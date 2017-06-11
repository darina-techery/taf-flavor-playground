package rest.api.interceptors;

import okhttp3.*;
import okio.Buffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.Charset;

public class LoggingInterceptor implements Interceptor {
	private static final Charset UTF8 = Charset.forName("UTF-8");
	private Logger log = LogManager.getLogger();

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();
		boolean compressed = false;
		String curlCmd = "curl -i -X " + request.method() + " '" + request.url() + "'";

		Headers headers = request.headers();
		for (int i = 0, count = headers.size(); i < count; i++) {
			String name = headers.name(i);
			String value = headers.value(i);
			if ("Accept-Encoding".equalsIgnoreCase(name) && "gzip".equalsIgnoreCase(value)) {
				compressed = true;
			}
			curlCmd += " \\\n -H " + "\"" + name + ": " + value + "\"";
		}

		curlCmd += " \\\n";
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
			curlCmd += " --data $'" + buffer.readString(charset).replace("\n", "\\n") + "'";
		}

		curlCmd += ((compressed) ? " --compressed " : " ");

		log.debug("\n< REQUEST:\n{}", curlCmd);

		final Response response = chain.proceed(request);
		if (response.isSuccessful()) {
			log.debug("\n> SUCCESS: \n\t" + response.toString() + "\n");
		} else {
			log.debug("\n> FAILURE: \n\t" + response.toString() + "\n"
					+ (response.body() == null ? "" : "\t" + response.body().string()));
		}
		return response;
	}
}

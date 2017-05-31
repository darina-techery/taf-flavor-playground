package rest.api.interceptors;

import okhttp3.*;
import okio.Buffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.Charset;

public class CurlLoggingInterceptor implements Interceptor {
	private static final Charset UTF8 = Charset.forName("UTF-8");
	private Logger log = LogManager.getLogger();

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();
		boolean compressed = false;
		String curlCmd = "curl -i -X " + request.method();

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

		curlCmd += ((compressed) ? " --compressed " : " ") + request.url();

		log.debug("\n\tURL: {}\n\t{}",request.url(), curlCmd);

		return chain.proceed(request);

	}
}

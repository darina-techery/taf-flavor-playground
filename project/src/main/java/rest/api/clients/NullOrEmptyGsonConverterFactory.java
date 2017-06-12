package rest.api.clients;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class NullOrEmptyGsonConverterFactory extends Converter.Factory {
	@Override
	public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
		final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
		return (Converter<ResponseBody, Object>) body -> {
			if (body.contentLength() == 0) return null;
			return delegate.convert(body);
		};
	}
}

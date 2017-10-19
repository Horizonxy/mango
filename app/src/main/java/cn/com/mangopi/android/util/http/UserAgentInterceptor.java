package cn.com.mangopi.android.util.http;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class UserAgentInterceptor implements Interceptor {

    public String userAgent;

    public UserAgentInterceptor(String userAgent){
        this.userAgent = userAgent;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request userAgentRequest = request.newBuilder()
                    .removeHeader("User-Agent")
                    .addHeader("User-Agent", userAgent)
                    .build();
        return chain.proceed(userAgentRequest);
    }
}

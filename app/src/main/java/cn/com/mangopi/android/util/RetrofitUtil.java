package cn.com.mangopi.android.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.util.http.NetworkInterceptor;
import cn.com.mangopi.android.util.http.UserAgentInterceptor;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitUtil {

	public static OkHttpClient createOkHttpClient(){
		HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
		logging.setLevel(HttpLoggingInterceptor.Level.BODY);

		String userAgent = System.getProperty("http.agent", "");
		//缓存路径
		FileUtils.delDir(new File( Application.application.getCacheDir(), "http"));
		Cache cache = new Cache(new File(Application.application.getCacheDir(), "/response"), Constants.SIZE_OF_CACHE);

		OkHttpClient client = new OkHttpClient.Builder()
				.addInterceptor(new UserAgentInterceptor(userAgent))
				//有网络时的拦截器
				.addNetworkInterceptor(new NetworkInterceptor())
				//没网络时的拦截器
				.addInterceptor(new NetworkInterceptor())
				.addInterceptor(logging)
				.cache(cache)
				.connectTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
				.writeTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
				.readTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
				.build();

		return client;
	}

	public static Retrofit createRetrofit(){
		Retrofit retrofit = new Retrofit.Builder()
				.client(RetrofitUtil.createOkHttpClient())
				.baseUrl(Constants.END_POIND)
				.addConverterFactory(GsonConverterFactory.create(createGson()))
				.addConverterFactory(ScalarsConverterFactory.create())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.build();
		return retrofit;
	}

	public static Gson createGson(){
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {

			@Override
			public Date deserialize(JsonElement json, Type typeOfT,
									JsonDeserializationContext context)
					throws JsonParseException {
				String date = json.getAsJsonPrimitive().getAsString();
				String JSONDateToMilliseconds = "\\/(Date\\((.*?)(\\+.*)?\\))\\/";
				Pattern pattern = Pattern.compile(JSONDateToMilliseconds);
				Matcher matcher = pattern.matcher(date);
				String result = matcher.replaceAll("$2");
				try {
					return new Date(new Long(result));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
		return  builder.create();
	}
}

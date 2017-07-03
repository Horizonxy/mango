package com.mango.util;

import com.mango.Application;
import com.mango.Constants;
import com.mango.util.http.NetworkInterceptor;
import com.mango.util.http.UserAgentInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

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
		logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

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
				.addConverterFactory(GsonConverterFactory.create())
				.addConverterFactory(ScalarsConverterFactory.create())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.build();
		return retrofit;
	}

}

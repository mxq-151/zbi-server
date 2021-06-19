package org.zbi.server.utils.okhttp;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * OKHTTPClient静态内部类
 */
public class OkHttpClientHolder {

	private OkHttpClientHolder() {
	}
	public static OkHttpClient getClientInstance() {
		return SingletonHolder.client;
	}

	// 初次被引用时加载
	static class SingletonHolder {
		private static final long TIMEOUT_CONNECT = 300;
		private static final long TIMEOUT_READ = 300;
		/**
		 * 初始化 普通OkHttpClient
		 */
		private static OkHttpClient client = new OkHttpClient.Builder()
				// .addInterceptor(new LoggingInterceptor())
				.connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
				.readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
				.build();
	}

}

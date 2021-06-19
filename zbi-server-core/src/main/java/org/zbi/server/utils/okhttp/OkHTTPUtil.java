package org.zbi.server.utils.okhttp;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by ChenJiaHao on 2017/6/14.
 */
public class OkHTTPUtil {
	private Logger logger = LoggerFactory.getLogger(OkHTTPUtil.class);
	private String responseBody = "";
	private boolean isResponsed = false;

	private final MediaType MEDIA_TYPE_APP_JSON = MediaType.parse(OkHttpConstants.MIME_APPLICATION_JSON);
	private final MediaType MEDIA_TYPE_APP_PROTOBUF = MediaType.parse(OkHttpConstants.MIME_APPLICATION_PROTOBUF);

	private OkHttpResp httpRequest(OkHttpClient client, Request request, String requestUrl) {
		OkHttpResp respEntity = null;
		Response response = null;

		// 发送请求
		try {
			response = client.newCall(request).execute();
			responseBody = response.body().string();
			if (response.isSuccessful()) {
				logger.info("response succeed, Status Code:[{}], Post URL:[{}]", response.code(), requestUrl);

			} else {
				logger.info("response failed, Status Code:[{}], Message:[{}], Body[{}], Header[{}]]", response.code(),
						response.message(), responseBody, response.headers());

			}
			respEntity = new OkHttpResp(response.code(), responseBody, response.message());
		} catch (IOException e) {
			logger.error("Send http data failed", e);
		}

		// 关闭连接
		if (response != null) {
			response.close();
		}

		return respEntity;
	}

	/**
	 * 构建Request对象
	 * 
	 * @param requestUrl
	 *            请求URL
	 * @param requestBody
	 *            请求body
	 * @param header
	 *            请求Header
	 * @return
	 */
	private Request buildRequest(String requestUrl, RequestBody requestBody, Map<String, String> header,
			HttpMethod httpMethod) {
		Request.Builder builder = new Request.Builder().url(requestUrl);


		if (httpMethod == HttpMethod.POST) {
			builder.post(requestBody);
		} else if (httpMethod == HttpMethod.DELETE) {
			builder.delete(requestBody);
		} else if (httpMethod == HttpMethod.PUT) {
			builder.put(requestBody);
		}

		// 添加http头部信息
		if (header != null && header.size() != 0) {
			header.forEach((key, value) -> {
				builder.addHeader(key, value);
			});
		}

		return builder.build();
	}

	public OkHttpResp postProtoBuf(OkHttpClient client, String requestUrl, byte[] bytes,
			Map<String, String> header) {
		// 构造请求体
		RequestBody requestBody = RequestBody.create(MEDIA_TYPE_APP_PROTOBUF, bytes);

		Request request = buildRequest(requestUrl, requestBody, header, HttpMethod.POST);

		return httpRequest(client, request, requestUrl);
	}

	public OkHttpResp postXWWWFormUrlencoded(OkHttpClient client, String requestUrl, Map<String, String> header,
			Map<String, String> bodyMap) {
		FormBody.Builder builder = new FormBody.Builder();
		bodyMap.forEach((k, v) -> {
			builder.add(k, v);
		});
		RequestBody requestBody = builder.build();

		Request request = buildRequest(requestUrl, requestBody, header, HttpMethod.POST);

		return httpRequest(client, request, requestUrl);
	}

	public OkHttpResp postJson(OkHttpClient client, String requestUrl, String jsonData) {
		return postJson(client, requestUrl, jsonData, null);
	}

	public OkHttpResp postJson(OkHttpClient client, String requestUrl, String jsonData,
			Map<String, String> header) {
		// 构造请求体
		RequestBody requestBody = RequestBody.create(MEDIA_TYPE_APP_JSON, jsonData);

		Request request = buildRequest(requestUrl, requestBody, header, HttpMethod.POST);

		return httpRequest(client, request, requestUrl);
	}

	// form表单提交
	public OkHttpResp postForm(OkHttpClient client, String requestUrl, Map<String, String> param,
			Map<String, String> header) {
		MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
		param.forEach((key, value) -> {
			builder.addFormDataPart(key, value);
		});
		RequestBody requestBody = builder.build();
		Request request = buildRequest(requestUrl, requestBody, header, HttpMethod.POST);
		return httpRequest(client, request, requestUrl);
	}

	// delete请求(JSON参数)
	public OkHttpResp deleteJson(OkHttpClient client, String requestUrl, Map<String, String> header) {
		return deleteJson(client, requestUrl, null, header);
	}

	// delete请求(JSON参数)
	public OkHttpResp deleteJson(OkHttpClient client, String requestUrl, String jsonData,
			Map<String, String> header) {
		// 构造请求体
		RequestBody requestBody = RequestBody.create(MEDIA_TYPE_APP_JSON, jsonData);

		Request request = buildRequest(requestUrl, requestBody, header, HttpMethod.DELETE);

		return httpRequest(client, request, requestUrl);
	}

	public OkHttpResp delete(OkHttpClient client, String requestUrl, Map<String, String> header) {

		Request request = buildRequest(requestUrl, null, header, HttpMethod.DELETE);

		return httpRequest(client, request, requestUrl);
	}

	/**
	 * GET请求
	 * 
	 * @param client
	 * @param requestUrl
	 *            请求URL
	 * @return
	 */
	public OkHttpResp get(OkHttpClient client, String requestUrl) {
		return get(client, requestUrl, null);
	}

	/**
	 * GET请求(带HTTP头)
	 * 
	 * @param client
	 * @param requestUrl
	 *            请求URL
	 * @param header
	 *            HTTP头部信息
	 * @return
	 */
	public OkHttpResp get(OkHttpClient client, String requestUrl, Map<String, String> header) {
		// 构造get请求
		Request request = buildRequest(requestUrl, null, header, HttpMethod.GET);

		return httpRequest(client, request, requestUrl);
	}

	// -----------------下方为附带重发机制的请求方法(尚未整理)-------------
	public String httpRequest(String requestUrl, Map<String, String> postParamMap) {
		String responseBody = "";

		// 配置post参数
		FormBody.Builder formBuilder = new FormBody.Builder();
		postParamMap.forEach((key, value) -> {
			formBuilder.add(key, value);
		});

		// 初始化OkHttpClient
		OkHttpClient client = OkHttpClientHolder.getClientInstance();
		Request request = new Request.Builder().url(requestUrl).post(formBuilder.build()).build();

		Response response = null;
		try {
			// 执行请求
			response = client.newCall(request).execute();
			responseBody = response.body().string();
			int responseCode = response.code();
			logger.debug("响应码:[{}], Post请求url[{}]", responseCode, requestUrl);
		} catch (IOException e) {
			logger.error("httpRequest-发生错误", e);
		}

		return responseBody;

	}

	public String httpRequestGetAsync(String requestUrl) {
		OkHttpClient client = OkHttpClientHolder.getClientInstance();
		Request request = new Request.Builder().url(requestUrl).build();
		try {
			// 添加Timeout重试机制
			client.newCall(request).enqueue(new OkHTTPUtil.TimeoutRetryCallback(client));

		} catch (Exception e) {
			logger.error("httpRequestGetAsync-HTTP请求错误", e);
		}

		// 阻塞(OkHttp暂时不提供异步get)
		while (!isResponsed) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				logger.error("httpRequestGetAsync-等待结果时发生错误", e);
			}
		}

		return responseBody;
	}

	/**
	 * 超时重连回调
	 */
	class TimeoutRetryCallback implements Callback {
		private OkHttpClient client;

		public TimeoutRetryCallback() {
		}

		public TimeoutRetryCallback(OkHttpClient client) {
			this.client = client;
		}

		// String responseBody = "";
		int serversLoadTimes = 0;
		int maxLoadTimes = 5;

		@Override
		public void onFailure(Call call, IOException e) {
			if (e.getClass().equals(SocketTimeoutException.class) && serversLoadTimes < maxLoadTimes)// 如果超时并未超过指定次数，则重新连接
			{
				serversLoadTimes++;
				logger.error("尝试发送失败，当前重试连接次数:[{}]", serversLoadTimes);
				client.newCall(call.request()).enqueue(this);
			} else {
				e.printStackTrace();
				logger.error("发送HTTP请求失败", e);
				isResponsed = true; // 超过访问次数时才允许返回数据
			}

		}

		@Override
		public void onResponse(Call call, Response response) throws IOException {
			int respCode = response.code();
			logger.debug("响应码:[{}], Get异步请求", respCode);
			responseBody = response.body().string();
			logger.debug(responseBody);

			isResponsed = true;
		}
	}

}

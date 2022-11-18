package gemastik.pendekar.data.api

import okhttp3.Interceptor
import okhttp3.Response


class AppInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("apiKey", "6vt27i7_QYGivDC69wNc3yfghOEGupVdeXZh0RwBm6U")
            .build()
        val requestBuilder = original.newBuilder().url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
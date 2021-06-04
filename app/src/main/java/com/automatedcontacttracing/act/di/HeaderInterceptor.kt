package com.automatedcontacttracing.act.di

import okhttp3.Interceptor
import okhttp3.Request

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val original: Request = chain.request()

        // Request customization: add request headers
        val requestBuilder: Request.Builder = original.newBuilder()
            .header("Content-Type", "application/json")
            .method(original.method(), original.body())
        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}
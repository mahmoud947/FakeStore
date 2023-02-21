package com.example.fakestore.data.remote.utils

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.Invocation
import javax.inject.Inject
import javax.inject.Named

class AuthInterceptor @Inject constructor(
    @Named("Auth")
    private val prefs: SharedPreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val invocation = chain.request().tag(Invocation::class.java)
            ?: return chain.proceed(chain.request())

        val shouldAttachAuthHeader = invocation
            .method()
            .annotations
            .any { it.annotationClass == Authenticated::class }

        return if (shouldAttachAuthHeader) {
            chain.proceed(
                chain.request()
                    .newBuilder().also { builder: Request.Builder ->
                        val token = prefs.getString("token", null)
                        builder.addHeader("Authorization", "Bearer $token")
                    }
                    .build()
            )
        } else chain.proceed(chain.request())
    }
}


@Target(AnnotationTarget.FUNCTION)
annotation class Authenticated
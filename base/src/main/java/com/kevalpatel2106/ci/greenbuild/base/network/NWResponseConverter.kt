/*
 * Copyright 2018 Keval Patel.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kevalpatel2106.ci.greenbuild.base.network

import com.google.gson.Gson
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.reflect.Type

/**
 * Created by Keval on 12/11/17.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */

internal class NWResponseConverter(private val gson: Gson) : Converter.Factory() {

    companion object {

        @JvmStatic
        fun create(gson: Gson): NWResponseConverter = NWResponseConverter(gson)
    }

    override fun responseBodyConverter(type: Type,
                                       annotations: Array<Annotation>,
                                       retrofit: Retrofit): Converter<ResponseBody, *>? {
        return try {

            //Response is the string type.
            //No need for the GSON converter
            if (type === String::class.java) {
                StringResponseConverter()
            } else GsonConverterFactory.create(gson).responseBodyConverter(type, annotations, retrofit)
        } catch (ignored: OutOfMemoryError) {
            null
        }

    }

    override fun requestBodyConverter(type: Type,
                                      parameterAnnotations: Array<Annotation>,
                                      methodAnnotations: Array<Annotation>,
                                      retrofit: Retrofit): Converter<*, RequestBody>? {
        return GsonConverterFactory.create(gson)
                .requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit)
    }

    private class StringResponseConverter : Converter<ResponseBody, String> {
        @Throws(IOException::class)
        override fun convert(value: ResponseBody): String = value.string()
    }
}

/*
 *
 *  * Copyright 2017 Kojadin
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.erevacation.challenge.data.remote;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import timber.log.Timber;

public class HttpLoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        try {
            Request newRequest = chain.request().newBuilder().build();

            Timber.d("%s %s %s", newRequest.method(), newRequest.url().toString(),
                    stringifyRequestBody(newRequest).length() <= 1024 ?
                    stringifyRequestBody(newRequest) : "to long output for print");
            Response response = chain.proceed(newRequest);
            if (!response.isSuccessful()) {
                String message = response.message();
                Timber.e("api error message: " + message + " code: " + response.code());
            }

            return response;
        } catch (SocketTimeoutException | UnknownHostException timeoutException) {
            Timber.e(timeoutException, "Bad Internet");
            throw timeoutException;
        } catch (IOException ioException) {
            Timber.e(ioException, "Call canceled. Caused most probably by okhttp bug unsubscribe");
            throw ioException;
        }
    }

    /**
     * Format request body to string
     *
     * @param request - There request
     * @return The request stringified
     */
    private String stringifyRequestBody(Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final Exception e) {
            return "";
        }
    }
}

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

public class ErrorHandler {

    public static String handleError(Throwable e) {
        String formattedError = e.getMessage();
        // todo parse nicer error massages
        return formattedError;
    }

    public interface HttpResponse {
        int OK = 200;
        int CREATED = 201;
        int NO_CONTENT = 204;
        int NOT_MODIFIED = 304;
        int BAD_REQUEST = 400;
        int UNAUTHORIZED = 401;
        int FORBIDDEN = 403;
        int NOT_FOUND = 404;
        int METHOD_NOT_ALLOWED = 405;
        int CONFLICT = 409;
        int GONE = 410;
        int TOO_MANY_REQUESTS = 429;
        int INTERNAL_SERVER_ERROR = 500;
    }

    public interface ErrorCode {
        int GENERAL_INPUT = 40000;
        int MISSING_GRANT_TYPE = 40001;
        int UNSUPPORTED_GRANT_TYPE = 40002;
        int INVALID_CREDENTIALS = 40003;
        int AUTHORIZATION_HEADER = 40100;
        int UNSUPPORTED_TOKEN_TYPE = 40101;
        int TOKEN_EXPIRED = 40102;
        int AUTHORIZATION_REQUIRED = 40103;
        int TOO_MANY_REQUESTS = 42901;
        int UNKNOWN_ERROR = 100000;
    }

    public interface ErrorMessage {
        String UNAUTHORIZED = "Unauthorized";
    }
}

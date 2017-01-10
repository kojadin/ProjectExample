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

package com.erevacation.challenge.anotations;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import timber.log.Timber;

public class SettingsConstants {

    public static void populateConstants(Class<?> klass) {
        String packageName = klass.getPackage().getName();
        for (Field field : klass.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers()) &&
                    field.isAnnotationPresent(RestoringKeysConstant.class)) {
                String value = packageName + "." + field.getName();
                try {
                    field.set(null, value);
                    Timber.i("Setup service constant: " + value + "");
                } catch (IllegalAccessException iae) {
                    Timber.i("Unable to setup constant for field " +
                            field.getName() +
                            " in class " + klass.getName());
                }
            }
        }
    }

}

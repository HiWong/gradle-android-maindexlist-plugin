/*
 * Copyright (C) 2016 chentaov5@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jllk.gradle

import org.gradle.logging.StyledTextOutput

/**
 * @author chentaov5@gmail.com
 */
final class JLLKLog {
    protected static StyledTextOutput out

    public static void log(String tag, String msg) {
        out.style(StyledTextOutput.Style.UserInput)
        out.withStyle(StyledTextOutput.Style.Info).text("[$tag] ")
        out.println(msg)
    }

    public static void tom(String msg) {
        out.style(StyledTextOutput.Style.UserInput)
        out.withStyle(StyledTextOutput.Style.Info).text('[tom] ')
        out.println(msg)
    }
}


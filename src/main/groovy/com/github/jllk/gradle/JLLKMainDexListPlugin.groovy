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

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.ProjectConfigurationException
import org.gradle.logging.StyledTextOutputFactory

/**
 * @author chentaov5@gmail.com
 */
class JLLKMainDexListPlugin implements Plugin<Project> {
    private Project project

    @Override
    void apply(Project project) {
        this.project = project

        if (!["com.android.application",
              "android",
              "com.android.module.application"].any { project.plugins.findPlugin(it) }) {
            throw new ProjectConfigurationException("Please apply 'com.android.application' " +
                    "or 'android' " +
                    "or 'com.android.module.application' plugin", null)
        }

        if (JLLKLog.out == null) {
            JLLKLog.out = project.gradle.services.get(StyledTextOutputFactory).create("")
        }

        createExtensions()

        createTask()

        configureProject()

    }

    void createExtensions() {
        project.extensions.create('jllk', JLLKMainDexListPluginExtension)

        def task = project.gradle.startParameter.taskNames[0]
        if (task != null) {
            if (["Release", "asR"].any { task.contains(it) }) {
                project.jllk.isRelease = true
            } else if (["Debug", "asD"].any { task.contains(it) }) {
                project.jllk.isRelease = false
            }
        }
    }

    void createTask() {
        project.tasks.create('makeMinimumMaindexlist', JLLKMainDexListTask)
    }

    void configureProject() {
        project.afterEvaluate {
            project.tasks.matching {
                it.name.startsWith("dex")
            } each { dx ->
                dx.dependsOn 'makeMinimumMaindexlist'

                if (dx.additionalParameters == null) {
                    dx.additionalParameters = []
                }

                dx.additionalParameters += "--main-dex-list=$project.projectDir/maindexlist.txt".toString()
                dx.additionalParameters += "--minimal-main-dex"
            }
        }
    }
}

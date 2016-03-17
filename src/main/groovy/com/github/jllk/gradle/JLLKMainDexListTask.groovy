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

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import com.android.multidex.JLLKClassReferenceListBuilder

/**
 * @author chentaov5@gmail.com
 */
class JLLKMainDexListTask extends DefaultTask {
    private File rootClassList
    private File rootJar
    private File allclassesJar

    @TaskAction
    def run() {
        // TODO Need to specified a root_class_list.txt file in project.rootDir
        rootClassList = new File("$project.projectDir/root_class_list.txt")
        if (!rootClassList.exists()) {
            JLLKLog.log("Error", "root_class_list.txt not found in $project.rootDir")
            return
        }

        def mode = project.jllk.isRelease ? "release" : "debug"

        allclassesJar = new File("${project.buildDir}/intermediates/multi-dex/$mode/allclasses.jar")
        if (!allclassesJar.exists()) {
            JLLKLog.log("Error", "$allclassesJar.path not exists.")
            return
        }

        rootJar = createRootJar()
        if (!rootJar.exists() || rootJar.length() == 0) {
            JLLKLog.log("Error", "createRootJar failed.")
            return
        }

        JLLKLog.tom "rootJar: ${rootJar.getAbsolutePath()}"
        JLLKLog.tom "allclassesJar: ${allclassesJar.getAbsolutePath()}"

        // Create maindexlist.txt
        String[] args = [
                rootJar.getAbsolutePath(),
                allclassesJar.getAbsolutePath()
        ]
        JLLKClassReferenceListBuilder.main(args)
    }

    def makeDir(String dirName) {
        def dir = new File(dirName)
        if (dir.exists()) {
            "rm ${dir.getAbsolutePath()} -rf".execute()
        }
        dir.mkdirs()
        dir
    }

    def createRootJar() {
        def tmpAll = makeDir("tmp_all")

        def tmpAllPath = tmpAll.getAbsolutePath()
        "unzip -o ${allclassesJar.getAbsolutePath()} -d $tmpAllPath".execute().text

        def tmpRoot = makeDir("tmp_root")

        def reader = new BufferedReader(new FileReader(rootClassList))
        def fqn
        while ((fqn = reader.readLine()) != null) {
            def pkg = fqn.substring(0, fqn.lastIndexOf(File.separator))
            def rootClassOutPath = [tmpRoot.getAbsolutePath(), pkg].join(File.separator)
            "mkdir -p $rootClassOutPath".execute()
            "cp $tmpAll.path/$fqn $rootClassOutPath".execute()
        }

        "rm root.jar -rf".execute()
        JLLKLog.tom "jar cvf root.jar -C ${tmpRoot.getAbsolutePath()} .".execute().text
        new File("root.jar")
    }
}

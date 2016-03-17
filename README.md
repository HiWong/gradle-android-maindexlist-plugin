# gradle-android-maindexlist-plugin

A gradle plugin help to make minimum size of maindexlist and maindex.


# Installation

1) clone repo

```bash
git clone https://github.com/JLLK/gradle-android-maindexlist-plugin.git
git clone https://github.com/JLLK/ClassDependenceAnalyser.git
```

2) install

```bash
cd gradle-android-maindexlist-plugin
gradle install

cd ClassDependenceAnalyser
gradle install
```

3) apply

Copy `gradle-android-maindexlist-plugin-1.0.0.jar` and `jllk-cda-1.0.0.jar` to your `project.rootDir/libs`, and then modify `build.gradle`:

```bash
buildscript {
    repositories {
        flatDir dirs: "$project.rootDir/libs"
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:1.1.1'
        classpath "com.github.jllk:gradle-android-maindexlist-plugin:1.0.0"
        classpath "com.github.jllk:jllk-cda:1.0.0"
    }
}

apply plugin: com.github.jllk.gradle.JLLKMainDexListPlugin

```

# Usage

1) prepare `root_class_list.txt`

`root_class_list.txt` will generate `maindexlist.txt` by this plugin.

For example:

```
com/github/jllk/multidex/sample/MainActivity.class
com/github/jllk/multidex/sample/SampleApp.class
com/github/jllk/multidex/JLLKMultiDex$V14.class
com/github/jllk/multidex/JLLKMultiDex$V19.class
com/github/jllk/multidex/JLLKMultiDex$V4.class
com/github/jllk/multidex/JLLKMultiDex.class
com/github/jllk/multidex/JLLKMultiDexExtractor$1.class
com/github/jllk/multidex/JLLKMultiDexExtractor.class
com/github/jllk/multidex/JLLKMultiDexInstaller$1.class
com/github/jllk/multidex/JLLKMultiDexInstaller$InstallType.class
com/github/jllk/multidex/JLLKMultiDexInstaller$InstallValue$Builder.class
com/github/jllk/multidex/JLLKMultiDexInstaller$InstallValue.class
com/github/jllk/multidex/JLLKMultiDexInstaller.class
com/github/jllk/multidex/ZipUtil$CentralDirectory.class
com/github/jllk/multidex/ZipUtil.class
com/github/jllk/multidex/hook/JLLKMultiDexHook.class
com/github/jllk/multidex/hook/JLLKReflectHelper.class
com/github/jllk/multidex/hook/JLLKInstrumentationProxy.class
com/github/jllk/multidex/hook/JLLKInstrumentationProxy$IExecStartActivityDelegate.class
com/github/jllk/multidex/hook/JLLKInstrumentationProxy$ExecStartActivityDelegate.class
com/github/jllk/multidex/hook/JLLKInstrumentationProxy$ExecStartActivityDelegateV17.class%
```

2) build apk

Go to your `project.projectDir`, and run:

```bash
gradle assembleDebug
```

# See also

ClassDependenceAnalyser: [https://github.com/JLLK/ClassDependenceAnalyser](https://github.com/JLLK/ClassDependenceAnalyser)

multidex-sample: [https://github.com/JLLK/multidex-sample](https://github.com/JLLK/multidex-sample)


# License

This plugin is licensed under Apache License 2.0. See LICENSE for details.
package com.xuhuawei.gradle.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class GradleStudyPlugin implements Plugin<Project>{

    @Override
    void apply(Project target) {
        println "Hello project ${target.name}"
    }
}
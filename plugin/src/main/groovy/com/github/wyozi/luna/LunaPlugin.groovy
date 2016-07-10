package com.github.wyozi.luna

import org.gradle.api.Plugin
import org.gradle.api.Project

class LunaPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.tasks.create("compileLuna", LunaCompiler) {
            source = project.file("src/")
            include "**/*.luna"
            outputDir = project.file("out/")
        }
    }
}

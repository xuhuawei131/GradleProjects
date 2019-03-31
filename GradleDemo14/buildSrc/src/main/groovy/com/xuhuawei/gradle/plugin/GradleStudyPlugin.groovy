package com.xuhuawei.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

class GradleStudyPlugin implements Plugin<Project>{
    /**
     * 唯一需要实现的就是这个方法，参数就是引入了当前插件的project对象
     * @param target
     */
    @Override
    void apply(Project target) {
        target.extensions.create("xhwReleaseInfo", ReleaseInfoExtension)
        //创建Task
        Task task=target.tasks.create('xhwReleaseInfoTask',
                ReleaseInfoTask)
        Task buildtask=target.tasks.findByName("build")
        buildtask.doLast {
            task.execute()
        }
    }
}
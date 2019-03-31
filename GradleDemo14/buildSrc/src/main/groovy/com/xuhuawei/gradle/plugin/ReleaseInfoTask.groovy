package com.xuhuawei.gradle.plugin

import groovy.xml.MarkupBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * 我们在build.gradle中创建的task 默认都是继承DefaultTask
 * 自定义task，实现我们逻辑功能
 *
 */
class ReleaseInfoTask extends DefaultTask{

    ReleaseInfoTask(){
        group='xhw'
        description='update the release info'
    }
    /**
     * 被TaskAction注解修饰的方法
     * 是运行在 执行阶段
     * 我们的doFirst、doLast方法分别运行于我们方法之前和之后方法中
     */
    @TaskAction
    doAction(){
        updateInfo()
    }
    //真正的将Extension类中的信息写入指定文件中
    private void updateInfo(){
        //获取将要写入的信息
        String versionCodeMsg=project.extensions.xhwReleaseInfo.versionCodePlugin
        String versionNameMsg=project.extensions.xhwReleaseInfo.versionNamePlugin
        String versionInfoMsg=project.extensions.xhwReleaseInfo.versionInfoPlugin
        String fileName=project.extensions.xhwReleaseInfo.fileNamePlugin
        //下面使我们之前实现的逻辑
        println "******************* versionCode=$versionCodeMsg,versionName=$versionNameMsg,versionInfo=$versionInfoMsg"

        writeXML(versionCodeMsg,versionNameMsg,versionInfoMsg,project.file(fileName))
    }
    /**
     * 写入xml文件中
     * @param data
     * @param file
     */
    private void writeXML(String versionCodePlugin,String versionNamePlugin,String versionInfoPlugin,File file){
        //输出文件 等同于outputs.files

        println "versionCode=$versionCodePlugin,versionName=$versionNamePlugin,versionInfo=$versionInfoPlugin"
        def sw = new StringWriter()
        def xmlBuilder = new MarkupBuilder(sw)//生成xml数据的核心类
        String content = file.text

        //文件中没有内容
        if (content != null && content.size() == 0) {
            //没有写入数据，所以要写根节点releases
            xmlBuilder.releases {
                release {
                    versionName(versionNamePlugin)
                    versionCode(versionCodePlugin)
                    versionInfo(versionInfoPlugin)
                }
            }
            //写入文件
            file.withWriter {
                Writer writer ->
                    writer.append(sw.toString())
            }
        } else {
            List<String> readlines = file.readLines()
            //已经有了版本信息，那么就不用写根节点releases信息了
            xmlBuilder.release {
                versionName(versionNamePlugin)
                versionCode(versionCodePlugin)
                versionInfo(versionInfoPlugin)
            }
            //字符串行数
            int lineSize = readlines.size() - 1

            file.withWriter {
                Writer writer ->//writer 是会覆盖重写的，
                    readlines.eachWithIndex { String item, int i ->
                        if (i != lineSize) {//如果是最后一行 写一个换行符
                            writer.append(item + "\n")
                        } else {
                            //把我们新的xml数据写入进去
                            writer.append('\n' + sw.toString() + '\n')
                            writer.append(item)//这是最后一行 那个根节点的结束符
                        }
                    }
            }
        }
    }
}
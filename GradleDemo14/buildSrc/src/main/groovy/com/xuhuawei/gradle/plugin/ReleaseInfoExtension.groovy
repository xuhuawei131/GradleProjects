package  com.xuhuawei.gradle.plugin
/**
 * 与自定义的plugin进行参数传递
 * 就是创建了一个java bean
 */
public class ReleaseInfoExtension{
    public String versionCodePlugin
    public String versionNamePlugin
    public String versionInfoPlugin
    public String fileNamePlugin
    ReleaseInfoExtension(){
        println "ReleaseInfoExtension construct"
    }

    @Override
    public String toString() {
        return "ReleaseInfoExtension{" +
                "versionCodePlugin='" + versionCodePlugin + '\'' +
                ", versionNamePlugin='" + versionNamePlugin + '\'' +
                ", versionInfoPlugin='" + versionInfoPlugin + '\'' +
                ", fileNamePlugin='" + fileNamePlugin + '\'' +
                '}';
    }
}
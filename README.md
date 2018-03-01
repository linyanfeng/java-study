# java-study
java for object, threat...

## cacheline
  **wiki:** 
  * http://blog.csdn.net/lqp276/article/details/52231261
  * http://blog.chinaunix.net/uid-26726125-id-3761705.html
  
  **example:** 
  * http://hg.openjdk.java.net/code-tools/jol/file/tip/jol-samples/src/main/java/org/openjdk/jol/samples/
  
### classload
  **wiki**
  * http://blog.csdn.net/sunxianghuang/article/details/52093189
### intrumentation
 * 使用：
  ```$xslt
    mvn clean package
    java -javaagent:/Users/lykos/IdeaProjects/personalProject/java-study/instrumentation/target/instrumentation-1.0-SNAPSHOT-jar-with-dependencies.jar  lykos.study.com.instrument.SampleApp

```
  可以看到成功代理
  ```$xslt
Adding a SampleTransformer instance to the JVM.
Transforming lykos/study/com/instrument/SampleApp
java.util.concurrent.TimeUnit.sleep:3004
Hello World!
java.io.PrintStream.println:0
lykos.study.com.instrument.SampleApp.test:3006
```

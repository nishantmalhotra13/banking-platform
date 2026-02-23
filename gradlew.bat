@ECHO OFF
SET DIRNAME=%~dp0
SET CLASSPATH=%DIRNAME%\gradle\wrapper\gradle-wrapper.jar
"%JAVA_HOME%\bin\java" -cp "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*


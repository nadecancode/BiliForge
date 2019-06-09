package cn.charlotte.biliforge.inject.dependency.lead;

import cn.charlotte.biliforge.inject.dependency.ADependency;
import lombok.Getter;

@ADependency(maven = "com.squareup.retrofit2:converter-gson:2.4.0")
@ADependency(maven = "com.squareup.retrofit2:retrofit:2.6.0")
@ADependency(maven = "com.google.code.gson:gson:2.8.2")
@ADependency(maven = "io.netty:netty-all:4.1.29.Final")
@ADependency(maven = "com.google.guava:guava:26.0-jre")
@ADependency(maven = "com.fasterxml.jackson.core:jackson-databind:2.0.1")
@ADependency(maven = "com.squareup.okhttp3:okhttp:3.4.1")
@ADependency(maven = "com.squareup.okhttp3:logging-interceptor:3.11.0")
public class DependencyList {

    @Getter
    private static DependencyList instance;

    public DependencyList() {
        instance = this;
    }

}

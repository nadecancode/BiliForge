package cn.charlotte.biliforge.inject.dependency;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ADependencies.class)
public @interface ADependency {

    boolean loadIf() default true;

    String plugin() default "";

    String maven() default "";

    String mavenRepo() default Dependency.MAVEN_REPO_ALI;

    String url() default "";

}
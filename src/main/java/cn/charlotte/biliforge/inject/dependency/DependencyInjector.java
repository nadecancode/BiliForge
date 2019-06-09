package cn.charlotte.biliforge.inject.dependency;

import cn.charlotte.biliforge.inject.dependency.lead.DependencyList;
import lombok.Getter;

@Getter
public class DependencyInjector {

    public static void injectDependencies() {
        DependencyList list = new DependencyList();
        ADependency[] dependencies = getDependencies(list);
        if (dependencies.length != 0) {
            System.out.println("[BiliForge] Starting to load external maven dependencies...");
            for (ADependency dependency : dependencies) {
                String maven = dependency.maven();
                String repo = dependency.mavenRepo();
                String url = dependency.url();

                if (Dependency.requestLib(maven, repo, url)) {
                    System.out.println("[BiliForge] Loaded " + String.join(":", dependency.maven()));
                } else {
                    System.out.println("[BiliForge] Failed to load dependency | " + String.join(":", dependency.maven()));
                }
            }
            System.out.println("[BiliForge] Finished loading dependencies.");
        }
    }

    public static ADependency[] getDependencies(Object o) {
        ADependency[] dependencies = new ADependency[0];
        ADependencies d = o.getClass().getAnnotation(ADependencies.class);
        if (d != null) {
            dependencies = d.value();
        }
        ADependency d2 = o.getClass().getAnnotation(ADependency.class);
        if (d2 != null) {
            dependencies = new ADependency[]{d2};
        }
        return dependencies;
    }
}

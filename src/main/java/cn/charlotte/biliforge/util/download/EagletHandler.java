package cn.charlotte.biliforge.util.download;

@FunctionalInterface
public interface EagletHandler<T> {

    void handle(T event);

}

package ksy.medichat.util;

public interface Parser<T> {
    T parse(String str);
}

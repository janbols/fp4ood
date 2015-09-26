package be.guterfluss.transaction;

public interface Command<T> {
    T execute();
}

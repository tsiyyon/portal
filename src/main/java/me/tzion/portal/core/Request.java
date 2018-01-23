package me.tzion.portal.core;

import java.util.function.Consumer;

public interface Request<T> {
    Request<Object> EMPTY = new Empty();

    T get();

    <V> V field(Addressable<T> filter);

    void execute(Operation<T> operation);

    interface Operation<T> {
        void execute(Operator<T> operator);
    }

    interface Operator<T> {
        void operate(Consumer<T> consumer);
    }

    class Empty implements Request<Object> {
        Empty() {
        }

        @Override
        public Object get() {
            return new Object();
        }

        @Override
        public <V> V field(Addressable<Object> filter) {
            return null;
        }

        @Override
        public void execute(Operation<Object> operation) {

        }
    }

    interface Addressable<R> {
        <T> T locate(Object context);
    }
}

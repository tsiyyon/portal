package com.morpheus.portal;

import java.util.function.Consumer;

public interface Request<T> {
    Request<Object> EMPTY = new Empty();

    T get();

    <V> V field(String filter);

    void execute(Operation<T> operation);

    interface Operation<T> {
        void execute(Operator<T> operator);

    }

    interface Operator<T> {
        void operate(Consumer<T> consumer);
    }

    class Empty implements Request<Object> {
        public Empty() {
        }

        @Override
        public Object get() {
            return new Object();
        }

        @Override
        public <V> V field(String filter) {
            return null;
        }

        @Override
        public void execute(Operation<Object> operation) {

        }
    }
}

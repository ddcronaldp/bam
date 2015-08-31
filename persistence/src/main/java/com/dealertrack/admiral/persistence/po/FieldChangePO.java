package com.dealertrack.admiral.persistence.po;

import java.util.HashMap;
import java.util.Map;

public class FieldChangePO {
    private String field;
    private String value;

    private Operation operation;

    public Operation getOperation() {
        return operation;
    }

    public void setOperation( Operation operation ) {
        this.operation = operation;
    }

    public enum Operation {
        SET("set"),
        DELETE("delete"),
        APPEND("append");
        private final String value;
        private static Map<String, Operation> constants = new HashMap<>();

        static {
            for (Operation c: values()) {
                constants.put(c.value, c);
            }
        }

        Operation( String value ) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String getValue() {
            return value;
        }

        public static Operation fromValue(String value) {
            Operation constant = constants.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

        public static Map<String, Operation> getConstants() {
            return constants;
        }
    }

}

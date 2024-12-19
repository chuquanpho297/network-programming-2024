package com.networking.auction.protocol.request;

import java.lang.reflect.Field;

public abstract class Request {

    @Override
    public String toString() {
        try {
            StringBuilder result = new StringBuilder();
            Field[] fields = this.getClass().getDeclaredFields();

            result.append(getRequestType()).append("\n");
            for (Field field : fields) {
                field.setAccessible(true);
                result.append(field.get(this).toString().trim()).append("\n");
            }
            return result.toString();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Serialization error");
        }
    }

    public abstract int getRequestType();
}
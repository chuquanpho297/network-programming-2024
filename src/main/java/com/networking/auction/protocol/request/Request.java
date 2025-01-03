package com.networking.auction.protocol.request;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class Request {

    @Override
    public String toString() {
        try {
            StringBuilder result = new StringBuilder();
            Field[] fields = this.getClass().getDeclaredFields();

            result.append(getRequestType()).append("\n");
            for (Field field : fields) {
                field.setAccessible(true);

                if (Optional.class.isAssignableFrom(field.getType())) {
                    Optional<?> optional = (Optional<?>) field.get(this);
                    if (optional.isPresent()) {
                        Object value = optional.get();
                        if (value instanceof List) {
                            List<?> list = (List<?>) value;
                            String listString = list.stream()
                                    .map(Object::toString)
                                    .collect(Collectors.joining(" "));
                            result.append(listString.trim()).append("\n");
                        } else {
                            result.append(value.toString().trim()).append("\n");
                        }
                    } else {
                        result.append("NULL").append("\n");
                    }
                    continue;
                }

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
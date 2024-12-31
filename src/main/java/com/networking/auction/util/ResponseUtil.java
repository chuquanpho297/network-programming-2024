package com.networking.auction.util;

import java.util.Arrays;

public class ResponseUtil {
    public static String[] separateResponse(String response) {
        String[] parts = Arrays.stream(response.split("\n"))
                .filter(part -> !part.isEmpty())
                .toArray(String[]::new);
        return parts;
    }

    public static String[] separateResponseWithoutEndTag(String response) {
        String[] parts = separateResponse(response);
        return Arrays.copyOf(parts, parts.length - 1);
    }

    public final static String END_TAG = "End";
}

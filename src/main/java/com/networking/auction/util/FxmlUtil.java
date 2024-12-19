package com.networking.auction.util;

public enum FxmlUtil {
    LOGIN("login"),
    REGISTER("register"),
    ROOM("room");

    private final String dir;

    FxmlUtil(String dir) {
        this.dir = dir;
    }

    public static FxmlUtil fromString(String dir) {
        for (FxmlUtil config : FxmlUtil.values()) {
            if (config.dir.equalsIgnoreCase(dir)) {
                return config;
            }
        }
        throw new IllegalArgumentException("No enum constant " + FxmlUtil.class.getCanonicalName() + "." + dir);
    }

    public String getFxmlPath() {
        return String.format("%s/index.fxml", dir);
    }

    public String getCssPath() {
        return String.format("%s/styles.css", dir);
    }

    public String getTitle() {
        return dir;
    }
}
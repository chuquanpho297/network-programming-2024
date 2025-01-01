package com.networking.auction.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import com.networking.auction.HelloApplication;

import javafx.fxml.FXMLLoader;

public class FXMLLoaderController {
    protected FXMLLoader loader;

    protected FXMLLoaderController(){}

    protected FXMLLoaderController(String fxmlPath) throws IOException{
        URL location = Objects.requireNonNull(HelloApplication.class.getResource(fxmlPath));
        if (location == null) {
	        throw new IOException("FXML file not found: " + fxmlPath);
	    }
        this.loader = new FXMLLoader(location);
        this.loader.setController(this);
    }
    
    @SuppressWarnings("exports")
    public FXMLLoader getLoader() {
		return this.loader;
	}
}

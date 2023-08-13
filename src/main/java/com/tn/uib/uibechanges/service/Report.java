package com.tn.uib.uibechanges.service;

public class Report{
    private boolean isSourceConnected;
    private boolean isDestinationConnected;
    private boolean isSourcePathValid;
    private boolean isDestinationPathValid;
    private String message;

    public Report() {
        this.isSourceConnected = false;
        this.isDestinationConnected = false;
        this.isSourcePathValid = false;
        this.isDestinationPathValid = false;
        this.message = "Configuration invalide";
    }

    public boolean isSourceConnected() {
        return isSourceConnected;
    }

    public void setSourceConnected(boolean sourceConnected) {
        isSourceConnected = sourceConnected;
    }

    public boolean isDestinationConnected() {
        return isDestinationConnected;
    }

    public void setDestinationConnected(boolean destinationConnected) {
        isDestinationConnected = destinationConnected;
    }

    public boolean isSourcePathValid() {
        return isSourcePathValid;
    }

    public void setSourcePathValid(boolean sourcePathValid) {
        isSourcePathValid = sourcePathValid;
    }

    public boolean isDestinationPathValid() {
        return isDestinationPathValid;
    }

    public void setDestinationPathValid(boolean destinationPathValid) {
        isDestinationPathValid = destinationPathValid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

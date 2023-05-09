package com.example.project.Security.Advice;

import java.util.Date;

public class ErrorMessage {
    private Integer statusCode;
    private Date timestamp;
    private String message;
    private String description;

    public ErrorMessage (Integer statusCode, Date timestamp, String message, String description) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = message;
        this.description = description;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}

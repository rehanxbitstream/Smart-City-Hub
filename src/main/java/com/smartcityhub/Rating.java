package com.smartcityhub;

import java.sql.Timestamp;

public class Rating {
    private int ratingId;
    private int userId;
    private int serviceId;
    private int ratingValue;
    private String review;
    private Timestamp createdAt;
    private String serviceName;  // For display purposes

    // Constructors
    public Rating() {}
    
    public Rating(int userId, int serviceId, int ratingValue, String review) {
        this.userId = userId;
        this.serviceId = serviceId;
        this.ratingValue = ratingValue;
        this.review = review;
    }

    // Getters and Setters
    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "ratingId=" + ratingId +
                ", userId=" + userId +
                ", serviceId=" + serviceId +
                ", ratingValue=" + ratingValue +
                ", review='" + review + '\'' +
                ", createdAt=" + createdAt +
                ", serviceName='" + serviceName + '\'' +
                '}';
    }
}
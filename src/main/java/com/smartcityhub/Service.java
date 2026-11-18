package com.smartcityhub;

public class Service {
    private int serviceId;
    private int businessId;
    private String serviceName;
    private String description;
    
    private boolean approved;

    // Default constructor
    public Service() {}

    // Getters and setters
    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

   
    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    // toString() for debugging
    @Override
    public String toString() {
        return "Service{" +
                "serviceId=" + serviceId +
                ", businessId=" + businessId +
                ", serviceName='" + serviceName + '\'' +
                ", description='" + description + '\'' +
               
                ", approved=" + approved +
                '}';
    }
}

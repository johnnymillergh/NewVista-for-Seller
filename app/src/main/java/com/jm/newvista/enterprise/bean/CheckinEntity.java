package com.jm.newvista.enterprise.bean;

public class CheckinEntity {
    private UserEntity customer;
    private CustomerOrderEntity order;

    public UserEntity getCustomer() {
        return customer;
    }

    public void setCustomer(UserEntity customer) {
        this.customer = customer;
    }

    public CustomerOrderEntity getOrder() {
        return order;
    }

    public void setOrder(CustomerOrderEntity order) {
        this.order = order;
    }
}

package com.mycompany.warehouse_desktop.db.sale_transaction;

import java.sql.Timestamp;

public class SaleTransaction {

    private Long id;
    private Timestamp timestamp;       // Thời gian tạo giao dịch
    private Boolean isPaid;            // Đã thanh toán hay chưa
    private Long userIdEmployee;       // ID nhân viên thực hiện

    public SaleTransaction() {}

    public SaleTransaction(Long id, Timestamp timestamp, Boolean isPaid, Long userIdEmployee) {
        this.id = id;
        this.timestamp = timestamp;
        this.isPaid = isPaid;
        this.userIdEmployee = userIdEmployee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public Long getUserIdEmployee() {
        return userIdEmployee;
    }

    public void setUserIdEmployee(Long userIdEmployee) {
        this.userIdEmployee = userIdEmployee;
    }

    @Override
    public String toString() {
        return "Một lượt bán {" +
                "Mã giao dịch=" + id +
                ", Thời gian=" + timestamp +
                ", Đã thanh toán=" + isPaid +
                ", Mã nhân viên=" + userIdEmployee +
                '}';
    }
}

package com.mycompany.warehouse_desktop.db.sale_order_item;

public class SaleOrderItem {

    private SaleOrderItemId id;
    private Integer quantity;
    private Long price;

    public SaleOrderItem() {}

    public SaleOrderItem(SaleOrderItemId id, Integer quantity, Long price) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }

    public SaleOrderItemId getId() {
        return id;
    }

    public void setId(SaleOrderItemId id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Sản phẩm trong hóa đơn bán {" +
                "Mã sản phẩm=" + id.getProductId() +
                ", Mã giao dịch bán=" + id.getSaleTransactionId() +
                ", Số lượng=" + quantity +
                ", Đơn giá=" + price +
                '}';
    }
}

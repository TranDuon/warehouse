package com.mycompany.warehouse_desktop.db.purchase_order_item;

public class PurchaseOrderItem {

    private PurchaseOrderItemId id;
    private Integer quantity;
    private Long price;

    public PurchaseOrderItem() {}

    public PurchaseOrderItem(PurchaseOrderItemId id, Integer quantity, Long price) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }

    public PurchaseOrderItemId getId() { return id; }
    public void setId(PurchaseOrderItemId id) { this.id = id; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Long getPrice() { return price; }
    public void setPrice(Long price) { this.price = price; }
}

package com.mycompany.warehouse_desktop.db.sale_order_item;

public class SaleOrderItemId {

    private Long productId;
    private Long saleTransactionId;

    public SaleOrderItemId() {}

    public SaleOrderItemId(Long productId, Long saleTransactionId) {
        this.productId = productId;
        this.saleTransactionId = saleTransactionId;
    }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Long getSaleTransactionId() { return saleTransactionId; }
    public void setSaleTransactionId(Long saleTransactionId) {
        this.saleTransactionId = saleTransactionId;
    }
}

package com.mycompany.warehouse_desktop.db.purchase_order_item;

public class PurchaseOrderItemId {
    private Long productId;
    private Long purchaseTransactionId;

    public PurchaseOrderItemId() {}

    public PurchaseOrderItemId(Long productId, Long purchaseTransactionId) {
        this.productId = productId;
        this.purchaseTransactionId = purchaseTransactionId;
    }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Long getPurchaseTransactionId() { return purchaseTransactionId; }
    public void setPurchaseTransactionId(Long purchaseTransactionId) {
        this.purchaseTransactionId = purchaseTransactionId;
    }

    @Override
    public String toString() {
        return "Mã mặt hàng { " +
                "Mã sản phẩm=" + productId +
                ", Mã phiếu nhập=" + purchaseTransactionId +
                " }";
    }

}

package com.mycompany.warehouse_desktop.db.purchase_order_item;

import com.mycompany.warehouse_desktop.db.interfaces.ServiceInterface;

import java.util.List;

public class PurchaseOrderItemService
        implements ServiceInterface<PurchaseOrderItem, PurchaseOrderItemId> {

    private final PurchaseOrderItemRepo repo;

    public PurchaseOrderItemService() {
        this.repo = new PurchaseOrderItemRepo();
    }

    @Override
    public PurchaseOrderItem findById(PurchaseOrderItemId id) {
        return repo.findById(id);
    }

    @Override
    public List<PurchaseOrderItem> getList(Integer page, Integer size) {
        return repo.getList(page, size);
    }

    @Override
    public PurchaseOrderItem create(PurchaseOrderItem item) {
        return repo.create(item);
    }

    @Override
    public PurchaseOrderItem update(PurchaseOrderItemId id, PurchaseOrderItem item) {
        return repo.update(id, item);
    }

    @Override
    public Boolean delete(PurchaseOrderItemId id) {
        return repo.delete(id);
    }

    public List<PurchaseOrderItem> findByPurchaseTransactionId(Long transactionId) {
        return repo.findByPurchaseTransactionId(transactionId);
    }

    public Boolean deleteByProductId(Long productId) {
        return repo.deleteByProductId(productId);
    }

    public Boolean deleteByPurchaseTransactionId(Long transactionId) {
        return repo.deleteByPurchaseTransactionId(transactionId);
    }
}

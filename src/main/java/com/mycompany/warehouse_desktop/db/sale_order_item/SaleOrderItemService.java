package com.mycompany.warehouse_desktop.db.sale_order_item;

import com.mycompany.warehouse_desktop.db.interfaces.ServiceInterface;

import java.util.List;

public class SaleOrderItemService implements ServiceInterface<SaleOrderItem, SaleOrderItemId> {

    private final SaleOrderItemRepository repository;

    public SaleOrderItemService() {
        this.repository = new SaleOrderItemRepository();
    }

    @Override
    public SaleOrderItem findById(SaleOrderItemId id) {
        return repository.findById(id);
    }

    @Override
    public List<SaleOrderItem> getList(Integer page, Integer size) {
        return repository.getList(page, size);
    }

    @Override
    public SaleOrderItem create(SaleOrderItem item) {
        return repository.create(item);
    }

    @Override
    public SaleOrderItem update(SaleOrderItemId id, SaleOrderItem item) {
        item.setId(id);
        return repository.update(id, item);
    }

    @Override
    public Boolean delete(SaleOrderItemId id) {
        return repository.delete(id);
    }

    public List<SaleOrderItem> findBySaleTransactionId(Long transactionId) {
        return repository.findBySaleTransactionId(transactionId);
    }
}

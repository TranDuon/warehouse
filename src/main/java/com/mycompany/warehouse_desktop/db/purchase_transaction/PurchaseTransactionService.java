package com.mycompany.warehouse_desktop.db.purchase_transaction;

import com.mycompany.warehouse_desktop.db.interfaces.ServiceInterface;

import java.sql.Timestamp;
import java.util.List;

public class PurchaseTransactionService implements ServiceInterface<PurchaseTransaction, Long> {

    private final PurchaseTransactionRepo repo;

    public PurchaseTransactionService() {
        this.repo = new PurchaseTransactionRepo();
    }

    @Override
    public PurchaseTransaction findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public List<PurchaseTransaction> getList(Integer page, Integer size) {
        return repo.getList(page, size);
    }

    @Override
    public PurchaseTransaction create(PurchaseTransaction transaction) {
        return repo.create(transaction);
    }

    @Override
    public PurchaseTransaction update(Long id, PurchaseTransaction transaction) {
        return repo.update(id, transaction);
    }

    @Override
    public Boolean delete(Long id) {
        return repo.delete(id);
    }

    public List<PurchaseTransaction> findByTime(Timestamp from, Timestamp to) {
        return repo.findByTime(from, to);
    }
}

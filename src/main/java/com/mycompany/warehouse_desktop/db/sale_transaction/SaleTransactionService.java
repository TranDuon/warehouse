package com.mycompany.warehouse_desktop.db.sale_transaction;

import com.mycompany.warehouse_desktop.db.interfaces.ServiceInterface;

import java.sql.Timestamp;
import java.util.List;

public class SaleTransactionService implements ServiceInterface<SaleTransaction, Long> {

    private final SaleTransactionRepository repository;

    public SaleTransactionService() {
        this.repository = new SaleTransactionRepository();
    }

    @Override
    public SaleTransaction findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<SaleTransaction> getList(Integer page, Integer size) {
        return repository.getList(page, size);
    }

    @Override
    public SaleTransaction create(SaleTransaction transaction) {
        return repository.create(transaction);
    }

    @Override
    public SaleTransaction update(Long id, SaleTransaction transaction) {
        return repository.update(id, transaction);
    }

    @Override
    public Boolean delete(Long id) {
        return repository.delete(id);
    }

    public List<SaleTransaction> findByTime(Timestamp from, Timestamp to) {
        return repository.findByTime(from, to);
    }
}

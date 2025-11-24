package com.mycompany.warehouse_desktop.db.sale_invoice;

import com.mycompany.warehouse_desktop.db.interfaces.ServiceInterface;

import java.util.List;

public class SaleInvoiceService implements ServiceInterface<SaleInvoice, Long> {

    private final SaleInvoiceRepository repository;

    public SaleInvoiceService() {
        this.repository = new SaleInvoiceRepository();
    }

    @Override
    public SaleInvoice findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<SaleInvoice> getList(Integer page, Integer size) {
        return repository.getList(page, size);
    }

    @Override
    public SaleInvoice create(SaleInvoice invoice) {
        return repository.create(invoice);
    }

    @Override
    public SaleInvoice update(Long id, SaleInvoice invoice) {
        return repository.update(id, invoice);
    }

    @Override
    public Boolean delete(Long id) {
        return repository.delete(id);
    }
}

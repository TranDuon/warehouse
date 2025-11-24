package com.mycompany.warehouse_desktop.db.product;

import com.mycompany.warehouse_desktop.db.interfaces.ServiceInterface;

import java.util.List;

public class ProductService implements ServiceInterface<ProductEntity, Long> {

    private final ProductRepository productRepository;

    public ProductService() {
        this.productRepository = new ProductRepository();
    }

    @Override
    public ProductEntity findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<ProductEntity> getList(Integer page, Integer size) {
        return productRepository.getList(page, size);
    }

    @Override
    public ProductEntity create(ProductEntity product) {
        return productRepository.create(product);
    }

    @Override
    public ProductEntity update(Long id, ProductEntity product) {
        return productRepository.update(id, product);
    }

    @Override
    public Boolean delete(Long id) {
        return productRepository.delete(id);
    }

    public List<ProductEntity> findByName(String name) {
        return productRepository.findByName(name);
    }
}

package com.mycompany.warehouse_desktop.db.product;

public class ProductEntity {

    private Long id;
    private String name;
    private Long price;
    private String unit;
    private String description;
    private Integer quantity;

    public ProductEntity() {}

    public ProductEntity(Long id, String name, Long price, String unit, String description, Integer quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.description = description;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Sản phẩm { " +
                "ID=" + id +
                ", Tên='" + name + '\'' +
                ", Giá=" + price +
                ", Đơn vị='" + unit + '\'' +
                ", Mô tả='" + description + '\'' +
                ", Số lượng tồn=" + quantity +
                " }";
    }
}

package com.mycompany.warehouse_desktop.db.interfaces;

import java.util.List;

public interface ServiceInterface<T, Id> {

    T findById(Id id);

    List<T> getList(Integer pageIndex, Integer pageSize);

    T create(T t);

    T update(Id id, T t);

    Boolean delete(Id id);
}

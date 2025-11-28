package com.mycompany.warehouse_desktop.db.interfaces;

import java.sql.ResultSet;
import java.util.List;

public interface RepoInterface<T, Id> {

    T getFromResultSet(ResultSet rs);

    T findById(Id id);

    List<T> getList(Integer pageIndex, Integer pageSize);

    T create(T t);

    T update(Id id, T t);

    Boolean delete(Id id);
}

package com.cgg.framework.dao.repository;

import com.cgg.framework.dao.entity.TreeEntity;
import org.springframework.data.repository.CrudRepository;

public interface TreeRepository<T extends TreeEntity> extends CrudRepository<T, Long> {
}

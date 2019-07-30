package com.ascpm.todo.repository.comm;

import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.impl.JPAQuery;

import javax.persistence.EntityManager;

public interface MappingRepository<T> {

    default JPAQuery<T> getQuery(EntityManager entityManager, EntityPathBase<T> qEntity) {
        return new JPAQuery<T>(entityManager).from(qEntity);
    }
}

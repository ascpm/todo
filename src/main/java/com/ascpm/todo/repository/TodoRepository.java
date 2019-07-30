package com.ascpm.todo.repository;

import com.ascpm.todo.data.entity.TodoEntity;
import com.ascpm.todo.repository.comm.MappingRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface TodoRepository extends JpaRepository<TodoEntity, Long>, QuerydslPredicateExecutor<TodoEntity>,
        MappingRepository<TodoEntity> {

    Optional<TodoEntity> findByPrevId(long prevId);

    Optional<TodoEntity> findByNextId(long nextId);
}

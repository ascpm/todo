package com.ascpm.todo.service;

import com.ascpm.todo.data.entity.TodoEntity;
import com.ascpm.todo.data.request.TodoRequest;
import com.ascpm.todo.data.response.TodoResponse;
import com.ascpm.todo.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TodoService {

    @Autowired
    private TodoRepository repository;

    public List<TodoResponse> findAll() {
        List<TodoEntity> entities = this.repository.findAll();
        List<TodoResponse> responses = new ArrayList<>();
        entities.stream()
                .filter(it -> it.getPrevId() == -1L)
                .findAny()
                .ifPresent(it -> sort(entities, it.getId(), responses));
        return responses;
    }

    @Transactional(rollbackFor = Exception.class)
    public TodoResponse modify(TodoRequest request) {
        return this.repository.findById(request.getId())
                .map(it -> {
                    this.pop(it);
                    this.push(it, request.getPrevId(), request.getNextId());
                    request.toModify(it);
                    return this.repository.save(it).toResponse();
                })
                .orElseThrow(RuntimeException::new);
    }

    @Transactional(rollbackFor = Exception.class)
    public TodoResponse create(TodoRequest request) {
        return this.repository.findByPrevId(-1L)
                .map(it -> {
                    request.setNextId(it.getId());
                    TodoEntity creatEntity = this.repository.save(request.toCreate());
                    it.setPrevId(creatEntity.getId());
                    this.repository.save(it);
                    return creatEntity.toResponse();
                })
                .orElseGet(() -> {
                    request.setNextId(-1L);
                    return this.repository.save(request.toCreate()).toResponse();
                });
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(TodoRequest request) {
        this.repository.findById(request.getId())
                .ifPresent(it -> {
                    this.pop(it);
                    this.repository.delete(it);
                });

    }

    private void push(TodoEntity entity, long prevId, long nextId) {
        this.repository.findById(prevId)
                .ifPresent(e -> {
                    e.setNextId(entity.getId());
                    this.repository.save(e);
                });
        this.repository.findById(nextId)
                .ifPresent(e -> {
                    e.setPrevId(entity.getId());
                    this.repository.save(e);
                });
    }

    private void pop(TodoEntity entity) {
        long prevId = entity.getPrevId();
        long nextId = entity.getNextId();
        this.repository.findByPrevId(entity.getId())
                .ifPresent(e -> {
                    e.setPrevId(prevId);
                    this.repository.save(e);
                });
        this.repository.findByNextId(entity.getId())
                .ifPresent(e -> {
                    e.setNextId(nextId);
                    this.repository.save(e);
                });
    }

    private void sort(List<TodoEntity> entities, long id, List<TodoResponse> responses) {
        entities.stream()
                .filter(it -> it.getId() == id)
                .findAny()
                .map(TodoEntity::toResponse)
                .ifPresent(it -> {
                    responses.add(it);
                    long nextId = it.getNextId();

                    if (nextId != -1L) {
                        sort(entities, nextId, responses);
                    }
                });
    }
}

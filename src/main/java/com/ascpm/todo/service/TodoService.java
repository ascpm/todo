package com.ascpm.todo.service;

import com.ascpm.todo.data.converter.TodoEntityToResponseConverter;
import com.ascpm.todo.data.converter.TodoRequestToEntityConverter;
import com.ascpm.todo.data.entity.TodoEntity;
import com.ascpm.todo.data.request.TodoRequest;
import com.ascpm.todo.data.response.TodoResponse;
import com.ascpm.todo.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TodoService {

    @Autowired
    private TodoRepository repository;
    @Autowired
    private TodoEntityToResponseConverter responseConverter;
    @Autowired
    private TodoRequestToEntityConverter entityConverter;

    public List<TodoResponse> findAll() {
        return this.repository.findAll()
                .stream()
                .map(this.responseConverter::convert)
                .collect(Collectors.toList());
    }

    public TodoResponse findById(long id) {
        return this.repository.findById(id)
                .map(this.responseConverter::convert)
                .orElse(TodoResponse.empty());
    }

    @Transactional(rollbackFor = Throwable.class)
    public TodoResponse save(TodoRequest request) {
        log.info("### id: {} ###", request.getId());
        if (ObjectUtils.isEmpty(request.getId())) {
            return this.responseConverter.convert(this.create(request));
        } else {
            return this.responseConverter.convert(this.modify(request));
        }
    }

    private TodoEntity modify(TodoRequest request) {
        return this.repository.findById(request.getId())
                .map(it -> {
                    long prevId = it.getPrevId();
                    long nextId = it.getNextId();
                    long modifyPrevId = request.getPrevId();
                    long modifyNextId = request.getNextId();
                    this.repository.findByPrevId(it.getId())
                            .ifPresent(e -> {
                                e.setPrevId(prevId);
                                this.repository.save(e);
                            });
                    this.repository.findByNextId(it.getId())
                            .ifPresent(e -> {
                                e.setNextId(nextId);
                                this.repository.save(e);
                            });
                    this.repository.findById(modifyPrevId)
                            .ifPresent(e -> {
                                e.setNextId(it.getId());
                                this.repository.save(e);
                            });
                    this.repository.findById(modifyNextId)
                            .ifPresent(e -> {
                                e.setPrevId(it.getId());
                                this.repository.save(e);
                            });
                    return this.repository.save(this.entityConverter.convertToModify(request));
                })
                .orElseThrow(RuntimeException::new);
    }

    private TodoEntity create(TodoRequest request) {
        return this.repository.findByPrevId(-1L)
                .map(it -> {
                    request.setNextId(it.getId());
                    TodoEntity creatEntity = this.repository.save(this.entityConverter.convertToCreate(request));
                    it.setPrevId(creatEntity.getId());
                    this.repository.save(it);
                    return creatEntity;
                })
                .orElse(this.repository.save(this.entityConverter.convertToCreate(request)));
    }
}

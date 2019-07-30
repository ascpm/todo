package com.ascpm.todo.data.converter;

import com.ascpm.todo.data.entity.TodoEntity;
import com.ascpm.todo.data.request.TodoRequest;
import org.springframework.stereotype.Component;

@Component
public class TodoRequestToEntityConverter {

    public TodoEntity convertToCreate(TodoRequest request) {
        return TodoEntity
                .builder()
                .uid(request.getUid())
                .name(request.getName())
                .prevId(-1L)
                .nextId(request.getNextId())
                .done(false)
                .build();
    }

    public TodoEntity convertToModify(TodoRequest request) {
        return TodoEntity
                .builder()
                .id(request.getId())
                .uid(request.getUid())
                .name(request.getName())
                .prevId(request.getPrevId())
                .nextId(request.getNextId())
                .done(request.isDone())
                .build();
    }

}

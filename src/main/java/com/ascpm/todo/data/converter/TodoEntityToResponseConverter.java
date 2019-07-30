package com.ascpm.todo.data.converter;

import com.ascpm.todo.data.entity.TodoEntity;
import com.ascpm.todo.data.response.TodoResponse;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
public class TodoEntityToResponseConverter {

    public TodoResponse convert(TodoEntity entity) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return TodoResponse
                .builder()
                .id(entity.getId())
                .uid(entity.getUid())
                .name(entity.getName())
                .prevId(entity.getPrevId())
                .nextId(entity.getNextId())
                .createdTime(formatter.format(entity.getCreatedTime().toLocalDateTime()))
                .modifiedTime(Optional.ofNullable(entity.getModifiedTime())
                        .map(mt -> formatter.format(mt.toLocalDateTime()))
                        .orElse(""))
                .build();
    }

}

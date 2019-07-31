package com.ascpm.todo.data.request;

import com.ascpm.todo.data.entity.TodoEntity;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TodoRequest implements Serializable {

    private static final long serialVersionUID = 8560094045738591436L;

    private long id;
    private String uid;
    private String name;
    private long prevId;
    private long nextId;
    private boolean done;

    public TodoEntity toCreate() {
        return TodoEntity
                .builder()
                .uid(this.uid)
                .name(this.name)
                .prevId(-1L)
                .nextId(this.nextId)
                .done(false)
                .build();
    }

    public void toModify(TodoEntity entity) {
        entity.setUid(this.uid);
        entity.setName(this.name);
        entity.setPrevId(this.prevId);
        entity.setNextId(this.nextId);
        entity.setDone(this.done);
    }

}

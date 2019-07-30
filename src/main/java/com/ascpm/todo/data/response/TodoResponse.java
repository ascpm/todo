package com.ascpm.todo.data.response;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TodoResponse implements Serializable {

    private static final long serialVersionUID = -5421163081285275512L;

    private long id;
    private String uid;
    private String name;
    private long prevId;
    private long nextId;
    private boolean done;
    private String createdTime;
    private String modifiedTime;

    public static TodoResponse empty() {
        return TodoResponse.builder().build();
    }

}

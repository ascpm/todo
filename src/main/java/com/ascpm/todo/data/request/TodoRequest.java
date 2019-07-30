package com.ascpm.todo.data.request;

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

}

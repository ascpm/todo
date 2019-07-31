package com.ascpm.todo.data.entity;

import com.ascpm.todo.data.entity.comm.MappingEntity;
import com.ascpm.todo.data.response.TodoResponse;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Entity
@Table(name = "`todo`")
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class TodoEntity extends MappingEntity implements Serializable {

    private static final long serialVersionUID = 1968586716372687738L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`", nullable = false)
    private long id;

    @Column(name = "`uid`", length = 40, nullable = false)
    private String uid;

    @Column(name = "`name`", length = 100, nullable = false)
    private String name;

    @Column(name = "`prev_id`")
    private long prevId;

    @Column(name = "`next_id`")
    private long nextId;

    @Column(name = "`done`", nullable = false)
    private boolean done;

    @Builder
    public TodoEntity(Timestamp createdTime, Timestamp modifiedTime,
                      long id,
                      String uid, String name,
                      long prevId, long nextId,
                      boolean done) {
        super(createdTime, modifiedTime);
        this.id = id;
        this.uid = uid;
        this.name = name;
        this.prevId = prevId;
        this.nextId = nextId;
        this.done = done;
    }

    public TodoResponse toResponse() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return TodoResponse
                .builder()
                .id(this.id)
                .uid(this.uid)
                .name(this.name)
                .prevId(this.prevId)
                .nextId(this.nextId)
                .createdTime(formatter.format(this.getCreatedTime().toLocalDateTime()))
                .modifiedTime(Optional.ofNullable(this.getModifiedTime())
                        .map(mt -> formatter.format(mt.toLocalDateTime()))
                        .orElse(""))
                .build();
    }

}

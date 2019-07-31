package com.ascpm.todo.controller;

import com.ascpm.todo.data.request.TodoRequest;
import com.ascpm.todo.data.response.TodoResponse;
import com.ascpm.todo.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api/todos")
@Slf4j
public class TodoController {

    @Autowired
    private TodoService service;

    @GetMapping
    public ResponseEntity<List<TodoResponse>> get() {
        return ResponseEntity.ok(this.service.findAll());
    }

    @PostMapping
    public ResponseEntity<TodoResponse> post(@RequestBody TodoRequest request) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(location).body(this.service.create(request));
    }

    @PatchMapping
    public ResponseEntity<TodoResponse> patch(@RequestBody TodoRequest request) {
        return ResponseEntity.ok(this.service.modify(request));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody TodoRequest request) {
        this.service.delete(request);
        return ResponseEntity.noContent().build();
    }
}

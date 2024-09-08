package com.ivoyant.springboot_todo.service;

import com.ivoyant.springboot_todo.entity.Todo;
import com.ivoyant.springboot_todo.exception.TodoNotFoundException;
import com.ivoyant.springboot_todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {


    private TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo saveTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public void deleteTodoById(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new TodoNotFoundException("Todo with ID " + id + " not found.");
        }
        todoRepository.deleteById(id);
    }


    public Todo findTodoById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo with ID " + id + " not found."));
    }
}

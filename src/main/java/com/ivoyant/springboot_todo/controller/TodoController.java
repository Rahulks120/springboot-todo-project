package com.ivoyant.springboot_todo.controller;

import com.ivoyant.springboot_todo.entity.Todo;
import com.ivoyant.springboot_todo.exception.TodoNotFoundException;
import com.ivoyant.springboot_todo.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TodoController {


    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("todos", todoService.getAllTodos());
        model.addAttribute("newTodo", new Todo());
        return "index";
    }

    @PostMapping("/add")
    public String addTodo(@ModelAttribute("newTodo") Todo todo) {
        todoService.saveTodo(todo);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable("id") Long id) {
        todoService.deleteTodoById(id);
        return "redirect:/";
    }

    @GetMapping("/complete/{id}")
    public String completeTodo(@PathVariable("id") Long id) {
        Todo todo = todoService.findTodoById(id);
        if (todo != null) {
            todo.setCompleted(!todo.isCompleted());
            todoService.saveTodo(todo);
        }
        return "redirect:/";
    }
    @ExceptionHandler(TodoNotFoundException.class)
    public String handleTodoNotFoundException(TodoNotFoundException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        model.addAttribute("todos", todoService.getAllTodos());
        model.addAttribute("newTodo", new Todo());
        return "index";
    }
}

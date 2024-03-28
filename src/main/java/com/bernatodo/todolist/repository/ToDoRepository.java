package com.bernatodo.todolist.repository;

import com.bernatodo.todolist.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoRepository extends JpaRepository<ToDo, Long>{

}

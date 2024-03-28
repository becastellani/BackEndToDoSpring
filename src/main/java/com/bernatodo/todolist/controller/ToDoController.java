package com.bernatodo.todolist.controller;

import com.bernatodo.todolist.model.ToDo;
import com.bernatodo.todolist.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todo")
public class ToDoController {
    @Autowired
    private ToDoRepository toDoRepository;

    @GetMapping
    public List<ToDo> listarTodo(){
        return toDoRepository.findAll();
    }

    @PostMapping
    public ToDo criarTodo(@RequestBody ToDo toDo){
        toDo.setCreated_at(new Date());
        return toDoRepository.save(toDo);
    }

    @PutMapping("/{id}")
    public ToDo atualizarTodo(@PathVariable Long id, @RequestBody ToDo toDoAtualizado){
        return toDoRepository.findById(id)
                .map(toDo -> {
                    toDo.setDescription(toDoAtualizado.getDescription());
                    toDo.setUpdated_at(new Date());
                    toDo.setStatus(toDoAtualizado.isStatus());
                    return toDoRepository.save(toDo);
                })
                .orElseThrow();
    }

    @PatchMapping("/{id}/complete")
    public ToDo marcarComoConcluido(@PathVariable Long id) {
        Optional<ToDo> optionalToDo = toDoRepository.findById(id);
        if (optionalToDo.isPresent()) {
            ToDo toDo = optionalToDo.get();
            toDo.setStatus(true);
            toDo.setUpdated_at(new Date());
            return toDoRepository.save(toDo);
        } else {
            throw new RuntimeException("To-Do não encontrado com o ID: " + id);
        }
    }

    @PatchMapping("/{id}/todo")
    public ToDo marcarComoAFazer(@PathVariable Long id) {
        Optional<ToDo> optionalToDo = toDoRepository.findById(id);
        if (optionalToDo.isPresent()) {
            ToDo toDo = optionalToDo.get();
            toDo.setStatus(false);
            toDo.setUpdated_at(new Date());
            return toDoRepository.save(toDo);
        } else {
            throw new RuntimeException("To-Do não encontrado com o ID: " + id);
        }
    }


    @DeleteMapping("/{id}")
    public void deletarTodo(@PathVariable Long id){
        toDoRepository.deleteById(id);
    }

}

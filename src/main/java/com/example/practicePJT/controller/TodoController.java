package com.example.practicePJT.controller;

import com.example.practicePJT.dto.TodoForm;
import com.example.practicePJT.entity.Todo;
import com.example.practicePJT.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class TodoController {
  @Autowired
  private TodoRepository todoRepository;

  @GetMapping("/todos/home")
  public String home() {return "todos/home";}

  @GetMapping("/todos/new")
  public String newTodoForm() {return "todos/new";}

  @PostMapping("/todos/create")
  public String create(TodoForm form) {
    log.info(form.toString());

    Todo todo = form.toEntity();
    log.info(todo.toString());

    Todo saved = todoRepository.save(todo);
    log.info(saved.toString());
    return "redirect:/todos/" + saved.getId();
  }

  @GetMapping("/todos/{id}")
  public String detail(@PathVariable Long id, Model model) {
    log.info("id = " + id);

    Todo todoEntity = todoRepository.findById(id).orElse(null);
    model.addAttribute("todo",todoEntity);
    return "todos/detail";
  }

  @GetMapping("/todos/list")
  public String list(Model model) {
    List<Todo> todolist = todoRepository.findAll();
    model.addAttribute("todolist",todolist);
    return "todos/list";
  }

  @GetMapping("/todos/{id}/edit")
  public String edit(@PathVariable Long id, Model model) {
    Todo todoEntity = todoRepository.findById(id).orElse(null);
    model.addAttribute("todo",todoEntity);
    if (todoEntity != null) {
      model.addAttribute("isUrgent", "긴급".equals(todoEntity.getPriority()));
      model.addAttribute("isNormal", "보통".equals(todoEntity.getPriority()));
      model.addAttribute("isLow", "나중".equals(todoEntity.getPriority()));
      model.addAttribute("isTODO","TODO".equals(todoEntity.getStatus()));
      model.addAttribute("isPROG","IN_PROGRESS".equals(todoEntity.getStatus()));
      model.addAttribute("isDONE","DONE".equals(todoEntity.getStatus()));
      model.addAttribute("isHOLD","HOLD".equals(todoEntity.getStatus()));
    }
    return "todos/edit";
  }

  @PostMapping("/todos/update")
  public String update(TodoForm form) {
    log.info(form.toString());

    Todo todoEntity = form.toEntity();
    log.info(todoEntity.toString());

    Todo target = todoRepository.findById(todoEntity.getId()).orElse(null);
    if (target != null) {
      todoRepository.save(todoEntity);
    }
    return "redirect:/todos/" + todoEntity.getId();
  }

  @GetMapping("/todos/{id}/delete")
  public String delete(@PathVariable Long id, RedirectAttributes rttr) {
    log.info("삭제요청");

    Todo todoEntity = todoRepository.findById(id).orElse(null);

    if (todoEntity != null) {
      todoRepository.delete(todoEntity);
      rttr.addFlashAttribute("msg", id + "번 삭제 완료!");
    }
    return "redirect:/todos/list";
  }
}

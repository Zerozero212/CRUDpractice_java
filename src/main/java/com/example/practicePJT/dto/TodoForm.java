package com.example.practicePJT.dto;

import com.example.practicePJT.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@ToString
public class TodoForm {
  private Long id;
  private String title;
  private String content;
  private LocalDate deadline;
  private String priority;
  private String status;

  public Todo toEntity() {
    String defaultStatus = (this.status == null || this.status.isEmpty()) ? "TODO" : this.status;
    return new Todo(id,title,content,deadline,priority,defaultStatus,null,null);
  }
}

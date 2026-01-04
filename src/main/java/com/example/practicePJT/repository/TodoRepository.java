package com.example.practicePJT.repository;

import com.example.practicePJT.entity.Todo;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TodoRepository extends CrudRepository<Todo,Long> {
  @Override
  @NonNull
  List<Todo> findAll();

  // 1. 생성일 역순으로 정렬해서 가져오기
  List<Todo> findAllByOrderByCreatedAtDesc();

  // 2. 특정 상태(status)의 할 일만 가져오기 (ex : 진행 중인 것만)
  List<Todo> findByStatus(String status);

  // 3. 마감기한이 임박한 순서대로 가져오기
  List<Todo> findAllByOrderByDeadlineAsc();
}
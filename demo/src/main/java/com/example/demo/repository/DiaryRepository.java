package com.example.demo.repository;

import com.example.demo.model.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    // JpaRepository 已經內建了 findAll() 和 save()，所以這裡空空的就可以了！
}
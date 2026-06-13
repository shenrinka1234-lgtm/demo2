package com.example.demo.controller;

import com.example.demo.model.Diary;
import com.example.demo.repository.DiaryRepository;
import com.example.demo.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diaries")
@CrossOrigin(origins = "*")
public class DiaryController {

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private AiService aiService; // 👈 聘請剛剛寫好的 AI 服務

    @GetMapping
    public List<Diary> getAllDiaries() {
        return diaryRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    @PostMapping
    public Diary createDiary(@RequestBody Diary diary) {
        // 1. 先把前端傳來的文字，丟給 AI 分析
        String aiResult = aiService.analyzeSentiment(diary.getContent());
        
        // 2. 把 AI 分析的結果，塞進日記的情緒欄位裡
        diary.setSentiment(aiResult);
        
        // 3. 存入資料庫！
        return diaryRepository.save(diary);
    }
}
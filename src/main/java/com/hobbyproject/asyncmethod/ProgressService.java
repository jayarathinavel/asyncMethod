package com.hobbyproject.asyncmethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProgressService {
    @Autowired
    ProgressRepository repository;

    ProgressEntity entity = new ProgressEntity();

    public void saveProgress(String progressId, String message){
        entity.setProgressId(progressId);
        entity.setMessage(message);
        repository.save(entity);
    }

    public String getProgress(String progressId){
        return repository.getById(progressId).getMessage();
    }
    public void deleteProgress(String progressId){
        repository.deleteById(progressId);
    }
}

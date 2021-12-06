package com.hobbyproject.asyncmethod;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

@org.springframework.stereotype.Service
public class Service {
    @Autowired
    Repository repository;

    @Autowired
    ProgressService progressService;

    Entity entity = new Entity();

    private final Logger logger = LoggerFactory.getLogger(Service.class);

    @Async("asyncExecutor")
    public void saveToDatabase(File file, String progressId){
        logger.info("Reading Excel File");
        try(FileInputStream fileInputStream = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(fileInputStream)){
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet){
                if(row.getRowNum()>0){
                    readCellsAndSaveRow(progressId, row);
                }
                //Intentional Sleep to mock big process, if needed. Even the normal flow takes a minute to insert all 10000 rows.
                //TimeUnit.SECONDS.sleep(1);
            }
            logger.info("Saved all values to Database");
            progressService.saveProgress(progressId, "Saved all values to Database");
            TimeUnit.SECONDS.sleep(10); //Intentional Sleep to mock big process
            progressService.deleteProgress(progressId);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void readCellsAndSaveRow(String progressId, Row row) {
        List<Cell> cellValueList = new ArrayList<>();
        String message = row.getRowNum() + " rows inserted.";
        for (Cell cell : row){
            cellValueList.add(cell);
        }
        filterAndSaveRow(cellValueList);
        progressService.saveProgress(progressId, message);
    }

    private void filterAndSaveRow(List<Cell> cellValueList) {
        entity.setRestaurantId((long) cellValueList.get(0).getNumericCellValue());
        entity.setRestaurantName(cellValueList.get(1).toString());
        entity.setCity(cellValueList.get(3).toString());
        entity.setAddress(cellValueList.get(4).toString());
        entity.setRating(Double.valueOf(cellValueList.get(17).toString()));
        repository.save(entity);
        entity = new Entity();
    }
}

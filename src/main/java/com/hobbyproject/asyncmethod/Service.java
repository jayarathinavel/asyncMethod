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

@org.springframework.stereotype.Service
public class Service {
    @Autowired
    Repository repository;

    Entity entity = new Entity();

    private final Logger logger = LoggerFactory.getLogger(Service.class);

    public void saveToDatabase(File file){
        logger.info("Reading Excel File");
        try(FileInputStream fileInputStream = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(fileInputStream)){
            Sheet sheet = workbook.getSheetAt(0);
            List<Cell> cellValueList = new ArrayList<>();
            for (Row row : sheet){
                for (Cell cell : row){
                    cellValueList.add(cell);
                }
                filterAndSaveRows(cellValueList);
                cellValueList = new ArrayList<>();
                TimeUnit.SECONDS.sleep(1); //Intentional Sleep to mock big process
            }
            logger.info("Saved all values to Database");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void filterAndSaveRows(List<Cell> cellValueList) throws InterruptedException {
        entity.setRestaurantId(cellValueList.get(0).toString());
        entity.setRestaurantName(cellValueList.get(1).toString());
        entity.setCity(cellValueList.get(3).toString());
        entity.setAddress(cellValueList.get(4).toString());
        entity.setRating(Double.valueOf(cellValueList.get(17).toString()));
        repository.save(entity);
        entity = new Entity();
    }
}

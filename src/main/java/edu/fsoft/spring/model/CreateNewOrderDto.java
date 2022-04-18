package edu.fsoft.spring.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreateNewOrderDto {
    private int id;
    private LocalDate createdDate;
    private String createdBy;
    private String customerPhone;
    private float total;
    private int point;
    private List<Integer> listItems;
}

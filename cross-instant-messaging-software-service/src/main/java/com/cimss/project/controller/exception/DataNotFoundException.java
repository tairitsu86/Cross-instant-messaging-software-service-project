package com.cimss.project.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DataNotFoundException extends RuntimeException{
    private String dataField;
    private String dataValue;
    public String getErrorMessage(){
        return String.format("The data %s=%s is not found in database!",dataField,dataValue);
    }

}

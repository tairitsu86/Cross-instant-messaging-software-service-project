package com.cimss.project.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestValueNotFoundException extends RuntimeException{
    private List<String> values;
    public String RequestValue(){
        String result = String.format("Request value not found, this method request: %s",values.get(0));
        for(int i=1,l=values.size();i<l;i++)
            result = String.format("%s,%s",result,values.get(i));
        return result;
    }
    public RequestValueNotFoundException(@NotNull String... values){
        this.values = new ArrayList<>();
        for(String value:values)
            this.values.add(value);
    }
}

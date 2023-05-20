package com.cimss.project.im;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ButtonList {
    private String title;
    private String text;
    //key:button display, value:button send
    private Map<String,String> buttons;

    public static ButtonList CreateButtonList( String title,String text,Map<String,String> buttons){
        return new ButtonList(title,text,buttons);
    }
/*
    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,String> map = new HashMap<>();
        map.put("OMO","OAO");
        map.put("OWO","QAQ");
        map.put("OAO","OWO");
        map.put("QAQ","OMO");
        ButtonList buttonList = new ButtonList("Testing~~","test!!!!!",map);
        System.out.println(objectMapper.writeValueAsString(buttonList));
    }
*/
}

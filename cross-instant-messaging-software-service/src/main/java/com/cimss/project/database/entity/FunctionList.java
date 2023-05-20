package com.cimss.project.database.entity;

import com.cimss.project.im.ButtonList;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FunctionList implements Serializable{
    private String title;
    private String text;
    private String path;
    @ElementCollection
    @Column(length = 65535)
    private Map<String,TextButton> textButtons;

    @Autowired
    @Hidden
    @Transient
    @JsonIgnore
    private ObjectMapper objectMapper;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TextButton implements Serializable {
        private boolean isEndPoint;
        @Embedded
        private Object data;
    }
    public static FunctionList MappingFromObject(Object o){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(objectMapper.writeValueAsString(o), FunctionList.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    //TODO key<=20 char,all json<=65535 char, textButtons.size()<=4,endPoint data<=300
    public boolean decodeTest(String path){
        this.path = path;
        try{
            textButtons.forEach((key,value)->{
                if(value.isEndPoint) return;
                FunctionList functionList = FunctionList.MappingFromObject(value.data);
                functionList.decodeTest(String.format("%s%s/",path,key));
                value.data = functionList;
            });
        }catch (RuntimeException e){
            return false;
        }
        return true;
    }
    public ButtonList toButtonList(String groupId){
        Map<String,String> buttons = new HashMap<>();
        textButtons.forEach((key,value)->{
            //    /cimss <command type> <group id> <software> <userId> <var>
            if(value.isEndPoint) {
                buttons.put(key,String.format("/cimss notify %s   %s",groupId,(String)value.data));
            }else {
                buttons.put(key,String.format("/cimss toPath %s   %s%s/",groupId,path,key));
            }
        });
        return ButtonList.CreateButtonList(title,text,buttons);
    }
    public FunctionList path(String path){
        if(path==null) return null;
        if(path.equals("/")) return this;
        String key = path.split("/")[1];
        return FunctionList
                .MappingFromObject(textButtons.get(key).data)
                .path(path.substring(key.length()+1));
    }
/*
    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
//        Map<String,TextButton> tb = new HashMap<>();
//        tb.put("Key1",new TextButton(true,"Hi"));
//        tb.put("Key2",new TextButton(true,"Hi!"));
//        tb.put("Key3",new TextButton(true,"!Hi"));
//        FunctionList f = new FunctionList("Title!","Text!",null, tb,null);
//        Map<String,TextButton> tb2 = new HashMap<>();
//        tb2.put("Key4",new TextButton(false,f));
//        tb2.put("Key5",new TextButton(true,"OAO"));
//        FunctionList f2 = new FunctionList("Title!","Text!",null, tb2,null);
        String s = "{\n" +
                "    \"title\": \"Fan Control Service!\",\n" +
                "    \"text\": \"Control your fan!\",\n" +
                "    \"textButtons\": {\n" +
                "      \"Power Switch\": {\n" +
                "        \"data\": {\n" +
                "          \"title\": \"Power Switch!\",\n" +
                "          \"text\": \"Turn on or turn off!\",\n" +
                "          \"textButtons\": {\n" +
                "            \"ON\": {\n" +
                "              \"data\": \"ON\",\n" +
                "              \"endPoint\": true\n" +
                "            },\n" +
                "            \"OFF\": {\n" +
                "              \"data\": \"OFF\",\n" +
                "              \"endPoint\": true\n" +
                "            }\n" +
                "          }\n" +
                "        },\n" +
                "        \"endPoint\": false\n" +
                "      },\n" +
                "      \"Get Data\": {\n" +
                "        \"data\": {\n" +
                "          \"title\": \"Get Data!\",\n" +
                "          \"text\": \"Get temperature and humidity or the state of fan!\",\n" +
                "          \"textButtons\": {\n" +
                "            \"temperature and humidity\": {\n" +
                "              \"data\": \"TH\",\n" +
                "              \"endPoint\": true\n" +
                "            },\n" +
                "            \"fan state\": {\n" +
                "              \"data\": \"STATE\",\n" +
                "              \"endPoint\": true\n" +
                "            }\n" +
                "          }\n" +
                "        },\n" +
                "        \"endPoint\": false\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "  ";
        FunctionList f2 =objectMapper.readValue(s, FunctionList.class);
//                System.out.println();
//        System.out.println(f.decodeTest("/"));

        System.out.println(f2.decodeTest("/"));
        System.out.println(objectMapper.writeValueAsString(f2));
    }

 */

}

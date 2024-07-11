package com.example.smspr.controller;

import com.example.smspr.domain.Tbboard;
import com.example.smspr.repository.TbboardRepository;
import com.example.smspr.repository.TbpostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/tbboard")
@RestController
public class TbboardRestController {
    List<Map<String, Object>> tbboardList = new ArrayList<>();

    //instance 안만들고 그냥 사용할 수 있음 , tbboardrepository의 기능을 쓴다
    TbboardRepository tbboardRepository;
    public TbboardRestController(
            TbpostRepository tbboardRepository
    ) {
        this.tbboardRepository = tbboardRepository;
    }


    @GetMapping("/create")
    public Map<String, Object> create(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam String writer
    ) {
        Map<String, Object> returnVal = new HashMap<>();
        Map<String, Object> tbboard = new HashMap<>();
        tbboard.put("order", tbboardList.size() + 1);
        tbboard.put("title", title);
        tbboard.put("content", content);
        tbboard.put("writer", writer);

        //tbboardList.add(tbboard);
        Tbboard tbboard1 = Tbboard.of(title, content, writer);
        tbboardRepository.save(tbboard1);

        returnVal.put("resultCode", 200);
        returnVal.put("order", tbboardList.size());
        return returnVal;
    }

    @GetMapping("/list")
    public Map<String, Object> list(){
        Map<String, Object> returnVal = new HashMap<>();

        returnVal.put("resultCode", 200);
        //매번 지우지 말고 DB에 있는 정보 다 가지고 와줘
        returnVal.put("data", tbboardRepository.findAll());
        //returnVal.put("data", tbboardList);
        return returnVal;
    }

    @GetMapping("/detail/{id}")
    public Map<String, Object> detail(@PathVariable String id){
        Map<String, Object> returnVal = new HashMap<>();
        int resultCode = 0;
        returnVal.put("resultCode", resultCode);

        Tbboard tbboard = tbboardRepository.findById(id).orElseThrow(() -> new RuntimeException(""));

        returnVal.put("data", tbboard);
        return returnVal;
    }

    @GetMapping("/update")
    public Map<String, Object> update(@RequestParam Map<String, Object> params){
        Map<String, Object> returnVal = new HashMap<>();
        String id = (String) params.get("id");
        int resultCode = 0;

        Tbboard tbboard = tbboardRepository.findById(id).orElseThrow(() -> new RuntimeException(""));
        if(params.get("title") != null && !"".equals(params.get("title"))){
            tbboard.setTitle(params.get("title")+ "");
            resultCode = 200;
        }
        if(params.get("content") != null && !"".equals(params.get("cpntent"))){
            tbboard.setContent(params.get("content")+ "");
            resultCode = 200;
        }
        if(params.get("writer") != null && !"".equals(params.get("writer"))){
            tbboard.setWriter(params.get("writer")+ "");
            resultCode = 200;
        }
        returnVal.put("resultCode", resultCode);
        //returnVal.put("data", tbboardList);
        return returnVal;
    }

    @GetMapping("/delete")
    public Map<String, Object> delete(@RequestParam Map<String, Object> params){
        Map<String, Object> returnVal = new HashMap<>();
        String id = (String) params.get("id");
        int resultCode = 200;

        Tbboard tbboard = tbboardRepository.findById(id).orElseThrow(() -> new RuntimeException(""));
        tbboardRepository.delete(tbboard);

        returnVal.put("resultCode", resultCode);
        //returnVal.put("data", tbboardList);
        return returnVal;
    }
}
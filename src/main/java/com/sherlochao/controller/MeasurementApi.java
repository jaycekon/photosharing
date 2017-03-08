package com.sherlochao.controller;

import com.sherlochao.model.Measurement;
import com.sherlochao.service.MeasurementService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */
@Slf4j
@Controller
@RequestMapping("/measurementapi")
public class MeasurementApi {

    @Resource
    private MeasurementService measurementService;

    @RequestMapping("/addMeasurement")
    @ResponseBody
    public Object addMeasurement(Measurement measurement){
        measurementService.saveMeasurement(measurement);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status","success");
        jsonObject.put("msg","添加试题成功");
        return jsonObject.toString();
    }

    @RequestMapping("/listMeasurement")
    @ResponseBody
    public Object listMeasurement(){
        JSONObject jsonObject = new JSONObject();
        List<Measurement> list = measurementService.listMeasurement();
        jsonObject.put("topics",list);
        return jsonObject;
    }

    



}

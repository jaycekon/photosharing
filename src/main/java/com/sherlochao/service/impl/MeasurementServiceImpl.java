package com.sherlochao.service.impl;

import com.sherlochao.dao.MeasurementDao;
import com.sherlochao.model.Measurement;
import com.sherlochao.service.MeasurementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */
@Transactional
@Service("measurementService")
public class MeasurementServiceImpl implements MeasurementService {

    @Resource
    private MeasurementDao measurementDao;

    @Override
    public void saveMeasurement(Measurement measurement) {
        measurementDao.saveMeasurement(measurement);
    }

    @Override
    public void updateMeasurement(Measurement measurement) {
        measurementDao.updateMeasurement(measurement);
    }

    @Override
    public Measurement findByMeasurementId(int measurementId) {
        return measurementDao.findByMeasurementId(measurementId);
    }

    @Override
    public List<Measurement> listMeasurement() {
        List<Measurement> list = measurementDao.listMeasurement();
        if(list.isEmpty()){
            return new ArrayList<>();
        }
        return list;
    }
}

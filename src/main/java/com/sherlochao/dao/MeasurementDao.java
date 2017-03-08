package com.sherlochao.dao;

import com.sherlochao.model.Measurement;

import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */
public interface MeasurementDao {
    void saveMeasurement(Measurement measurement);

    void updateMeasurement(Measurement measurement);

    Measurement findByMeasurementId(int measurementId);

    List<Measurement> listMeasurement();

    List<Measurement> findMeasurement(int articleId);
}

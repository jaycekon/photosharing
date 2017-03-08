package com.sherlochao.dao.impl;

import com.sherlochao.dao.MeasurementDao;
import com.sherlochao.model.Measurement;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */
@Repository("measurementDao")
public class MeasurementDaoImpl extends GenericDaoImpl<Measurement>
        implements MeasurementDao {



    @Override
    public void saveMeasurement(Measurement measurement) {
        super.getSession().save(measurement);

    }

    @Override
    public void updateMeasurement(Measurement measurement) {
        super.getSession().update(measurement);
    }

    @Override
    public Measurement findByMeasurementId(int measurementId) {
        return super.get(String.valueOf(measurementId));
    }

    @Override
    public List<Measurement> listMeasurement() {
        String hql = "from Measurement";
        Query query = super.getSession().createQuery(hql);
        return query.list();
    }
}

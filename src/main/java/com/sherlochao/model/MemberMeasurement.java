package com.sherlochao.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/8.
 */
@Data
@ToString
@Entity
@Table(name = "member_measurement")
public class MemberMeasurement implements Serializable{

    private static final long serialVersionUID = 5887251578703661368L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_measurement_id")
    private Integer memberMeasurementId;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "measurement_id")
    private String measurementId;

    @Column(name = "answer")
    private String answer;

    @Column(name = "truth")
    private String truth;
}

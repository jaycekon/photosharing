package com.sherlochao.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/8.
 */
@ToString
@Data
@Entity
@Table(name = "measurement")
public class Measurement implements Serializable {
    private static final long serialVersionUID = 3204986643443894032L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "measurement_id")
    private Integer measurementId;// id

    @Column(name = "subject")
    private String subject;

    @Column(name = "optionA")
    private String optionA;

    @Column(name = "optionB")
    private String optionB;

    @Column(name = "optionC")
    private String optionC;

    @Column(name = "optionD")
    private String optionD;

    @Column(name = "answer")
    private String answer;
}

package com.example.project1.entity;

import javax.persistence.*;

@Entity
public class TransferHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long formAccNumber;

    private Long toAccNumber;

    private double sum;

    public TransferHistory(Long formAccNumber, Long toAccNumber, double sum) {
        this.formAccNumber = formAccNumber;
        this.toAccNumber = toAccNumber;
        this.sum = sum;
    }

    public TransferHistory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFormAccNumber() {
        return formAccNumber;
    }

    public void setFormAccNumber(Long formAccNumber) {
        this.formAccNumber = formAccNumber;
    }

    public Long getToAccNumber() {
        return toAccNumber;
    }

    public void setToAccNumber(Long toAccNumber) {
        this.toAccNumber = toAccNumber;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }
}

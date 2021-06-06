package com.atc.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "measures", schema = "shapp", catalog = "")
public class MeasureEntity {
    private int id;
    private LocalDateTime creationDateTime;
    private BigDecimal measuredValue;
    private String note;
    private UserEntity usersByUserId;
    private MeasurandEntity measurandsByMeasurandId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    @Basic
    @Column(name = "creation_date_time", nullable = false)
    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    @Basic
    @Column(name = "measured_value", nullable = false, precision = 2)
    public BigDecimal getMeasuredValue() {
        return measuredValue;
    }

    public void setMeasuredValue(BigDecimal measuredValue) {
        this.measuredValue = measuredValue;
    }

    @Basic
    @Column(name = "note", nullable = true, length = -1)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasureEntity that = (MeasureEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(creationDateTime, that.creationDateTime) && Objects.equals(measuredValue, that.measuredValue) && Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creationDateTime, measuredValue, note);
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public UserEntity getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(UserEntity usersByUserId) {
        this.usersByUserId = usersByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "measurand_id", referencedColumnName = "id")
    public MeasurandEntity getMeasurandsByMeasurandId() {
        return measurandsByMeasurandId;
    }

    public void setMeasurandsByMeasurandId(MeasurandEntity measurandsByMeasurandId) {
        this.measurandsByMeasurandId = measurandsByMeasurandId;
    }
}

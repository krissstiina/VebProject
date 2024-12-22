package veb.cinema.demo.models;

import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hall")
public class Hall extends BaseEntity {
    private int capacity;
    private int hallNumber;


    public Hall(){}

    public Hall(int capacity, int hallNumber){
        this.capacity = capacity;
        this.hallNumber = hallNumber;
    }

    @Column(name = "capacity")
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void decreaseCapacity() {
        if (capacity > 0) {
            capacity--;
        } else {
            throw new IllegalStateException("Зал уже заполнен");
        }
    }


    @Column(name = "hallNumber")
    public int getHallNumber() {
        return hallNumber;
    }

    public void setHallNumber(int hallNumber) {
        this.hallNumber = hallNumber;
    }
}




package veb.cinema.demo.dto;

import java.io.Serializable;

public class HallDto implements Serializable {
    private int id;
    private int capacity;
    private int hallNumber;

    protected HallDto(){}

    public HallDto(int id, int capacity, int hallNumber){
        this.id = id;
        this.capacity = capacity;
        this.hallNumber = hallNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getHallNumber() {
        return hallNumber;
    }

    public void setHallNumber(int hallNumber) {
        this.hallNumber = hallNumber;
    }
}

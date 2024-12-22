package veb.cinema.demo.dto;

public class HallCreateDto {
    private int capacity;
    private int hallNumber;

    protected HallCreateDto(){}

    public HallCreateDto(int id, int capacity, int hallNumber){
        this.capacity = capacity;
        this.hallNumber = hallNumber;
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

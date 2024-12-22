package veb.cinema.demo.dto;

public class FilmCreateDto {
    private String name;
    private String genre;
    private String producer;
    private int yearOfPublish;
    private double averageRating;

    public FilmCreateDto(String name, String genre, String producer, int yearOfPublish, double averageRating){
        this.name = name;
        this.genre = genre;
        this.producer = producer;
        this.yearOfPublish = yearOfPublish;
        this.averageRating = averageRating;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    protected FilmCreateDto() {}

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getProducer() {
        return producer;
    }

    public int getYearOfPublish() {
        return yearOfPublish;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public void setYearOfPublish(int yearOfPublish) {
        this.yearOfPublish = yearOfPublish;
    }

    public void setName(String name) {
        this.name = name;
    }

}

package veb.cinema.demo.dto;

import java.io.Serializable;

public class UserDto implements Serializable {
    private int id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String middleName;
    private String phoneNumber;
    private int age;
    private String confirmPassword;

    public UserDto(String email, String password, String name, String surname, String middleName, String phoneNumber, int age ){
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.middleName = middleName;
        this.phoneNumber = phoneNumber;
        this.age = age;
    }

    public UserDto() {

    }

    public UserDto(int id, String name, String email, String surname, String middleName, String phoneNumber, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.surname = surname;
        this.middleName = middleName;
        this.phoneNumber = phoneNumber;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}

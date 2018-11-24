package model;

import java.util.Date;

public class Doctor {

    private int id;
    private String email;
    private String password;
    private HealthcareUnit healthcareUnit;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String pesel;
    private String phoneNumber;

    public Doctor(int id, String email, String password, HealthcareUnit healthcareUnit, String firstName,
                  String lastName, Date birthDate, String pesel, String phoneNumber) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.healthcareUnit = healthcareUnit;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.pesel = pesel;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HealthcareUnit getHealthcareUnit() {
        return healthcareUnit;
    }

    public void setHealthcareUnit(HealthcareUnit healthcareUnit) {
        this.healthcareUnit = healthcareUnit;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

package app.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name="email")
    private String email;
    @Column(name="password")
    private String password;
    @OneToOne
    private HealthcareUnit healthcareUnit;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name="birth_date")
    private Date birthDate;
    @Column(name="pesel")
    private String pesel;
    @Column(name="phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "doctor")
    private List<WorkingHours> workingHours;

    public Doctor() {

    }

    public Doctor(int id, String email, String password, HealthcareUnit healthcareUnit, String firstName,
                  String lastName, Date birthDate, String pesel, String phoneNumber,
                  List<WorkingHours> workingHours) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.healthcareUnit = healthcareUnit;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.pesel = pesel;
        this.phoneNumber = phoneNumber;
        this.workingHours = workingHours;
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

    public List<WorkingHours> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(List<WorkingHours> workingHours) {
        this.workingHours = workingHours;
    }
}

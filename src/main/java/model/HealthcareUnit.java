package model;

public class HealthcareUnit {

    private int id;
    private String unitName;
    private String address;
    private String postalCode;
    private String city;

    public HealthcareUnit(int id, String unitName, String address, String postalCode, String city) {
        this.id = id;
        this.unitName = unitName;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}

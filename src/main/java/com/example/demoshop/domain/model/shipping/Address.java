package main.java.com.example.demoshop.java.com.example.demoshop.domain.model.shipping;


import java.util.Objects;

public class Address {
    private final String street;
    private final String city;
    private final String postalCode;
    private final String country;

    public Address(String street, String city, String postalCode, String country) {
        this.street = Objects.requireNonNull(street);
        this.city = Objects.requireNonNull(city);
        this.postalCode = Objects.requireNonNull(postalCode);
        this.country = Objects.requireNonNull(country);
    }

    public String street() { return street; }
    public String city() { return city; }
    public String postalCode() { return postalCode; }
    public String country() { return country; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address that)) return false;
        return street.equals(that.street) &&
                city.equals(that.city) &&
                postalCode.equals(that.postalCode) &&
                country.equals(that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, city, postalCode, country);
    }
}

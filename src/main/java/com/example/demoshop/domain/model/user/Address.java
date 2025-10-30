package com.example.demoshop.domain.model.user;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
@Access(AccessType.FIELD)
public class Address {

    private final String street;
    private final String city;
    private final String country;
    private final String postalCode;

    protected Address() {
        this.street = null;
        this.city = null;
        this.country = null;
        this.postalCode = null;
    }

    public Address(String street, String city, String country, String postalCode) {
        this.street = Objects.requireNonNull(street, "Street cannot be null");
        this.city = Objects.requireNonNull(city, "City cannot be null");
        this.country = Objects.requireNonNull(country, "Country cannot be null");
        this.postalCode = Objects.requireNonNull(postalCode, "Postal code cannot be null");
    }

    public String getStreet() { return street; }
    public String getCity() { return city; }
    public String getCountry() { return country; }
    public String getPostalCode() { return postalCode; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return street.equals(address.street)
                && city.equals(address.city)
                && country.equals(address.country)
                && postalCode.equals(address.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, city, country, postalCode);
    }
}

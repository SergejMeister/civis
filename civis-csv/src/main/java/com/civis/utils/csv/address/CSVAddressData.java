package com.civis.utils.csv.address;


/**
 * Model to represents csv-address data.
 */
public class CSVAddressData {

    private String city;

    private String zip;

    private String region;


    public CSVAddressData(String city, String zip, String region) {
        setCity(city);
        setZip(zip);
        setRegion(region);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CSVAddressData)) {
            return false;
        }

        CSVAddressData that = (CSVAddressData) o;

        return !(getCity() != null ? !getCity().equals(that.getCity()) : that.getCity() != null) &&
                !(getZip() != null ? !getZip().equals(that.getZip()) : that.getZip() != null) &&
                !(getRegion() != null ? !getRegion().equals(that.getRegion()) : that.getRegion() != null);

    }

    @Override
    public int hashCode() {
        int result = getCity() != null ? getCity().hashCode() : 0;
        result = 31 * result + (getZip() != null ? getZip().hashCode() : 0);
        result = 31 * result + (getRegion() != null ? getRegion().hashCode() : 0);
        return result;
    }
}

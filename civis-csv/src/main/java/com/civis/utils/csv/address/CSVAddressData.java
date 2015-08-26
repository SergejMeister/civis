/*
 * Copyright 2015 Sergej Meister
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

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

package com.civis.utils.opennlp.models.address;

import com.civis.utils.opennlp.models.BaseSpan;

/**
 * This is a span to represents address data
 */
public class AddressSpan extends BaseSpan {

    //type of start-tag address. <START:address><END>
    public static final String PREFIX_TYPE_ADDRESS = "address";


    private String street;
    private String streetNumber;
    private String city;
    private String zip;
    private String country;


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AddressSpan)) {
            return false;
        }

        AddressSpan that = (AddressSpan) o;

        if (getStreet() != null ? !getStreet().equals(that.getStreet()) : that.getStreet() != null) {
            return false;
        }
        if (getStreetNumber() != null ? !getStreetNumber().equals(that.getStreetNumber()) :
                that.getStreetNumber() != null) {
            return false;
        }
        if (getCity() != null ? !getCity().equals(that.getCity()) : that.getCity() != null) {
            return false;
        }
        return !(getZip() != null ? !getZip().equals(that.getZip()) : that.getZip() != null);

    }

    @Override
    public int hashCode() {
        int result = getStreet() != null ? getStreet().hashCode() : 0;
        result = 31 * result + (getStreetNumber() != null ? getStreetNumber().hashCode() : 0);
        result = 31 * result + (getCity() != null ? getCity().hashCode() : 0);
        result = 31 * result + (getZip() != null ? getZip().hashCode() : 0);
        return result;
    }
}

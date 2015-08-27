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

package com.civis.utils.opennlp.models.contactperson;


import com.civis.utils.opennlp.models.BaseSpan;
import org.apache.commons.lang3.StringUtils;

/**
 * Contact person span.
 */
public class ContactPersonSpan extends BaseSpan {

    public static final String PREFIX_TYPE_PERSON = "person";
    public static final String PREFIX_TYPE_SALUTATION = "salutation";

    private String firstName;
    private String secondName;
    private String sex;


    public ContactPersonSpan(String firstName, String secondName, String sex) {
        setFirstName(firstName);
        setSecondName(secondName);
        setSex(sex);
    }

    public ContactPersonSpan(Double probability, String sex) {
        super(probability);
        setSex(sex);
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContactPersonSpan)) {
            return false;
        }

        ContactPersonSpan that = (ContactPersonSpan) o;

        if (getFirstName() != null ? !getFirstName().equals(that.getFirstName()) : that.getFirstName() != null) {
            return false;
        }
        if (getSecondName() != null ? !getSecondName().equals(that.getSecondName()) : that.getSecondName() != null) {
            return false;
        }
        return !(getSex() != null ? !getSex().equals(that.getSex()) : that.getSex() != null);

    }

    @Override
    public int hashCode() {
        int result = getFirstName() != null ? getFirstName().hashCode() : 0;
        result = 31 * result + (getSecondName() != null ? getSecondName().hashCode() : 0);
        result = 31 * result + (getSex() != null ? getSex().hashCode() : 0);
        return result;
    }

    /**
     * Return firstName secondName.
     * <p/>
     * firstName is null, return only secondName.
     * secondName is null, return only firstName.
     * firstName and secondName are null, return empty string.
     */
    public String getFullName() {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(firstName)) {
            sb.append(firstName);
        }
        if (StringUtils.isNotBlank(secondName)) {
            sb.append(" ").append(secondName);
        }

        return sb.toString();
    }

    /**
     * Remove whitespace between first and second name.
     * <p/>
     * Alexander Rom return AlexanderRom
     */
    public String getFullNameWithoutWhiteSpace() {
        return StringUtils.deleteWhitespace(getFullName());
    }

    /**
     * Build full name and check if full name contains given value.
     */
    public boolean contains(String value) {
        return !StringUtils.isBlank(value) && getFullNameWithoutWhiteSpace().contains(value);

    }
}

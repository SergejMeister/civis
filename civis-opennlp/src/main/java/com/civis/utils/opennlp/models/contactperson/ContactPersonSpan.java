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

/**
 * Contact person span.
 */
public class ContactPersonSpan extends BaseSpan {

    public static final String PREFIX_TYPE_PERSON = "person";
    public static final String PREFIX_TYPE_SALUTATION = "salutation";

    private String firstName;
    private String secondName;
    private String sex;


    public ContactPersonSpan() {
    }

    public ContactPersonSpan(String firstName, String secondName) {
        setFirstName(firstName);
        setSecondName(secondName);
    }

    public ContactPersonSpan(String firstName, String secondName,String sex) {
        setFirstName(firstName);
        setSecondName(secondName);
        setSex(sex);
    }

    public ContactPersonSpan(String firstName, String secondName, Double probability) {
        super(probability);
        setFirstName(firstName);
        setSecondName(secondName);
    }

    public ContactPersonSpan(String firstName, String secondName, String sex, Double probability) {
        super(probability);
        setFirstName(firstName);
        setSecondName(secondName);
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
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ContactPersonSpan)) {
            return false;
        }

        ContactPersonSpan that = (ContactPersonSpan) other;

        if (getFirstName() == null) {
            if (that.getFirstName() != null) {
                return false;
            }
        } else {
            if (!getFirstName().equals(that.getFirstName())) {
                return false;
            }
        }

        if (getSecondName() == null) {
            if (that.getSecondName() != null) {
                return false;
            }
        } else {
            if (!getSecondName().equals(that.getSecondName())) {
                return false;
            }
        }

        if (getSex() == null) {
            if (that.getSex() != null) {
                return false;
            }
        }

        return getSex().equals(that.getSex());

    }

    @Override
    public int hashCode() {
        int result = -1;
        if (this.sex != null) {
            result = result + getSex().hashCode();
        }
        if (this.firstName != null) {
            result = 31 * getFirstName().hashCode();
        }
        if (this.secondName != null) {
            result = 31 * result + getSecondName().hashCode();
        }

        return result;
    }
}

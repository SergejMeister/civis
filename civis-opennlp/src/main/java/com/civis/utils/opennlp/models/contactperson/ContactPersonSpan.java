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


/**
 * Contact person span.
 */
public class ContactPersonSpan {

    public static final String PREFIX_TYPE_PERSON = "person";
    public static final String PREFIX_TYPE_SALUTATION = "salutation";

    private String firstName;
    private String secondName;
    private String sex;
    private Double probability;


    public ContactPersonSpan() {
    }

    public ContactPersonSpan(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public ContactPersonSpan(String firstName, String secondName, Double probability) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.probability = probability;
    }

    public ContactPersonSpan(String firstName, String secondName, String sex, Double probability) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.sex = sex;
        this.probability = probability;
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

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }
}

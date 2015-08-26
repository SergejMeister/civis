package com.civis.utils.csv.names;

/**
 * Model to represents csv-name data.
 */
public class CSVNameData {

    private String name;

    private String gender;


    public CSVNameData(String name, String gender) {
        setName(name);
        setGender(gender);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns name with first upper case letter.
     */
    public String getCapitalizeName() {
        if (name != null && name.length() != 0) {
            char[] chars = name.toCharArray();
            chars[0] = Character.toUpperCase(chars[0]);
            return new String(chars);
        } else {
            return name;
        }
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CSVNameData)) {
            return false;
        }

        CSVNameData that = (CSVNameData) o;

        return !(getName() != null ? !getName().equals(that.getName()) : that.getName() != null) &&
                !(getGender() != null ? !getGender().equals(that.getGender()) : that.getGender() != null);

    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getGender() != null ? getGender().hashCode() : 0);
        return result;
    }
}

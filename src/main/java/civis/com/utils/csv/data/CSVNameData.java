package civis.com.utils.csv.data;

/**
 * Model CsvName.
 */
public class CSVNameData {

    private String name;

    private String gender;


    public CSVNameData(String name, String gender) {
        this.name = name;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        return !(gender != null ? !gender.equals(that.gender) : that.gender != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        return result;
    }
}

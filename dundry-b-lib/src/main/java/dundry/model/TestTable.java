package dundry.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "test_table")
public class TestTable {
    private Long    test_table_id;
    private Integer test_int;
    private Float   test_float;
    private String  test_string;

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    public Long getTest_table_id() {
        return test_table_id;
    }
    /*
     * setter for test_table_id is private but is used by hibernate
     */
    @SuppressWarnings("unused")
    private void setTest_table_id(Long test_table_id) {
        this.test_table_id = test_table_id;
    }
    
    public Integer getTest_int() {
        return test_int;
    }
    public void setTest_int(Integer test_int) {
        this.test_int = test_int;
    }
    
    public Float getTest_float() {
        return test_float;
    }
    public void setTest_float(Float test_float) {
        this.test_float = test_float;
    }
    
    public String getTest_string() {
        return test_string;
    }
    public void setTest_string(String test_string) {
        this.test_string = test_string;
    }
}

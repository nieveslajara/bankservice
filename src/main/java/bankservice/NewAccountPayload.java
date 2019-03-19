package bankservice;

import lombok.Data;

// Here Lombok establishes annotation param. internally all getters and setters are generated
@Data
public class NewAccountPayload implements Validable {
    private Integer number;
    private String name;
    private Boolean creditcard;
    private Boolean synthetic;
    private Double balance;

    public boolean isValid() {
        return !number.equals(null) && !name.isEmpty()
                && !creditcard.equals(null) && !synthetic.equals(null) && !balance.equals(null);
    }
}
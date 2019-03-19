package bankservice;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ModelTest {

    @Before
    public void initModel(){
        Model mymodel = new Model();
        assertTrue(mymodel.getAllAccounts().isEmpty());
    }

    @Test
    public void createAccount() {
        Model mymodel = new Model();
        assertEquals(1, mymodel.createAccount(5, "My account", false, true, 2.0));
        assertEquals(2, mymodel.createAccount(5, "My account", false, true, 2.0));
        assertFalse(4 == mymodel.createAccount(5, "My account", false, true, 2.0));
        assertTrue( 4 == mymodel.createAccount(5, "My account", false, true, 2.0));
    }

    @Test
    public void getAllAccounts() {
        Model mymodel = new Model();
        mymodel.createAccount(1357756, "Personal account", false, false, 1202.14);
        mymodel.createAccount(2446987, "Business account", false, false, 34057.00);
        mymodel.createAccount(9981644, "Credit card", true, false, -10057.00);
        mymodel.createAccount(0, "Expense claims", false, true, 0.0);
        assertEquals(4, mymodel.getAllAccounts().size());
    }

    // default account tests
    @Test
    public void getDefaultOneElementNullNegativeBalance() {
        Model mymodel = new Model();
        assertEquals(null, mymodel.getDefault());
        mymodel.createAccount(1357756, "Personal account", false, false, -1202.14);
        assertEquals(null, mymodel.getDefault());
    }

    @Test
    public void getDefaultOneElementNullNegativeSynthetic() {
        Model mymodel = new Model();
        assertEquals(null, mymodel.getDefault());
        mymodel.createAccount(1357756, "Personal account", false, true, 1202.14);
        assertEquals(null, mymodel.getDefault());
    }

    @Test
    public void getDefaultOneElementRegular() {
        Model mymodel = new Model();
        mymodel.createAccount(1357756, "Personal account", false, false, 1202.14);
        assertFalse(mymodel.getDefault().equals(null));
        assertEquals((Double) 1202.14,  mymodel.getDefault().getBalance());
    }

    @Test
    public void getDefaultNull() {
        Model mymodel = new Model();
        mymodel.createAccount(1357756, "Personal account", false, false, -1202.14);
        mymodel.createAccount(2446987, "Business account", false, true, 34057.00);
        mymodel.createAccount(9981644, "Credit card", true, false, -10057.00);
        mymodel.createAccount(0, "Expense claims", false, true, 0.0);
        assertEquals(null, mymodel.getDefault());
    }

    @Test
    public void getDefaultRegular() {
        Model mymodel = new Model();
        mymodel.createAccount(1357756, "Personal account", false, false, 1202.14);
        mymodel.createAccount(2446987, "Business account", false, false, 34057.00);
        mymodel.createAccount(9981644, "Credit card", true, false, -10057.00);
        mymodel.createAccount(0, "Expense claims", false, true, 0.0);
        mymodel.createAccount(777, "Expense claims", false, false, 90000.0);
        mymodel.createAccount(777, "Expense claims", false, false, 87000.0);
        mymodel.createAccount(777, "Expense claims", false, true, 870000.0);

        assertEquals((Double) 90000.0, mymodel.getDefault().getBalance());

    }
}

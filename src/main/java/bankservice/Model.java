package bankservice;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// In a real robust scenario you may want to use a DB, for this example we just store the accounts in internal memory
public class Model {
    private int nextId = 1; // start unique identifier in 1
    private Map accounts = new HashMap<>();

    @Data
    class Account {
        private int id;
        private String number;
        private String name;
        private Boolean creditcard;
        private Boolean synthetic;
        private Double balance;


    }

    // if < 0 then it is stored as empty String
    public int createAccount(Integer number, String name, Boolean creditcard, Boolean synthetic, Double balance) {
        int id = nextId++;
        Account account = new Account();
        account.setId(id);

        if (number!=null && number>0) {
            account.setNumber(Integer.toString(number));
        } else {
            account.setNumber("");
        }
        account.setName(name);
        account.setCreditcard(creditcard);
        account.setSynthetic(synthetic);
        account.setBalance(balance);
        accounts.put(id, account);
        return id;
    }

    public List getAllAccounts() {
        return (List) accounts.keySet().stream().sorted().map((id) -> accounts.get(id)).collect(Collectors.toList());
    }

    public Account getDefault(){
        /*
         * If there is a bank account with a positive balance that is at least twice as high as all other bank accounts, return the `id` of that account.
         * If there is only a single bank account, return the id of that account.
         * Synthetic bank accounts can never be chosen as default account.
         * Accounts with negative balance can never be chosen as default accounts.
         * If no account satisfied the above, return `null`.
         */
        Account defaultAccount = null;

        try{
            List currentAccounts = getAllAccounts();
            if( currentAccounts.size() > 0 ){
                if( currentAccounts.size() == 1 ){
                    Account element = (Account) accounts.get(1);
                    if( element.getBalance() > 0.0 && !element.getSynthetic() ){
                        defaultAccount = element;
                    }
                }
                else{
                    Account first ;
                    // get the first positive balance account and synthetic=false
                    for(int i=0; i<currentAccounts.size(); i++){
                        first = (Account) currentAccounts.get(i);
                        if( first.getBalance() > 0.0 && !first.getSynthetic() ){
                            Account aux ;
                            for(int j=i+1; j<currentAccounts.size(); j++){
                                aux = (Account) currentAccounts.get(j);
                                if( aux.getBalance() > 0.0 && !aux.getSynthetic() && aux.getBalance() > 2*first.getBalance() ){
                                    defaultAccount = aux;
                                    first = aux;
                                }
                            }
                            break; // outer for loop, already first positive and non synthetic account
                            // note here: if not other account found to compare with, null is returned
                        }
                    }
                }
            }
        }
        catch (Exception e){
            throw e;
        }
        finally {
            return defaultAccount;
        }

    }
}
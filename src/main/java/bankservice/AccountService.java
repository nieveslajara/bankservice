package bankservice;

import static spark.Spark.get;
import static spark.Spark.post;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.StringWriter;

public class AccountService {

    private static final int HTTP_BAD_REQUEST = 400;

    public static String dataToJson(Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, data);
            return sw.toString();
        } catch (IOException e) {
            throw new RuntimeException("IOException from a StringWriter?");
        }
    }

    public static void main(String[] args) {

        Model model = new Model();

        // init bankaccounts mock data
        model.createAccount(1357756, "Personal account", false, false, 1202.14);
        model.createAccount(2446987, "Business account", false, false, 34057.00);
        model.createAccount(9981644, "Credit card", true, false, -10057.00);
        model.createAccount(0, "Expense claims", false, true, 0.0);
        //model.createAccount(777, "Expense claims", false, false, 90000.0);
        //model.createAccount(777, "Expense claims", false, false, 87000.0);
        //model.createAccount(777, "Expense claims", false, true, 870000.0);

        get("/", (request, response) -> {
            response.redirect("/bankaccounts");
            return "";
        });

        // insert a post (using HTTP post method)
        post("/bankaccounts", (request, response) -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                NewAccountPayload creation = mapper.readValue(request.body(), NewAccountPayload.class);
                if (!creation.isValid()) {
                    response.status(HTTP_BAD_REQUEST);
                    return "";
                }
                // Jackson to automatically check if the payload structure is correct. With validation method.
                // Note, number is considered any integer in the payload, but stored as a String in the Model, according to the
                // javascript examples in the problem description
                int id = model.createAccount(creation.getNumber(), creation.getName(), creation.getCreditcard(),
                        creation.getSynthetic(), creation.getBalance());
                response.status(200);
                response.type("application/json");
                return id;
            } catch (JsonParseException jpe) {
                response.status(HTTP_BAD_REQUEST);
                return "";
            }
        });

        // get all post (using HTTP get method)
        get("/bankaccounts", (request, response) -> {
            response.status(200);
            response.type("application/json");
            return dataToJson(model.getAllAccounts());
        });

        // get all post (using HTTP get method)
        get("/bankaccounts/default", (request, response) -> {
            response.status(200);
            response.type("application/json");
            return dataToJson(model.getDefault());
        });
    }
}

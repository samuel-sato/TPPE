package unb.tppe.api;


import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class SellerTest {

    @Test( )
    void createSeller(){
        Map<String, Object> dto = new HashMap<>();
        dto.put("name", "João");
        dto.put("email", "joao@email.com");
        dto.put("birthdate", LocalDate.of(1990, 5, 20).toString());
        dto.put("baseSalary", 5000.0);
        dto.put("numberHours", 160.0);

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/sellers")
                .then()
                .statusCode(201);
    }
}

package unb.tppe.api;


import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import unb.tppe.aplication.dto.SellerDTO;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class SellerTest {

    @Test( )
    void listAll(){
        given()
                .when()
                .get("/sellers")
                .then()
                .statusCode(200)
                .body("", not(empty()));

    }

    @ParameterizedTest()
    @ValueSource(longs = {2L, 3L})
    void listById(Long id){

        given()
                .when()
                .get("/sellers" + "/" + id)
                .then()
                .statusCode(200)
                .body("id", equalTo(id.intValue()));
    }


    // Variável para armazenar o ID do vendedor criado para usar em outros testes
    // Atenção: Isso cria dependência entre testes. Para testes totalmente independentes,
    // cada teste de update/delete deveria criar seu próprio recurso.
    private static Long createdSellerId = 1L;

    // Seu SellerDTO (certifique-se de que está acessível)
    // Para este exemplo, vou instanciar diretamente.
    // Se o seu SellerDTO for @ApplicationScoped, você não precisa injetá-lo aqui para REST Assured,
    // apenas crie uma instância normal para o corpo da requisição.

    @Test
    @Order(1) // Garante que a inserção seja executada primeiro
    void testCreateSeller() {
        SellerDTO newSeller = new SellerDTO();
        newSeller.setName("João Silva Teste");
        newSeller.setEmail("joao.teste@example.com");
        newSeller.setBirthdate(LocalDate.of(1985, 5, 15));
        newSeller.setBaseSalary(5000.00);
        newSeller.setNumberHours(40.0);

        // Realiza a requisição POST para criar o vendedor
        // e extrai o ID do vendedor criado a partir da resposta
        given()
                .contentType(ContentType.JSON)
                .body(newSeller)
                .when()
                .post("/sellers")
                .then()
                .statusCode(201);

        System.out.println("Vendedor criado com ID: " + createdSellerId);
    }

    @Test
    @Order(2) // Executa após a criação
    void testUpdateSeller() {

        SellerDTO updatedSeller = new SellerDTO();
        updatedSeller.setName("João Silva Atualizado");
        updatedSeller.setEmail("joao.atualizado@example.com");
        updatedSeller.setBirthdate(LocalDate.of(1986, 6, 20));
        updatedSeller.setBaseSalary(5500.00);
        updatedSeller.setNumberHours(42.0);

        given()
                .contentType(ContentType.JSON)
                .body(updatedSeller)
                .pathParam("id", createdSellerId)
                .when()
                .put("/sellers/{id}")
                .then()
                .statusCode(200) // HTTP 200 OK (ou 204 No Content dependendo da sua API)
                .body("person.name", equalTo(updatedSeller.getName()))
                .body("person.email", equalTo(updatedSeller.getEmail()))
                .body("person.birthdate", equalTo(updatedSeller.getBirthdate().toString()))
                .body("baseSalary", equalTo((float) updatedSeller.getBaseSalary()))
                .body("numberHours", equalTo((float) updatedSeller.getNumberHours()))
                .body("id", equalTo(createdSellerId.intValue())); // Verifica se o ID permanece o mesmo

        // Opcional: Verificar com um GET se a atualização foi persistida
        given()
                .pathParam("id", createdSellerId)
                .when()
                .get("/sellers/{id}")
                .then()
                .statusCode(200)
                .body("person.name", equalTo("João Silva Atualizado"));
    }

    @Test
    @Order(3) // Executa após a atualização (e criação)
    void testDeleteSeller() {
        given()
                .pathParam("id", createdSellerId)
                .when()
                .delete("/sellers/{id}")
                .then()
                .statusCode(204); // HTTP 204 No Content é comum para DELETE bem-sucedido

        // Opcional: Verificar com um GET se o recurso foi realmente deletado (deve retornar 404)
        given()
                .pathParam("id", createdSellerId)
                .when()
                .get("/sellers/{id}")
                .then()
                .statusCode(404); // HTTP 404 Not Found
    }

}

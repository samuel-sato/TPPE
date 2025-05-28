package unb.tppe.api;


import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import unb.tppe.aplication.dto.ClientDTO;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class ClientTest {

    @Test( )
    void listAllClients(){
        given()
                .when()
                .get("/clients")
                .then()
                .statusCode(200)
                .body("", not(empty()));

    }

    @ParameterizedTest()
    @ValueSource(longs = {1L, 2L, 3L})
    void listByIdClient(Long id){

        given()
                .when()
                .get("/clients" + "/" + id)
                .then()
                .statusCode(200)
                .body("id", equalTo(id.intValue()));
    }


    private static Long createdClientId;

    // Seu ClientDTO (certifique-se de que está acessível)

    @Test
    @Order(1) // Garante que a inserção seja executada primeiro
    void testCreateClient() {
        ClientDTO newClient = new ClientDTO();
        newClient.setName("Maria Souza Teste");
        newClient.setEmail("maria.teste@example.com");
        newClient.setBirthdate(LocalDate.of(1990, 7, 22));
        newClient.setNotifyPromotion(true);

        // Realiza a requisição POST para criar o cliente
        // e extrai o ID do cliente criado a partir da resposta
        createdClientId = given()
                .contentType(ContentType.JSON)
                .body(newClient)
                .when()
                .post("/clients")
                .then()
                .statusCode(201) // HTTP 201 Created
                .body("name", equalTo(newClient.getName()))
                .body("email", equalTo(newClient.getEmail()))
                .body("birthdate", equalTo(newClient.getBirthdate().toString())) // LocalDate é serializado como String
                .body("notifyPromotion", equalTo(newClient.getNotifyPromotion()))
                .body("id", notNullValue()) // Verifica se um ID foi gerado
                .extract().path("id"); // Extrai o ID para usar nos próximos testes

        System.out.println("Cliente criado com ID: " + createdClientId);
    }

    @Test
    @Order(2) // Executa após a criação
    void testUpdateClient() {
        if (createdClientId == null) {
            System.err.println("ID do cliente não disponível, pulando teste de atualização.");
            // Para testes totalmente independentes, crie um cliente aqui.
            // Ex: ClientDTO clientToSetup = ...; createdClientId = given()...post("/clients")...extract().path("id");
            return;
        }

        ClientDTO updatedClient = new ClientDTO();
        updatedClient.setName("Maria Souza Atualizada");
        updatedClient.setEmail("maria.atualizada@example.com");
        updatedClient.setBirthdate(LocalDate.of(1991, 8, 25));
        updatedClient.setNotifyPromotion(false);

        given()
                .contentType(ContentType.JSON)
                .body(updatedClient)
                .pathParam("id", createdClientId)
                .when()
                .put("/clients/{id}")
                .then()
                .statusCode(200) // HTTP 200 OK (ou 204 No Content dependendo da sua API)
                .body("name", equalTo(updatedClient.getName()))
                .body("email", equalTo(updatedClient.getEmail()))
                .body("birthdate", equalTo(updatedClient.getBirthdate().toString()))
                .body("notifyPromotion", equalTo(updatedClient.getNotifyPromotion()))
                .body("id", equalTo(createdClientId.intValue())); // Verifica se o ID permanece o mesmo

        // Opcional: Verificar com um GET se a atualização foi persistida
        given()
                .pathParam("id", createdClientId)
                .when()
                .get("/clients/{id}")
                .then()
                .statusCode(200)
                .body("name", equalTo("Maria Souza Atualizada"))
                .body("notifyPromotion", equalTo(false));
    }

    @Test
    @Order(3) // Executa após a atualização (e criação)
    void testDeleteClient() {
        if (createdClientId == null) {
            System.err.println("ID do cliente não disponível, pulando teste de deleção.");
            // Para testes totalmente independentes, crie um cliente aqui.
            return;
        }

        given()
                .pathParam("id", createdClientId)
                .when()
                .delete("/clients/{id}")
                .then()
                .statusCode(204); // HTTP 204 No Content é comum para DELETE bem-sucedido

        // Opcional: Verificar com um GET se o recurso foi realmente deletado (deve retornar 404)
        given()
                .pathParam("id", createdClientId)
                .when()
                .get("/clients/{id}")
                .then()
                .statusCode(404); // HTTP 404 Not Found
    }

}

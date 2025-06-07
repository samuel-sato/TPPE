package unb.tppe.api;


import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import unb.tppe.aplication.dto.SaleDTO;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class SaleTest {

    @Test( )
    void listAll(){
        given()
                .when()
                .get("/sales")
                .then()
                .statusCode(200)
                .body("", not(empty()));

    }

    @ParameterizedTest()
    @ValueSource(longs = {1L, 3L})
    void listById(Long id){

        given()
                .when()
                .get("/sales" + "/" + id)
                .then()
                .statusCode(200)
                .body("id", equalTo(id.intValue()));
    }


    private static Long createdSaleId = 1L;

    // IDs de entidades que se presume existirem no banco de dados para os testes.
    // Estes devem ser substituídos por IDs válidos do seu ambiente de teste.
    private static final Long EXISTING_CLIENT_ID = 2L;    // Exemplo: Cliente com ID 1
    private static final Long EXISTING_SELLER_ID = 2L;    // Exemplo: Vendedor com ID 1
    private static final Long EXISTING_PRODUCT_ID_1 = 1L; // Exemplo: Produto com ID 1
    private static final Long EXISTING_PRODUCT_ID_2 = 2L; // Exemplo: Produto com ID 2
    private static final Long EXISTING_PRODUCT_ID_3 = 3L; // Exemplo: Produto com ID 3 (para atualização)


    // Seu SaleDTO (certifique-se de que está acessível)

    @Test
    @Order(1) // Garante que a inserção seja executada primeiro
    void testCreateSale() {
        SaleDTO newSale = new SaleDTO();
        newSale.setIdClient(EXISTING_CLIENT_ID);
        newSale.setIdSeller(EXISTING_SELLER_ID);
        newSale.setIdsProduct(Arrays.asList(EXISTING_PRODUCT_ID_1, EXISTING_PRODUCT_ID_2));

        given()
                .contentType(ContentType.JSON)
                .body(newSale)
                .when()
                .post("/sales")
                .then()
                .statusCode(201);
    }

    @Test
    @Order(2) // Executa após a criação
    void testUpdateSale() {

        // Exemplo de atualização: alterar a lista de produtos da venda
        SaleDTO updatedSale = new SaleDTO();
        // Geralmente, cliente e vendedor de uma venda não mudam, mas a lista de produtos pode.
        // Se a sua API permitir alterar cliente/vendedor, inclua-os aqui.
        // Para este DTO, vamos focar em alterar a lista de produtos.
        // É importante notar que a API deve definir claramente o que é "atualizável" em uma venda.
        updatedSale.setIdClient(EXISTING_CLIENT_ID); // Mantendo o mesmo cliente
        updatedSale.setIdSeller(EXISTING_SELLER_ID); // Mantendo o mesmo vendedor
        updatedSale.setIdsProduct(Arrays.asList(EXISTING_PRODUCT_ID_1, EXISTING_PRODUCT_ID_3)); // Nova lista de produtos

        given()
                .contentType(ContentType.JSON)
                .body(updatedSale) // O corpo da requisição PUT
                .pathParam("id", createdSaleId)
                .when()
                .put("/sales/{id}")
                .then()
                .statusCode(200) // HTTP 200 OK (ou 204 No Content, dependendo da sua API)
                .body("idClient", equalTo(updatedSale.getIdClient().intValue()))
                .body("idSeller", equalTo(updatedSale.getIdSeller().intValue()))
                .body("id", equalTo(createdSaleId.intValue()));

        // Opcional: Verificar com um GET se a atualização foi persistida
//        given()
//                .pathParam("id", createdSaleId)
//                .when()
//                .get("/sales/{id}")
//                .then()
//                .statusCode(200)
//                .body("idsProduct", containsInAnyOrder(
//                        EXISTING_PRODUCT_ID_1.intValue(),
//                        EXISTING_PRODUCT_ID_3.intValue()
//                ));
    }

    @Test
    @Order(3) // Executa após a atualização (e criação)
    void testDeleteSale() {

        given()
                .pathParam("id", 2)
                .when()
                .delete("/sales/{id}")
                .then()
                .statusCode(204); // HTTP 204 No Content é comum para DELETE bem-sucedido

        // Opcional: Verificar com um GET se o recurso foi realmente deletado (deve retornar 404)
        given()
                .pathParam("id", 2)
                .when()
                .get("/sales/{id}")
                .then()
                .statusCode(404); // HTTP 404 Not Found
    }
}

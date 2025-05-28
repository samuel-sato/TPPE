package unb.tppe.api;


import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import unb.tppe.aplication.dto.ProductDTO;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class ProductTest {

    @Test( )
    void listAll(){
        given()
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .body("", not(empty()));

    }

    @ParameterizedTest()
    @ValueSource(longs = {1L, 2L, 3L})
    void listById(Long id){

        given()
                .when()
                .get("/products" + "/" + id)
                .then()
                .statusCode(200)
                .body("id", equalTo(id.intValue()));
    }


    // Variável para armazenar o ID do produto criado
    private static Long createdProductId = 1L;
    // ID de um departamento que se presume existir no banco de dados para os testes.
    // Em um cenário real, este departamento deveria ser criado antes ou como parte do setup.
    private static final Long EXISTING_DEPARTMENT_ID = 1L;
    private static final Long ANOTHER_EXISTING_DEPARTMENT_ID = 2L; // Para testar atualização de departamento


    // Seu ProductDTO (certifique-se de que está acessível)

    @Test
    @Order(1) // Garante que a inserção seja executada primeiro
    void testCreateProduct() {
        ProductDTO newProduct = new ProductDTO();
        newProduct.setName("Notebook Gamer X");
        newProduct.setPrice(7500.99);
        newProduct.setDescription("Notebook de alta performance para jogos e trabalho pesado.");
        newProduct.setIdDepartment(EXISTING_DEPARTMENT_ID); // Assumindo que o departamento com ID 1 existe

        given()
                .contentType(ContentType.JSON)
                .body(newProduct)
                .when()
                .post("/products")
                .then()
                .statusCode(201);
    }

    @Test
    @Order(2) // Executa após a criação
    void testUpdateProduct() {

        ProductDTO updatedProduct = new ProductDTO();
        updatedProduct.setName("Notebook Gamer X (Edição Revisada)");
        updatedProduct.setPrice(7899.50);
        updatedProduct.setDescription("Notebook de alta performance com melhorias na refrigeração.");
        // Vamos assumir que podemos mudar o departamento do produto
        updatedProduct.setIdDepartment(ANOTHER_EXISTING_DEPARTMENT_ID); // Assumindo que o departamento com ID 2 existe

        given()
                .contentType(ContentType.JSON)
                .body(updatedProduct)
                .pathParam("id", createdProductId)
                .when()
                .put("/products/{id}")
                .then()
                .statusCode(200) // HTTP 200 OK (ou 204 No Content, dependendo da sua API)
                .body("name", equalTo(updatedProduct.getName()))
                .body("price", equalTo((float) updatedProduct.getPrice()))
                .body("description", equalTo(updatedProduct.getDescription()))
                .body("idDepartment", equalTo(updatedProduct.getIdDepartment().intValue()))
                .body("id", equalTo(createdProductId.intValue())); // Verifica se o ID permanece o mesmo

        // Opcional: Verificar com um GET se a atualização foi persistida
        given()
                .pathParam("id", createdProductId)
                .when()
                .get("/products/{id}")
                .then()
                .statusCode(200)
                .body("name", equalTo("Notebook Gamer X (Edição Revisada)"))
                .body("idDepartment", equalTo(ANOTHER_EXISTING_DEPARTMENT_ID.intValue()));
    }

    @Test
    @Order(3) // Executa após a atualização (e criação)
    void testDeleteProduct() {

        given()
                .pathParam("id", createdProductId)
                .when()
                .delete("/products/{id}")
                .then()
                .statusCode(204); // HTTP 204 No Content é comum para DELETE bem-sucedido

        // Opcional: Verificar com um GET se o recurso foi realmente deletado (deve retornar 404)
        given()
                .pathParam("id", createdProductId)
                .when()
                .get("/products/{id}")
                .then()
                .statusCode(404); // HTTP 404 Not Found
    }
}

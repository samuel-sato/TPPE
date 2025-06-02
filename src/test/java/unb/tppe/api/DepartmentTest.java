package unb.tppe.api;


import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import unb.tppe.aplication.dto.DepartmentCreateDTO;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class DepartmentTest {

    @Test( )
    void listAll(){
        given()
                .when()
                .get("/departments")
                .then()
                .statusCode(200)
                .body("", not(empty()));

    }

    @ParameterizedTest()
    @ValueSource(longs = {2L, 3L})
    void listById(Long id){

        given()
                .when()
                .get("/departments" + "/" + id)
                .then()
                .statusCode(200)
                .body("id", equalTo(id.intValue()));
    }


    private static Long createdDepartmentId = 1L;

    // Seu DepartmentCreateDTO (certifique-se de que está acessível)

    @Test
    @Order(1) // Garante que a inserção seja executada primeiro
    void testCreateDepartment() {
        DepartmentCreateDTO newDepartment = new DepartmentCreateDTO();
        newDepartment.setName("Tecnologia");
        newDepartment.setDescription("Departamento de desenvolvimento de software e infraestrutura.");

        given()
                .contentType(ContentType.JSON)
                .body(newDepartment)
                .when()
                .post("/departments")
                .then()
                .statusCode(201);
    }

    @Test
    @Order(2) // Executa após a criação
    void testUpdateDepartment() {

        // Usaremos o mesmo DTO para atualização, mas com dados diferentes.
        // Se sua API usar um DTO diferente para atualização, ajuste aqui.
        DepartmentCreateDTO updatedDepartment = new DepartmentCreateDTO();
        updatedDepartment.setName("Tecnologia e Inovação");
        updatedDepartment.setDescription("Departamento focado em desenvolvimento de software, infraestrutura e novas tecnologias.");

        given()
                .contentType(ContentType.JSON)
                .body(updatedDepartment)
                .pathParam("id", 2L)
                .when()
                .put("/departments/{id}")
                .then()
                .statusCode(200) // HTTP 200 OK (ou 204 No Content, dependendo da sua API)
                .body("name", equalTo(updatedDepartment.getName()))
                .body("description", equalTo(updatedDepartment.getDescription()))
                .body("id", equalTo(2)); // Verifica se o ID permanece o mesmo

        // Opcional: Verificar com um GET se a atualização foi persistida
        given()
                .pathParam("id", 2)
                .when()
                .get("/departments/{id}")
                .then()
                .statusCode(200)
                .body("name", equalTo("Tecnologia e Inovação"));
    }

    @Test
    @Order(3) // Executa após a atualização (e criação)
    void testDeleteDepartment() {

        given()
                .pathParam("id", createdDepartmentId)
                .when()
                .delete("/departments/{id}")
                .then()
                .statusCode(204); // HTTP 204 No Content é comum para DELETE bem-sucedido

        // Opcional: Verificar com um GET se o recurso foi realmente deletado (deve retornar 404)
        given()
                .pathParam("id", createdDepartmentId)
                .when()
                .get("/departments/{id}")
                .then()
                .statusCode(404); // HTTP 404 Not Found
    }
}

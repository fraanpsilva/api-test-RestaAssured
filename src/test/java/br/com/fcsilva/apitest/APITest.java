package br.com.fcsilva.apitest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

public class APITest {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080/tasks-backend";
    }

    @Test
    public void deveRetornarTarefas() {
        RestAssured
                .given()
                .when().get("/todo")
                .then().statusCode(200);
    }

    @Test
    public void deveAdicionarTarefaComSucesso() {
        RestAssured
                .given().body("{\"task\": \"Teste via api\",\"dueDate\": \"2022-12-30\"}").contentType(ContentType.JSON)
                .when().post("/todo")
                .then().statusCode(201);
    }

    @Test
    public void naoDeveAdicionarTarefaInvalida() {
        RestAssured
                .given().body("{\"task\": \"Teste via api\",\"dueDate\": \"2010-12-30\"}").contentType(ContentType.JSON)
                .when().post("/todo")
                .then()
                .log().all()
                .statusCode(400)
                .body("message", CoreMatchers.is("Due date must not be in past"));

    }

}

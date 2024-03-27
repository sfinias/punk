package io.sfinias.punk.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.sfinias.punk.entity.Beer;
import io.sfinias.punk.repository.BeerRepository;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.jeasy.random.randomizers.range.IntegerRangeRandomizer;
import org.jeasy.random.randomizers.range.LongRangeRandomizer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class BeerControllerTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void getAllBeers() {

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/beers")
                .then()
                .statusCode(200)
                .body(".", hasSize(0));

        EasyRandom generator = new EasyRandom(
                new EasyRandomParameters()
                        .randomize(Integer.class, new IntegerRangeRandomizer(1, 300))
                        .randomize(Long.class, new LongRangeRandomizer(1L, 300L))
                        .excludeField(FieldPredicates.named("yeast"))
        );

        beerRepository.saveAll(generator.objects(Beer.class, 5).toList());

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/beers")
                .then()
                .statusCode(200)
                .body(".", hasSize(5));
    }

    @Test
    void testGetBeersWithPagination() {

        EasyRandom generator = new EasyRandom(
                new EasyRandomParameters()
                        .randomize(Integer.class, new IntegerRangeRandomizer(1, 300))
                        .randomize(Long.class, new LongRangeRandomizer(1L, 300L))
                        .excludeField(FieldPredicates.named("yeast"))
        );

        beerRepository.saveAll(generator.objects(Beer.class, 13).toList());

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v2/beers?size=5")
                .then()
                .statusCode(200)
                .body("content", hasSize(5))
                .body("pageable.pageNumber", is(0))
                .body("pageable.pageSize", is(5))
                .body("totalPages", is(3))
                .body("first", is(true));

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v2/beers?size=5&page=2")
                .then()
                .statusCode(200)
                .body("content", hasSize(3))
                .body("pageable.pageNumber", is(2))
                .body("pageable.pageSize", is(5))
                .body("totalPages", is(3))
                .body("last", is(true));

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v2/beers?size=5&page=4")
                .then()
                .statusCode(200)
                .body("content", hasSize(0))
                .body("pageable.pageNumber", is(4))
                .body("pageable.pageSize", is(5))
                .body("totalPages", is(3))
                .body("last", is(true));
    }

    @Test
    void testGetRandomBeer() {

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v2/beer/random")
                .then()
                .statusCode(404)
                .body("error", is("There are no beers stored"))
                .body("status", is("NOT_FOUND"));

        EasyRandom generator = new EasyRandom(
                new EasyRandomParameters()
                        .randomize(Integer.class, new IntegerRangeRandomizer(1, 300))
                        .randomize(Long.class, new LongRangeRandomizer(1L, 300L))
                        .excludeField(FieldPredicates.named("yeast"))
        );

        beerRepository.saveAll(generator.objects(Beer.class, 5).toList());

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v2/beer/random")
                .then()
                .statusCode(200);
    }

    @LocalServerPort
    private Integer port;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:13"
    );

    @BeforeAll
    static void beforeAll() {

        postgres.start();
    }

    @AfterAll
    static void afterAll() {

        postgres.stop();
    }

    @BeforeEach
    void beforeEach() {

        RestAssured.baseURI = "http://localhost:" + port;
        beerRepository.deleteAll();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {

        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
}
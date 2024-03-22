package io.sfinias.punk.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

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
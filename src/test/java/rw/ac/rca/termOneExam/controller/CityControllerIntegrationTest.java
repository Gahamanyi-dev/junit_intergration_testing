package rw.ac.rca.termOneExam.controller;


import org.json.JSONException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.utils.APICustomResponse;

import java.util.Objects;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAll_success() throws JSONException {
        String response = this.restTemplate.getForObject("/api/cities/all", String.class);

        JSONAssert.assertEquals("[" +
                "{\"id\":101,\"name\":\"Kigali\",\"weather\":24}," +
                "{\"id\":102,\"name\":\"Musanze\",\"weather\":18}," +
                "{\"id\":103,\"name\":\"Rubavu\",\"weather\":20}," +
                "{\"id\":104,\"name\":\"Nyagatare\",\"weather\":28}]", response, false);
    }

    @Test
    public void getById_success(){
        ResponseEntity<City> response =  this.restTemplate.getForEntity("/api/cities/id/101",City.class);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Kigali", response.getBody().getName());
    }

    @Test
    public void getById_notFound() {
        ResponseEntity<APICustomResponse> response = this.restTemplate.getForEntity("/api/cities/id/554", APICustomResponse.class);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("City not found with id 554", response.getBody().getMessage());
    }

    @Test
    public void create_success() {
        City city = new City("Nyabihu", 11);
        ResponseEntity<City> response = restTemplate.postForEntity("/api/cities/add", city, City.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Nyabihu", Objects.requireNonNull(response.getBody()).getName());
    }

    @Test
    public void create_fail() {
        City city = new City("Musanze", 11);
        ResponseEntity<City> response = restTemplate.postForEntity("/api/cities/add", city, City.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}

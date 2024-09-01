package com.bank.cliente.integration.controlleres;



import com.bank.cliente.services.CuentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest
@ActiveProfiles("test")
public class CuentasRestControllerTest {

    @Autowired
    private CuentaService cuentaService;


    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    private MockRestServiceServer mockServer;

    private final String urlApiCuenta = "http://localhost:8081/api/cuenta";


    @BeforeEach
    public void setUp() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void whenM1ServiceReturnsData_thenM2ServiceShouldProcessResponse() {

        mockServer.expect(once(), requestTo(urlApiCuenta))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                       ); // Mocked response

        Object m2Response = cuentaService.getAllCuentas();

        //mockServer.verify();
        assertThat(m2Response).isNotNull();
//        assertThat(m2Response.getStatusCode()).isEqualTo(HttpStatus.OK);
        // Additional assertions based on what process should return
    }



    @Test
    public void whenM1ServiceIsCalled_thenM2ServiceShouldProcessResponse() {
        // Assuming M1 service is running locally or use MockRestServiceServer for a mock
        ResponseEntity  responseEntity = new RestTemplate().getForEntity(urlApiCuenta, List.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        // Further assertions on m2Response based on expected behavior
    }
}

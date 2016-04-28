package uk.co.mits4u.primes.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;
import uk.co.mits4u.primes.Application;

import java.net.URL;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class PrimesApiIT {

    @Value("${local.server.port}")
    private int port;

    private URL base;
    private RestTemplate template;

    @Before
    public void setUp() throws Exception {
        template = new TestRestTemplate();
    }

    @Test
    public void isNonPrime() throws Exception {

        base = new URL("http://localhost:" + port + "/numbers/10/isPrime");
        ResponseEntity<Boolean> response = template.getForEntity(base.toString(), Boolean.class);

        Boolean isPrime = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(isPrime).isFalse();

    }

    @Test
    public void isPrimeWithChosenStrategy() throws Exception {

        base = new URL("http://localhost:" + port + "/numbers/101/isPrime?algorithm=SUNDARAM");
        ResponseEntity<Boolean> response = template.getForEntity(base.toString(), Boolean.class);

        Boolean isPrime = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(isPrime).isTrue();

    }

    @Test
    public void calculatePrimesWithDefaults() throws Exception {

        base = new URL("http://localhost:" + port + "/numbers/primes?ceiling=10");
        ResponseEntity<Collection> response = template.getForEntity(base.toString(), Collection.class);

        Collection<Integer> primes = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(primes).containsExactly(2, 3, 5, 7);

    }

    @Test
    public void calculatePrimesWithSpecifiedRangeAndAlgorithm() throws Exception {

        base = new URL("http://localhost:" + port + "/numbers/primes?floor=0&ceiling=10&algorithm=SUNDARAM");
        ResponseEntity<Collection> response = template.getForEntity(base.toString(), Collection.class);

        Collection<Integer> primes = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(primes).containsExactly(2, 3, 5, 7);

    }

    @Test
    public void calculatePrimesWithLetters() throws Exception {

        base = new URL("http://localhost:" + port + "/numbers/one/isPrime");
        ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    public void calculatePrimesForWrongRange() throws Exception {

        base = new URL("http://localhost:" + port + "/numbers/primes?floor=10&ceiling=1");
        ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);

        String responseBody = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(responseBody).contains("Internal Server Error");

    }

}
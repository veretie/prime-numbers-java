package uk.co.mits4u.primes.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.mits4u.primes.Application;

import java.net.URL;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PrimesApiIT {

    @Value("${local.server.port}")
    private int port;

    private URL base;
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        template = new TestRestTemplate();
    }

    @Test
    public void isPrimeForBiggestAllowed() throws Exception {

        base = new URL("http://localhost:" + port + "/numbers/16777213/isPrime");
        ResponseEntity<Boolean> response = template.getForEntity(base.toString(), Boolean.class);

        Boolean isPrime = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(isPrime).isTrue();

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

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Boolean isPrime = response.getBody();

        assertThat(isPrime).isTrue();

    }

    @Test
    public void calculatePrimesWithDefaults() throws Exception {

        base = new URL("http://localhost:" + port + "/numbers/primes?ceiling=10");

        ResponseEntity<Collection> response = template.getForEntity(base.toString(), Collection.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Collection<Integer> primes = response.getBody();

        assertThat(primes).containsExactly(2, 3, 5, 7);

    }

    @Test
    public void calculatePrimesWithSpecifiedRangeAndAlgorithm() throws Exception {

        base = new URL("http://localhost:" + port + "/numbers/primes?floor=0&ceiling=10&algorithm=SUNDARAM");
        ResponseEntity<Collection> response = template.getForEntity(base.toString(), Collection.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Collection<Integer> primes = response.getBody();

        assertThat(primes).containsExactly(2, 3, 5, 7);

    }

    @Test
    public void calculatePrimesWithLetters() throws Exception {

        base = new URL("http://localhost:" + port + "/numbers/one/isPrime");
        ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }

    @Test
    public void calculatePrimesForWrongRange() throws Exception {

        base = new URL("http://localhost:" + port + "/numbers/primes?floor=10&ceiling=1");
        ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        String responseBody = response.getBody();

        assertThat(responseBody).contains("floor [10] cannot be higher then ceiling [1]");

    }

}
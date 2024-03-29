package uk.co.mits4u.primes.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@Tag(name = "prime-numbers", description = "Prime numbers service")
@RestController
public interface PrimesApi {

    @Operation(summary = "Calculate primes in specified [floor, ceiling] range")
    @GetMapping(path = "/numbers/primes", produces = "application/json")
    Collection<Integer> getPrimesInRange(@Parameter(description = "more or equal [default 0]", required = false)
                                         @RequestParam(name = "floor", defaultValue = "0") int floor,
                                         @Parameter(description = "less or equal then 2^24 = 16777216", required = true)
                                         @RequestParam("ceiling") int ceiling,
                                         @Parameter(description = "algorithm", required = false)
                                         @RequestParam(name = "algorithm", defaultValue = "ERATOSTHENES") AlgorithmName algorithmName);

    @Operation(summary = "Check if provided number is prime")
    @GetMapping(path = "/numbers/{number}/isPrime", produces = "application/json")
    boolean isPrime(@Parameter(description = "less or equal then 2^24 = 16777216", required = true)
                    @PathVariable("number") int numberToCheck,
                    @Parameter(description = "algorithm", required = false)
                    @RequestParam(name = "algorithm", defaultValue = "ERATOSTHENES") AlgorithmName algorithmName);


}

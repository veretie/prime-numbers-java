package uk.co.mits4u.primes.service;

import org.springframework.stereotype.Service;
import uk.co.mits4u.primes.api.PrimesAlgorithm;
import uk.co.mits4u.primes.api.PrimesApi;

import java.util.Arrays;
import java.util.Collection;

@Service
public class PrimesService implements PrimesApi {

    @Override
    public boolean isPrime(int number) {
        return false;
    }

    @Override
    public Collection<Integer> getPrimesInRange(int floor, int ceiling, PrimesAlgorithm primesAlgorithm) {
        return Arrays.asList(new Integer[]{1, 2, 3});
    }
}

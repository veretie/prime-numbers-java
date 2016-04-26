package uk.co.mits4u.primes.api;

public enum AlgorithmName {

    ONE("Sieve"), TWO("two");

    private String description;

    private AlgorithmName(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}


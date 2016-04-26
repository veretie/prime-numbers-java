package uk.co.mits4u.primes.api;

public enum AlgorithmName {

    ERATOSTHENES("Eratosthenes"), SUNDARAM("Sundaram"), ATKIN("Atkin");

    private String description;

    private AlgorithmName(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}


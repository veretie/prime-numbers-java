package uk.co.mits4u.primes.service;

import javafx.animation.KeyValue;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static uk.co.mits4u.primes.service.NumberValidator.MAX_NUMBER;

@Component
public class NumberValidatorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @InjectMocks
    private NumberValidator numberValidator;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testValidateZeroRange() throws Exception {
        numberValidator.validateRange(0, 0);
    }

    @Test
    public void testValidateMaxRange() throws Exception {
        numberValidator.validateRange(0, MAX_NUMBER);
    }

    @Test
    public void testValidateZeroRangeAtMax() throws Exception {
        numberValidator.validateRange(MAX_NUMBER, MAX_NUMBER);
    }

    @Test
    public void testValidateCorrectRange() throws Exception {
        Stream.of(ImmutablePair.of(0, 1), ImmutablePair.of(0, 2), ImmutablePair.of(1, 1), ImmutablePair.of(1, 2),
                ImmutablePair.of(100, 55000), ImmutablePair.of(1, 100000), ImmutablePair.of(0, 900000), ImmutablePair.of(9000, MAX_NUMBER))
                .forEach(range -> numberValidator.validateRange(0, 1));
    }


    @Test
    public void testValidateMaximumPossibleRange() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("prime cannot be negative. [-2147483648] is invalid");
        numberValidator.validateRange(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    @Test
    public void testValidateMaximumPossiblePositiveRange() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("[2147483647] is invalid. Select number <= 16777216 = 2^24");
        numberValidator.validateRange(0, Integer.MAX_VALUE);
    }

    @Test
    public void testValidateFloorBiggerThenCeiling() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("floor [1] cannot be higher then ceiling [0]");
        numberValidator.validateRange(1, 0);
    }

    @Test
    public void testMaxInt() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("[2147483647] is invalid. Select number <= 16777216 = 2^24");
        numberValidator.validateNumber(Integer.MAX_VALUE);
    }

    @Test
    public void testMinInt() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("prime cannot be negative. [-2147483648] is invalid");
        numberValidator.validateNumber(Integer.MIN_VALUE);
    }

    @Test
    public void testMaxNumber() throws Exception {
        numberValidator.validateNumber(MAX_NUMBER);
    }

    @Test
    public void testZero() throws Exception {
        numberValidator.validateNumber(0);
    }

    @Test
    public void testNegativeNumber() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("prime cannot be negative. [-1] is invalid");
        numberValidator.validateNumber(-1);
    }

    @Test
    public void testNumberTooBig() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("[16777217] is invalid. Select number <= 16777216 = 2^24");
        numberValidator.validateNumber(MAX_NUMBER + 1);
    }


}
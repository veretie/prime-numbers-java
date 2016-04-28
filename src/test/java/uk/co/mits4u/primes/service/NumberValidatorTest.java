package uk.co.mits4u.primes.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Component;

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
    public void testValidateCorrectRange() throws Exception {
        numberValidator.validateRange(0, 1);
    }

    @Test
    public void testValidateMaxRange() throws Exception {
        numberValidator.validateRange(0, MAX_NUMBER);
    }

    @Test
    public void testValidateZeroRange() throws Exception {
        numberValidator.validateRange(0, 0);
    }

    @Test
    public void testValidateZeroRangeAtMax() throws Exception {
        numberValidator.validateRange(MAX_NUMBER, MAX_NUMBER);
    }

    @Test
    public void testValidateFloorBiggerThenCeiling() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("floor [1] cannot be higher then ceiling [0]");
        numberValidator.validateRange(1, 0);
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
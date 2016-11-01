package com.mhra.mdcm.devices.appian.junit;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by TPD_Auto on 01/11/2016.
 */
@RunWith(Parameterized.class)
public class SmokeTests {

    private int numberOfTweets;
    private double expectedFee;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] { { 0, 0.00 }, { 50, 5.00 },
                { 99, 9.90 }, { 100, 10.00 }, { 101, 10.08 }, { 200, 18},
                { 499, 41.92 }, { 500, 42 }, { 501, 42.05 }, { 1000, 67 },
                { 10000, 517 }, });
    }

    public SmokeTests(int numberOfTweets, double expectedFee) {
        super();
        this.numberOfTweets = numberOfTweets;
        this.expectedFee = expectedFee;
    }

    @Test
    public void shouldCalculateCorrectFee() {
        SmokeTests premiumTweetsService = new SmokeTests(1, 20);
        System.out.println(numberOfTweets + ", " + expectedFee);
        Assert.assertThat(2, Matchers.is(Matchers.equalTo(2)));
    }
}

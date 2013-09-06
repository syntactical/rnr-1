package com.springapp.mvc.web;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HelloControllerTest {
    @Before
    public void setup() {
    }

    @Test
    public void simple() throws Exception {
        assertThat(true, is(true));
    }
}

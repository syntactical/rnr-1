package model;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Thoughtworker
 * Date: 9/11/13
 * Time: 11:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class TDateTest {

    @Test
    public void shouldGetMonth() throws Exception {
        TDate start = new TDate("1/3/2103");
        assertThat(start.getMonth(), is(1));
    }

    @Test
    public void shouldGetDay() throws Exception {
        TDate start = new TDate("2/4/2000");
        assertThat(start.getDay(), is(4));
    }

    @Test
    public void shouldGetYear() throws Exception {
        TDate start = new TDate("2/4/2000");
        assertThat(start.getYear(), is(2000));
    }
}

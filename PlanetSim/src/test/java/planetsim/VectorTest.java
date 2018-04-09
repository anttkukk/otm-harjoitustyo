/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetsim;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author anttkukk
 */
public class VectorTest {

    private Vector v;

    public VectorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        v = new Vector(2.0, 1.0);
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void vectorEqualsIsWorkingWhenVectorsAreSame() {
        Vector x = new Vector(2.0, 1.0);
        assertTrue(v.equals(x));
    }

    @Test
    public void vectorEqualsIsWorkingWhenVectorsAreNotSame() {
        Vector x = new Vector(1.0, 2.0);
        assertTrue(!v.equals(x));
    }

    @Test
    public void multiplyIsWorking() {
        v = v.multiply(1.0);
        assertEquals("2.0, 1.0", v.toString());
    }
    @Test
    public void additionIsWorking() {
        Vector x = new Vector(1.0, 2.0);
        v = v.add(x);
        assertEquals("3.0, 3.0",v.toString());
    }
}

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.function.BinaryOperator;

public class TestFileExist {
    @Test
    public void testFileExist() {

        Boolean result = Main.fileExist("src/main/Data/data2.csv");

        Assert.assertTrue(result);

    }

    @Before
    public void before() {

        Date date = new Date();

        System.out.println("Начало теста " + date.toString());

    }

    @After
    public void after(){

        Date date = new Date();

        System.out.println("Окончание теста " + date.toString());

    }

}

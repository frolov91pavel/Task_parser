import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TestJsonToList {

    @Test
    public void testJsonToList() {

        final String original = "[{\"id\":1,\"firstName\":\"John1\",\"lastName\":\"Smith\",\"country\":\"USA\",\"age\":25},{\"id\":2,\"firstName\":\"Inav\",\"lastName\":\"Petrov\",\"country\":\"RU\",\"age\":23}]";
        List<Employee> listResult = Main.jsonToList(original);


        Employee employee1 = new Employee(1, "John", "Smith", "USA", 25);
        Employee employee2 = new Employee(2, "Inav", "Petrov", "RU", 23);

        List<Employee> listExpected = new ArrayList<Employee>();
        listExpected.add(employee1);
        listExpected.add(employee2);

        Assert.assertEquals(listExpected.toString(), listResult.toString());

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
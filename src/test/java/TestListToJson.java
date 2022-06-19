import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestListToJson {

    @Test
    public void testListToJson() {

        Employee employee = new Employee(1,"A","B","RU",22);
        List<Employee> list1 = new ArrayList<>();
        list1.add(employee);

        String result =  Main.listToJson(list1);

        String expected = "[{\"id\":1,\"firstName\":\"С\",\"lastName\":\"B\",\"country\":\"RU\",\"age\":22}]";

        Assert.assertEquals(expected,result);

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

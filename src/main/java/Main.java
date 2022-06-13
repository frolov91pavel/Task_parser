import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        ///+++Задача 1: CSV - JSON парсер
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String pathCSV = "src/main/Data/data.csv";
        List<Employee> listCSV = parseCSV(columnMapping, pathCSV);
        String jsonCSV = listToJson(listCSV);
        writeString(jsonCSV, "src/main/Data/JsonCSV.txt");
        ///---

        ///+++Задача 2: XML - JSON парсер
        String pathXML = "src/main/Data/data.xml";
        List<Employee> listXML = parseXML(pathXML);
        String jsonXML = listToJson(listXML);
        writeString(jsonXML, "src/main/Data/JsonXML.txt");
        ///---

        ///+++Задача 3: JSON парсер (со звездочкой *)
        String json = readString("src/main/Data/JsonXML.txt");
        List<Employee> list = jsonToList(json);

        for (Employee e : list){
            System.out.println(e.toString());
        }
        ///---

    }

    private static List<Employee> jsonToList(String json) {

        List<Employee> employees = new ArrayList<>();

        Object obj = null;

        try {
            obj = new JSONParser().parse(json);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        JSONArray jsonArray = (JSONArray) obj;

        for (Object objJsonArray : jsonArray) {

            JSONObject empl = (JSONObject) objJsonArray;

            Long id = (Long) empl.get("id");
            String firstName = (String) empl.get("firstName");
            String lastName = (String) empl.get("lastName");
            String country = (String) empl.get("country");
            Long ageLong = (Long) empl.get("age");
            int age = ageLong.intValue();
            employees.add(new Employee(id, firstName, lastName, country, age));
        }

        return employees;
    }

    private static String readString(String path) {

        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String s;
            while ((s = br.readLine()) != null) {
                stringBuilder.append(s);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return stringBuilder.toString();

    }

    private static List<Employee> parseXML(String path) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

        Document document;
        try {
            document = builder.parse(new File(path));
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Employee> list = getListEmployees(document);

        return list;

    }

    public static List<Employee> getListEmployees(Document document) {

        List<Employee> employees = new ArrayList<>();

        NodeList employeeElements = document.getDocumentElement().getElementsByTagName("employee");

        for (int i = 0; i < employeeElements.getLength(); i++) {

            Node employee = employeeElements.item(i);

            long id = 0;
            String firstName = null;
            String lastName = null;
            String country = null;
            int age = 0;

            NodeList nodeList = employee.getChildNodes();

            for (int j = 0; j < nodeList.getLength(); j++) {

                Node tag = nodeList.item(j);

                if (Node.ELEMENT_NODE == tag.getNodeType()) {

                    switch (tag.getNodeName()) {
                        case ("id"):
                            id = Long.parseLong(tag.getTextContent());
                            break;
                        case ("firstName"):
                            firstName = tag.getTextContent();
                            break;
                        case ("lastName"):
                            lastName = tag.getTextContent();
                            break;
                        case ("country"):
                            country = tag.getTextContent();
                            break;
                        case ("age"):
                            age = Integer.parseInt(tag.getTextContent());
                            break;
                        default:
                            break;
                    }
                }
            }

            if (nodeList.getLength() > 0) {
                employees.add(new Employee(id, firstName, lastName, country, age));
            }

        }

        return employees;
    }

    private static List<Employee> parseCSV(String[] columnMapping, String fileName) {

        try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);
            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(csvReader).withMappingStrategy(strategy).build();
            List<Employee> staff = csv.parse();
            return staff;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void writeString(String json, String path) {

        try (FileWriter writer = new FileWriter(path, false)) {
            writer.write(json);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private static String listToJson(List<Employee> list) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        String json = gson.toJson(list, listType);
        return json;
    }

}

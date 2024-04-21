import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.awt.*;
import java.io.File;

public class CreateDDLMySQLTest extends CreateDDLMySQL{
    CreateDDLMySQL globalTestObj;
    @Before
    public void setGlobalTestObjUp(){
        globalTestObj = new CreateDDLMySQL(new EdgeTable[0],new EdgeField[0]);
    }
    @After
    public void resetGlobalTestObjUp(){
        globalTestObj = new CreateDDLMySQL(new EdgeTable[0],new EdgeField[0]);
    }

    @Test
    public void testConstructorWithCorrectInfo(){
        EdgeSaveParser convert = new EdgeSaveParser(new File("Courses2.edg.sav"));

        String one = "Table: 1\n" +
                "{\n" +
                "TableName: STUDENT\n" +
                "NativeFields: 7|8\n" +
                "RelatedTables: \n" +
                "RelatedFields: 0|0\n" +
                "}";
        String two = "Table: 2\n" +
                "{\n" +
                "TableName: FACULTY\n" +
                "NativeFields: 11|6\n" +
                "RelatedTables: 13\n" +
                "RelatedFields: 0|0\n" +
                "}";
        String three = "Table: 13\n" +
                "{\n" +
                "TableName: COURSES\n" +
                "NativeFields: 3|5\n" +
                "RelatedTables: 2\n" +
                "RelatedFields: 0|0\n" +
                "}\n";
        String four = "3|Grade|13|0|0|0|1|false|false|";
        String five = "4|CourseName|0|0|0|0|1|false|false|";
        String siz = "5|Number|13|0|0|0|1|false|false|";
        String seven = "6|FacSSN|2|0|0|0|1|false|false|";
        String eight = "7|StudentSSN|1|0|0|0|1|false|false|";
        String nine = "8|StudentName|1|0|0|0|1|false|false|";
        String ten = "11|FacultyName|2|0|0|0|1|false|false|";
        String[] fakeTable = {one,two,three};
        String[] fakeFields = {four,five,siz,seven,eight,nine,ten};
        CreateDDLMySQL testObj = new CreateDDLMySQL(convert.getEdgeTables(), convert.getEdgeFields());
        for (int i = 0; i < testObj.tables.length; i++) {
            String expected = fakeTable[i].trim().replaceAll("\\r\\n", "\n").replaceAll("\\r", "\n");
            String actual = testObj.tables[i].toString().trim().replaceAll("\\r\\n", "\n").replaceAll("\\r", "\n");
            assertEquals("CreateDDL should have the same printed table as " + fakeTable[i], expected, actual);
        }
        for (int i = 0; i < testObj.tables.length; i++) {
            assertEquals("CreateDDL should have the same printed fields as " + fakeFields[i], fakeFields[i].trim(),testObj.fields[i].toString().trim());
        }

    }
    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullValues(){
        CreateDDLMySQL testObj = new CreateDDLMySQL(null,null);
    }
    @Test
    public void testConstructorWithEmptyValues(){
        CreateDDLMySQL testObj = new CreateDDLMySQL(new EdgeTable[0],new EdgeField[0]);
        boolean test1 = false;
        boolean test2 = false;
        if(testObj.tables.length == 0){
            test1 = true;
        }
        if(testObj.fields.length == 0){
            test2 = true;
        }
        assertEquals("Table should be empty", true,test1);
        assertEquals("Fields should be empty", true,test2);
    }


    @Test
    public void testCreateDDLWithConnections(){
        EdgeSaveParser convert = new EdgeSaveParser(new File("Courses3.edg.sav"));
        CreateDDLMySQL testObj = new CreateDDLMySQL(convert.getEdgeTables(), convert.getEdgeFields());
        testObj.createDDL();

        String sqlString = testObj.sb.toString();
        String toTest = "CREATE DATABASE MySQLDB;\n" +
                "USE MySQLDB;\n" +
                "CREATE TABLE STUDENT (\n" +
                "\tStudentSSN VARCHAR(1),\n" +
                "\tStudentName VARCHAR(1)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE COURSES (\n" +
                "\tGrade VARCHAR(1),\n" +
                "\tNumber VARCHAR(1)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE FACULTY (\n" +
                "\tFacultyName VARCHAR(1),\n" +
                "\tFacSSN VARCHAR(1),\n" +
                "CONSTRAINT FACULTY_FK1 FOREIGN KEY(FacultyName) REFERENCES COURSES(Grade),\n" +
                "CONSTRAINT FACULTY_FK2 FOREIGN KEY(FacSSN) REFERENCES COURSES(Grade)\n" +
                ");\n";
        String expected = toTest.trim().replaceAll("\\r\\n", "\n").replaceAll("\\r", "\n");
        String actual = sqlString.trim().replaceAll("\\r\\n", "\n").replaceAll("\\r", "\n");
        assertEquals("Should contain correct Constraint and refernces",expected,actual);


    }
    @Test
    public void testCreateDDLWithNoInfo(){
        CreateDDLMySQL testObj = new CreateDDLMySQL(new EdgeTable[0],new EdgeField[0]);
        testObj.createDDL();

        String testString = "CREATE DATABASE MySQLDB;\n" + "USE MySQLDB;";
        String expected = testString.trim().replaceAll("\\r\\n", "\n").replaceAll("\\r", "\n");
        String actual = testObj.sb.toString().trim().replaceAll("\\r\\n", "\n").replaceAll("\\r", "\n");
        assertEquals("Should only createDatabase",expected,actual);

    }


    @Test(expected = Exception.class)
    public void testCreateDDLWithCorruptedInfo(){
        EdgeSaveParser convert = new EdgeSaveParser(new File("Courses4.edg.sav"));
        CreateDDLMySQL testObj = new CreateDDLMySQL(convert.getEdgeTables(), convert.getEdgeFields());
        testObj.createDDL();
    }

    @Test
    public void testCreateDDLWithSQLInjection(){
        EdgeSaveParser convert = new EdgeSaveParser(new File("Courses8.edg.sav"));
        CreateDDLMySQL testObj = new CreateDDLMySQL(convert.getEdgeTables(), convert.getEdgeFields());
        testObj.createDDL();
        String test = testObj.sb.toString().replaceAll("\r\n", "");;
        int index = test.indexOf("SELECT");
        assertEquals("Create DDL expression contains illegal terms", true,test.contains("SELECT"));
    }
    @Test
    public void testGetSQLStringExpectedSQLString() throws AWTException {
        EdgeSaveParser convert = new EdgeSaveParser(new File("Courses2.edg.sav"));
        CreateDDLMySQL testObj = new CreateDDLMySQL(convert.getEdgeTables(), convert.getEdgeFields());
        String sqlString = "CREATE DATABASE MySQLDB;\n" +
                "USE MySQLDB;\n" +
                "CREATE TABLE STUDENT (\n" +
                "\tStudentSSN VARCHAR(1),\n" +
                "\tStudentName VARCHAR(1)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE FACULTY (\n" +
                "\tFacultyName VARCHAR(1),\n" +
                "\tFacSSN VARCHAR(1)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE COURSES (\n" +
                "\tGrade VARCHAR(1),\n" +
                "\tNumber VARCHAR(1)\n" +
                ");";
        String expected = sqlString.trim().replaceAll("\\r\\n", "\n").replaceAll("\\r", "\n");
        String actual = testObj.getSQLString().trim().replaceAll("\\r\\n", "\n").replaceAll("\\r", "\n");
        assertEquals("Sql string should be the same generated", expected,actual);
    }

    @Test
    public void testConvertStrBooleanToIntTrue() {

        int result = globalTestObj.convertStrBooleanToInt("true");
        assertEquals(1, result);
    }

    @Test
    public void testConvertStrBooleanToIntFalse() {
        int result = globalTestObj.convertStrBooleanToInt("false");
        assertEquals(0, result);
    }

    @Test
    public void testConvertStrBooleanToIntOtherInput() {

        int result = globalTestObj.convertStrBooleanToInt("other");
        assertEquals(0, result);
    }

    @Test
    public void testGenerateDatabaseName() {
        //Simply hit enter for the default
        String dbName = globalTestObj.generateDatabaseName();
        assertNotNull("Database name is not null",dbName);
        assertFalse("Database name is not empty",dbName.isEmpty());
    }

    @Test
    public void testGenerateDatabaseNameCancel() {
        //exit out of genertate database name promt,
        //number 3 ran during tests
        String dbName = globalTestObj.generateDatabaseName();

        assertEquals("Database is null", dbName,"");
    }

    @Test
    public void testCreateDDLWithNullDatabaseName(){
        //exit out of window when prompted
        //number 2 ran during test for some reason
        globalTestObj.createDDL();

        String testString = "CREATE DATABASE ;\n" + "USE ;";
        String expected = testString.trim().replaceAll("\\r\\n", "\n").replaceAll("\\r", "\n");
        String actual = globalTestObj.sb.toString().trim().replaceAll("\\r\\n", "\n").replaceAll("\\r", "\n");
        assertEquals("Their should be no database name given",expected,actual);

    }












}

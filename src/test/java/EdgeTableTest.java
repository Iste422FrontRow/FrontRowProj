import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EdgeTableTest {
	EdgeTable testObj;

	@Before
	public void setUp() throws Exception {
		testObj = new EdgeTable("1|Courses");
	}

	//Constructor
	@Test
	public void testConstructor() {

		//Setup
		String expectedName = "Courses";
		int expectedNumFigure = 1;

		// Actual
		EdgeTable obj = new EdgeTable("1|Courses");
		
		//Analyze
		assertEquals("EdgeTable constructor was created, testing numFigure and name are correct",expectedName,obj.getName());
		assertEquals(expectedNumFigure, obj.getNumFigure());
	}

	@Test
	public void testGetName() {
		assertEquals("Name is set to Courses","Courses",testObj.getName());
	}

	@Test
	public void testGetNumFigure() {
		assertEquals("NumFigure is set to 1",1,testObj.getNumFigure());
	}
	
	

	@Test
	public void testAddandGetRelatedTable() {
		//Setup

		testObj.addRelatedTable(2);
		testObj.addRelatedTable(3);
		testObj.makeArrays();

		int[] expectedInts = {2,3};
		int[] actualTable = testObj.getRelatedTablesArray();

		//analyze
		assertEquals("Tables 2 and 3 were added",expectedInts[0],actualTable[0]);
		assertEquals(expectedInts[1], actualTable[1]);
	}

	@Test
	public void testAddandGetNativeField() {
		//Setup

		testObj.addNativeField(4);
		testObj.addNativeField(5);
		testObj.makeArrays();

		int[] expectedInts = {4,5};
		int[] actualTable = testObj.getNativeFieldsArray();

		//analyze
		assertEquals("Native field indexs 4 and 5 were added",expectedInts[0],actualTable[0]);
		assertEquals(expectedInts[1], actualTable[1]);

	}

	
	@Test
	public void testGetandSetRelatedFields() {
		//Setup
		EdgeTable obj = new EdgeTable("1|Courses");
		obj.addNativeField(4);
		obj.addRelatedTable(2);
		obj.makeArrays();
		obj.setRelatedField(0, 4);

		int expected = 4;

		//actual
		int[] actualTable = obj.getRelatedFieldsArray();

		//analyze
		assertEquals("Related field 4 reutned when Native field 4 related to Table 2",expected,actualTable[0]);

	}

	@Test
	public void testMoveFieldUp() {
		//Setup
		EdgeTable obj = new EdgeTable("1|Courses");
		obj.addNativeField(4);
		obj.addNativeField(5);
		obj.makeArrays();

		int[] expectedInts = {5,4};

		//actual
		obj.moveFieldUp(1);

		int[] actualTable = obj.getNativeFieldsArray();

		//analyze
		assertEquals("Native field 4  moved up above 5",expectedInts[0],actualTable[0]);
		assertEquals(expectedInts[1], actualTable[1]);

	}

	@Test
	public void testMoveFieldDown() {
		//Setup
		EdgeTable obj = new EdgeTable("1|Courses");
		obj.addNativeField(4);
		obj.addNativeField(5);
		obj.makeArrays();

		int[] expectedInts = {5,4};

		//actual
		obj.moveFieldDown(0);

		int[] actualTable = obj.getNativeFieldsArray();

		//analyze
		assertEquals("Native field 4  moved up above 5",expectedInts[0],actualTable[0]);
		assertEquals(expectedInts[1], actualTable[1]);

	}

	//Negative
	@Test
	public void testBadConstructor() {
		EdgeTable obj;

		// Actual
		try {
			obj = new EdgeTable("badstring");	
		
			//If went through
			assert false;
		}
		catch (Exception e) {
			//If error
			assert true;
		}
	}

	// @Test
	// public void testBadGetName() {

	// 	assertEquals("Name is set to Courses","Courses",testObj.getName());
	// }

	// @Test
	// public void testGetNumFigure() {
	// 	assertEquals("NumFigure is set to 1",1,testObj.getNumFigure());
	// }
	
	
	@Test
	public void testBadGetRelatedTable() {
		//analyze
		assertNull("Looking for Related table before instatiated", testObj.getRelatedTablesArray());
	}

	@Test
	public void testBadGetNativeField() {
		//Setup
		assertNull("Looking for Native fields before instatiated", testObj.getNativeFieldsArray());
	}

	
	@Test
	public void testBadGetRelatedFields() {
		//Setup
		assertNull("Looking for Related fields before instatiated", testObj.getRelatedFieldsArray());
	}

	
	@Test
	public void testBadSetRelatedFields() {
		//Setup
		EdgeTable obj = new EdgeTable("1|Courses");
		obj.addNativeField(4);
		obj.addRelatedTable(2);
		obj.makeArrays();
		try {
			obj.setRelatedField(-1, 4);
			assert false;
		} catch (Exception e) {
			assert true;
		}
	}

	@Test
	public void testBadMoveFieldUp() {
		//Setup
		EdgeTable obj = new EdgeTable("1|Courses");
		obj.addNativeField(4);
		obj.addNativeField(5);
		obj.makeArrays();

		int[] expectedInts = {4,5};

		//actual
		obj.moveFieldUp(0);

		int[] actualTable = obj.getNativeFieldsArray();

		//analyze
		assertEquals("Native field 4 try to move out of bounds, but returned back to normal",expectedInts[0],actualTable[0]);
		assertEquals(expectedInts[1], actualTable[1]);

	}

	@Test
	public void testBadMoveFieldDown() {
		//Setup
		EdgeTable obj = new EdgeTable("1|Courses");
		obj.addNativeField(4);
		obj.addNativeField(5);
		obj.makeArrays();

		int[] expectedInts = {4,5};

		//actual
		obj.moveFieldDown(1);

		int[] actualTable = obj.getNativeFieldsArray();

		//analyze
		assertEquals("Native field 5 tried to move out of bounds, returned back to same state",expectedInts[0],actualTable[0]);
		assertEquals(expectedInts[1], actualTable[1]);

	}

	@Test
	public void testBadMakeArray() {
		//Setup
		EdgeTable obj = new EdgeTable("1|Courses");
		obj.makeArrays();

		int[] expected = {};

		//analyze
		assertArrayEquals("Assert that Native Fields is empty", expected , obj.getNativeFieldsArray());
		assertArrayEquals("Assert that Related Fields is empty", expected , obj.getRelatedFieldsArray());
		assertArrayEquals("Assert that Related Table is empty", expected , obj.getNativeFieldsArray());

	}

	

	


}

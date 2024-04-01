import static org.junit.Assert.*;
import org.junit.Test;
public class EdgeFieldTest {
    @Test
    public void testConstructsWithValidInputString() {
        String inputString = "1|Name";
        EdgeField edgeField = new EdgeField(inputString);
        assertEquals(1, edgeField.getNumFigure());
        assertEquals("Name", edgeField.getName());
        assertEquals(0, edgeField.getTableID());
        assertEquals(0, edgeField.getTableBound());
        assertEquals(0, edgeField.getFieldBound());
        assertFalse(edgeField.getDisallowNull());
        assertFalse(edgeField.getIsPrimaryKey());
        assertEquals("", edgeField.getDefaultValue());
        assertEquals(EdgeField.VARCHAR_DEFAULT_LENGTH, edgeField.getVarcharValue());
        assertEquals(0, edgeField.getDataType());
        assertArrayEquals(new String[]{"Varchar", "Boolean", "Integer", "Double"}, EdgeField.getStrDataType());
        assertEquals("1|Name|0|0|0|0|1|false|false|", edgeField.toString());
    }
    @Test
    public void testReturnsCorrectNumFigureValue() {
        EdgeField edgeField = new EdgeField("1|Name");
        assertEquals(1, edgeField.getNumFigure());
    }

    @Test
    public void testReturnsCorrectNameValue() {
        EdgeField edgeField = new EdgeField("1|Name");
        assertEquals("Name", edgeField.getName());
    }

    @Test
    public void testReturnsCorrectTableIDValue() {
        EdgeField edgeField = new EdgeField("1|Name");
        assertEquals(0, edgeField.getTableID());
    }

    @Test
    public void testSetsCorrectTableIDValue() {
        EdgeField edgeField = new EdgeField("1|Name");
        edgeField.setTableID(2);
        assertEquals(2, edgeField.getTableID());
    }

    @Test
    public void testReturnsCorrectTableBoundValue() {
        EdgeField edgeField = new EdgeField("1|Name");
        assertEquals(0, edgeField.getTableBound());
    }

    @Test
    public void testSetsCorrectTableBoundValue() {
        EdgeField edgeField = new EdgeField("1|Name");
        edgeField.setTableBound(2);
        assertEquals(2, edgeField.getTableBound());
    }

    @Test
    public void testReturnsCorrectFieldBoundValue() {
        EdgeField edgeField = new EdgeField("1|Name");
        assertEquals(0, edgeField.getFieldBound());
    }

    @Test
    public void testSetsCorrectFieldBoundValue() {
        EdgeField edgeField = new EdgeField("1|Name");
        edgeField.setFieldBound(2);
        assertEquals(2, edgeField.getFieldBound());
    }

    @Test
    public void testReturnsCorrectDisallowNullValue() {
        EdgeField edgeField = new EdgeField("1|Name");
        assertEquals(false, edgeField.getDisallowNull());
    }

    @Test
    public void testSetsCorrectDisallowNullValue() {
        EdgeField edgeField = new EdgeField("1|Name");
        edgeField.setDisallowNull(true);
        assertEquals(true, edgeField.getDisallowNull());
    }

    @Test
    public void testReturnsCorrectIsPrimaryKeyValue() {
        EdgeField edgeField = new EdgeField("1|Name");
        assertEquals(false, edgeField.getIsPrimaryKey());
    }

    @Test
    public void testSetsCorrectIsPrimaryKeyValue() {
        EdgeField edgeField = new EdgeField("1|Name");
        edgeField.setIsPrimaryKey(true);
        assertEquals(true, edgeField.getIsPrimaryKey());
    }
    @Test
    public void testReturnsCorrectDefaultValue() {
        EdgeField edgeField = new EdgeField("1|Name");
        assertEquals("", edgeField.getDefaultValue());
    }

    @Test
    public void testSetsCorrectDefaultValue() {
        EdgeField edgeField = new EdgeField("1|Name");
        edgeField.setDefaultValue("default");
        assertEquals("default", edgeField.getDefaultValue());
    }

    @Test
    public void testReturnsCorrectVarcharValue() {
        EdgeField edgeField = new EdgeField("1|Name");
        assertEquals(EdgeField.VARCHAR_DEFAULT_LENGTH, edgeField.getVarcharValue());
    }

    @Test
    public void testSetsCorrectVarcharValue() {
        EdgeField edgeField = new EdgeField("1|Name");
        edgeField.setVarcharValue(5);
        assertEquals(5, edgeField.getVarcharValue());
    }

    @Test
    public void testReturnsCorrectDataType() {
        EdgeField edgeField = new EdgeField("1|Name");
        assertEquals(0, edgeField.getDataType());
    }

    @Test
    public void testSetsCorrectDataType() {
        EdgeField edgeField = new EdgeField("1|Name");
        edgeField.setDataType(2);
        assertEquals(2, edgeField.getDataType());
    }

    @Test
    public void testReturnsCorrectStrDataTypeArray() {
        assertArrayEquals(new String[]{"Varchar", "Boolean", "Integer", "Double"}, EdgeField.getStrDataType());
    }

    @Test
    public void testReturnsCorrectStringRepresentation() {
        EdgeField edgeField = new EdgeField("1|Name");
        assertEquals("1|Name|0|0|0|0|1|false|false|", edgeField.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowsErrorWhenConstructingWithInvalidInputStringFormat() {
        String invalidInputString = "InvalidInput";
        new EdgeField(invalidInputString);
    }

    @Test
    public void testDoesNotChangeVarcharValueWhenSetWithNegativeValue() {
        EdgeField edgeField = new EdgeField("1|Name");
        edgeField.setVarcharValue(-1);
        assertEquals(EdgeField.VARCHAR_DEFAULT_LENGTH, edgeField.getVarcharValue());
    }

    @Test
    public void testDoesNotChangeDataTypeWhenSetWithOutOfRangeValue() {
        EdgeField edgeField = new EdgeField("1|Name");
        edgeField.setDataType(10);
        assertNotEquals(10, edgeField.getDataType());
    }



}

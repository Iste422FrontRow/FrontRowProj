import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EdgeConvertCreateDDLTest {

    EdgeField[] fields;
    EdgeTable[] tables;
    public EdgeConvertCreateDDLTest() {
        this.fields = new EdgeField[]{new EdgeField("1|Field"), new EdgeField("2|Field2")};
        this.tables = new EdgeTable[]{new EdgeTable("1|Table"), new EdgeTable("1Table|2")};
    }

    static class ConcreteEdgeConvertCreateDDL extends EdgeConvertCreateDDL{

        public ConcreteEdgeConvertCreateDDL(){
        }

        @Override
        public String getDatabaseName() {
            return null;
        }

        @Override
        public String getProductName() {
            return null;
        }

        @Override
        public String getSQLString() {
            return null;
        }

        @Override
        public void createDDL() {

        }

        @Override
        protected EdgeField getField(int numFigure) {
            return super.getField(numFigure);
        }

        @Override
        protected EdgeTable getTable(int numFigure) {
            return super.getTable(numFigure);
        }
    }

    ConcreteEdgeConvertCreateDDL concrete;

    @Before
    public void setUp() throws Exception {
        concrete = new ConcreteEdgeConvertCreateDDL();
    }

    @Test
    public void constructionTest(){
        assertNotNull(concrete);
    }

    @Test
    public void tablesTest(){
        assertEquals(1, concrete.getField(0).getNumFigure());
    }
}

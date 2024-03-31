const assert = require('assert');
const EdgeField = require('../EdgeField');

describe('EdgeField Class Tests', () => {
    it('Test 1.1: Constructs an EdgeField object with valid inputString', () => {
        const inputString = '1|Name';
        const edgeField = new EdgeField(inputString);
        assert.equal(1, edgeField.getNumFigure());
        assert.equal('Name', edgeField.getName());
        assert.equal(0, edgeField.getTableID());
        assert.equal(0, edgeField.getTableBound());
        assert.equal(0, edgeField.getFieldBound());
        assert.equal(false, edgeField.getDisallowNull());
        assert.equal(false, edgeField.getIsPrimaryKey());
        assert.equal('', edgeField.getDefaultValue());
        assert.equal(EdgeField.VARCHAR_DEFAULT_LENGTH, edgeField.getVarcharValue());
        assert.equal(0, edgeField.getDataType());
        assert.equal(EdgeField.getStrDataType(), ["Varchar", "Boolean", "Integer", "Double"]);
        assert.equal('1|Name|0|0|0|0|1|false|false|', edgeField.toString());
    });

    it('Test 1.2: Returns correct numFigure value when getNumFigure is called', () => {
        const edgeField = new EdgeField('1|Name');
        assert.equal(1, edgeField.getNumFigure());
    });

    it('Test 1.3: Returns correct name value when getName is called', () => {
        const edgeField = new EdgeField('1|Name');
        assert.equal('Name', edgeField.getName());
    });

    it('Test 1.4: Returns correct tableID value when getTableID is called', () => {
        const edgeField = new EdgeField('1|Name');
        assert.equal(0, edgeField.getTableID());
    });

    it('Test 1.5: Sets the correct tableID value when setTableID is called', () => {
        const edgeField = new EdgeField('1|Name');
        edgeField.setTableID(2);
        assert.equal(2, edgeField.getTableID());
    });

    it('Test 1.6: Returns correct tableBound value when getTableBound is called', () => {
        const edgeField = new EdgeField('1|Name');
        assert.equal(0, edgeField.getTableBound());
    });

    it('Test 1.7: Sets the correct tableBound value when setTableBound is called', () => {
        const edgeField = new EdgeField('1|Name');
        edgeField.setTableBound(2);
        assert.equal(2, edgeField.getTableBound());
    });

    it('Test 1.8: Returns correct fieldBound value when getFieldBound is called', () => {
        const edgeField = new EdgeField('1|Name');
        assert.equal(0, edgeField.getFieldBound());
    });

    it('Test 1.9: Sets the correct fieldBound value when setFieldBound is called', () => {
        const edgeField = new EdgeField('1|Name');
        edgeField.setFieldBound(2);
        assert.equal(2, edgeField.getFieldBound());
    });

    it('Test 1.10: Returns correct disallowNull value when getDisallowNull is called', () => {
        const edgeField = new EdgeField('1|Name');
        assert.equal(false, edgeField.getDisallowNull());
    });

    it('Test 1.11: Sets the correct disallowNull value when setDisallowNull is called', () => {
        const edgeField = new EdgeField('1|Name');
        edgeField.setDisallowNull(true);
        assert.equal(true, edgeField.getDisallowNull());
    });

    it('Test 1.12: Returns correct isPrimaryKey value when getIsPrimaryKey is called', () => {
        const edgeField = new EdgeField('1|Name');
        assert.equal(false, edgeField.getIsPrimaryKey());
    });

    it('Test 1.13: Sets the correct isPrimaryKey value when setIsPrimaryKey is called', () => {
        const edgeField = new EdgeField('1|Name');
        edgeField.setIsPrimaryKey(true);
        assert.equal(true, edgeField.getIsPrimaryKey());
    });

    it('Test 1.14: Returns correct defaultValue value when getDefaultValue is called', () => {
        const edgeField = new EdgeField('1|Name');
        assert.equal('', edgeField.getDefaultValue());
    });

    it('Test 1.15: Sets the correct defaultValue value when setDefaultValue is called', () => {
        const edgeField = new EdgeField('1|Name');
        edgeField.setDefaultValue('default');
        assert.equal('default', edgeField.getDefaultValue());
    });

    it('Test 1.16: Returns correct varcharValue value when getVarcharValue is called', () => {
        const edgeField = new EdgeField('1|Name');
        assert.equal(EdgeField.VARCHAR_DEFAULT_LENGTH, edgeField.getVarcharValue());
    });

    it('Test 1.17: Sets the correct varcharValue value when setVarcharValue is called', () => {
        const edgeField = new EdgeField('1|Name');
        edgeField.setVarcharValue(5);
        assert.equal(5, edgeField.getVarcharValue());
    });

    it('Test 1.18: Returns correct dataType value when getDataType is called', () => {
        const edgeField = new EdgeField('1|Name');
        assert.equal(0, edgeField.getDataType());
    });

    it('Test 1.19: Sets the correct dataType value when setDataType is called', () => {
        const edgeField = new EdgeField('1|Name');
        edgeField.setDataType(2);
        assert.equal(2, edgeField.getDataType());
    });

    it('Test 1.20: Returns correct strDataType array when getStrDataType is called', () => {
        assert.deepStrictEqual(EdgeField.getStrDataType(), ["Varchar", "Boolean", "Integer", "Double"]);
    });

    it('Test 1.21: Returns correct string representation of the EdgeField object when toString is called', () => {
        const edgeField = new EdgeField('1|Name');
        assert.equal('1|Name|0|0|0|0|1|false|false|', edgeField.toString());
    });


    // Negative Tests


    it('Test 2.1: Throws an error when constructing an EdgeField object with invalid inputString format', () => {
        const invalidInputString = 'InvalidInput';
        assert.throws(() => new EdgeField(invalidInputString), Error);
    });

    it('Test 2.2: Does not change varcharValue when setVarcharValue is called with a negative value', () => {
        const edgeField = new EdgeField('1|Name');
        edgeField.setVarcharValue(-1);
        assert.equal(EdgeField.VARCHAR_DEFAULT_LENGTH, edgeField.getVarcharValue());
    });

    it('Test 2.3: Does not change dataType when setDataType is called with an out-of-range value', () => {
        const edgeField = new EdgeField('1|Name');
        edgeField.setDataType(10);
        assert.notEqual(10, edgeField.getDataType());
    });

});

CREATE ROWSTORE TABLE lbcat.modify_data_type_test (intColumn numeric(10) NULL, intColumn2 numeric(30) NULL)
ALTER TABLE lbcat.modify_data_type_test MODIFY intColumn VARCHAR(50)
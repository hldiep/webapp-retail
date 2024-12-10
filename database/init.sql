CREATE SEQUENCE OrderProductSequence
    START WITH 6
    INCREMENT BY 1;
CREATE SEQUENCE ProductSequence
    START WITH 34
    INCREMENT BY 1;

CREATE SEQUENCE EmployeeSequence
    START WITH 2
    INCREMENT BY 1;

GO
CREATE PROCEDURE getNextOrderProductSequence
AS
BEGIN
    SELECT NEXT VALUE FOR OrderProductSequence;
END;
GO
CREATE PROCEDURE getNextEmployeeSequence
AS
BEGIN
    SELECT NEXT VALUE FOR EmployeeSequence;
END;
GO
CREATE PROCEDURE getNextProductSequence
AS
BEGIN
    SELECT NEXT VALUE FOR ProductSequence;
END;
GO

SELECT * FROM sys.sequences WHERE name = 'EmployeeSequence';
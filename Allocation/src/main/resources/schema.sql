-- Create Project table
CREATE TABLE IF NOT EXISTS project (
    PROJECTID VARCHAR(50) PRIMARY KEY,
    ACCOUNT_NAME VARCHAR(50),
    PROJECT_NAME VARCHAR(100),
    ALLOCATION DOUBLE,
    PROJECT_START_DATE VARCHAR(20),
    PROJECT_END_DATE VARCHAR(20),
    REMARKS VARCHAR(255)
);

-- Insert sample data into Project table
INSERT INTO project (PROJECTID, ACCOUNT_NAME, PROJECT_NAME, ALLOCATION, PROJECT_START_DATE, PROJECT_END_DATE, REMARKS)
VALUES
    ('P001', 'ANCESTRY', 'Project A', 50.0, '2023-01-01', '2023-12-31', 'This is a sample project for Ancestry'),
    ('P002', 'BNYM', 'Project B', 30.0, '2023-02-01', '2023-11-30', 'This is a sample project for BNYM'),
    ('P003', 'EXPERIAN', 'Project C', 20.0, '2023-03-01', '2023-10-31', 'This is a sample project for Experian'),('P004', 'EXPERIAN', 'Project C', 20.0, '2023-03-01', '2023-10-31', 'This is a sample project for Experian');

-- Create Employee table
CREATE TABLE IF NOT EXISTS employee (
    EMPLOYEEID VARCHAR(50) PRIMARY KEY,
    EMPLOYEE_NAME VARCHAR(100),
    CAPABILITY_CENTRE VARCHAR(50),
    DATE_OF_JOINING VARCHAR(20),
    DESIGNATION VARCHAR(50),
    PRIMARY_SKILL VARCHAR(100),
    SECONDARY_SKILL VARCHAR(100),
    OVERALL_EXPERIENCE INTEGER
);

-- Insert sample data into Employee table
INSERT INTO employee (EMPLOYEEID, EMPLOYEE_NAME, CAPABILITY_CENTRE, DATE_OF_JOINING, DESIGNATION, PRIMARY_SKILL, SECONDARY_SKILL, OVERALL_EXPERIENCE)
VALUES
    ('E001', 'John Doe', 'PRODUCT_AND_PLATFORM', '2021-05-01', 'SENIOR_ENGINEER', 'Java', 'Spring', 5),
    ('E002', 'Jane Smith', 'DEP_CLOUD', '2019-08-15', 'ARCHITECT', 'Cloud', 'AWS', 8),
    ('E003', 'Alice Brown', 'DEVAA', '2020-03-12', 'TECHNICAL_LEAD', 'DevOps', 'Docker', 6),
    ('E004', 'Charlie Green', 'DEP_QUALITY', '2018-06-10', 'STAFF_ENGINEER', 'Automation Testing', 'Selenium', 7);

-- Create EmployeeProject table (assuming this is a join table between Employee and Project)
CREATE TABLE IF NOT EXISTS employee_project (
    EMPLOYEEID VARCHAR(50),
    PROJECTID VARCHAR(50),ALLOCATION DOUBLE NOT NULL DEFAULT 0.0,
    UNIQUE (EMPLOYEEID, PROJECTID),
    FOREIGN KEY (EMPLOYEEID) REFERENCES employee(EMPLOYEEID),
    FOREIGN KEY (PROJECTID) REFERENCES project(PROJECTID)
);

-- Insert sample data into EmployeeProject table (linking employees to projects)
INSERT INTO employee_project (EMPLOYEEID, projectID)
VALUES
    ('E001', 'P001'),
    ('E002', 'P002'),
    ('E003', 'P003'),
    ('E004', 'P001');

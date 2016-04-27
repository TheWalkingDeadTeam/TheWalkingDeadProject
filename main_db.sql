DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

GRANT ALL ON SCHEMA public TO public;
GRANT ALL ON SCHEMA public TO postgres;


CREATE TABLE Report_Template (
  Report_ID serial PRIMARY KEY,
  Query text NOT NULL,
  Name varchar(20) NOT NULL
);

CREATE TABLE Role (
  Role_ID serial PRIMARY KEY,
  Name varchar(20) NOT NULL,
  Description text NOT NULL
);

CREATE TABLE System_User_Status (
  System_User_Status_ID serial PRIMARY KEY,
  Name varchar(20) NOT NULL
);

CREATE TABLE System_User (
  System_User_ID serial PRIMARY KEY,
  Email varchar(100) NOT NULL UNIQUE,
  Password char(32) NOT NULL,
  Name varchar(30) NOT NULL,
  Surname varchar(30) NOT NULL,
  System_User_Status_ID int NOT NULL references System_User_Status(System_User_Status_ID) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE System_User_Role (
  System_User_ID int NOT NULL references System_User(System_User_id)  ON DELETE RESTRICT ON UPDATE CASCADE,
  Role_ID int NOT NULL references Role(Role_id)  ON DELETE RESTRICT ON UPDATE CASCADE,
  PRIMARY KEY(System_User_ID, Role_ID)
);

CREATE TABLE Feedback (
  Feedback_ID serial PRIMARY KEY,
  Score int NOT NULL,
  Comment text NOT NULL,
  Interviewer_ID int NOT NULL references System_User(System_User_id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE Email_Template (
  Email_Template_ID serial PRIMARY KEY,
  Body_Template text NOT NULL,
  Head_Template text NOT NULL
);

CREATE TABLE CES_Status (
  CES_Status_ID serial PRIMARY KEY,
  Name varchar(20) NOT NULL
);

CREATE TABLE Course_Enrollment_Session (
  CES_ID serial PRIMARY KEY,
  Year int NOT NULL,
  Start_Registration_Date date NOT NULL,
  End_Registration_Date date CHECK(End_Registration_Date > Start_Registration_Date),
  Start_Interviewing_Date date CHECK(Start_Interviewing_Date > End_Registration_Date),
  End_Interviewing_Date date CHECK(End_Interviewing_Date > Start_Interviewing_Date),
  Quota int NOT NULL CHECK (Quota > 0),
  CES_Status_ID int NOT NULL references CES_Status(CES_Status_ID)  ON DELETE RESTRICT ON UPDATE CASCADE,
  Reminders int CHECK (Reminders > 0),
  Interviewing_Time_Person int CHECK (Interviewing_Time_Person > 0),
  Interviewing_Time_Day int CHECK (Interviewing_Time_Day > 0)
);

CREATE TABLE Interviewer_Participation (
  System_User_ID int NOT NULL references System_User(System_User_ID)  ON DELETE RESTRICT ON UPDATE CASCADE,
  CES_ID int NOT NULL references Course_Enrollment_Session(CES_ID)  ON DELETE RESTRICT ON UPDATE CASCADE,
  PRIMARY KEY(System_User_ID, CES_ID)
);

CREATE TABLE Application (
  Application_ID serial PRIMARY KEY,
  System_User_ID int NOT NULL references System_User(System_User_ID)  ON DELETE RESTRICT ON UPDATE CASCADE,
  CES_ID int NOT NULL references Course_Enrollment_Session(CES_ID) ON DELETE RESTRICT ON UPDATE CASCADE,
  rejected bool,
  UNIQUE(System_User_ID, CES_ID)
);

CREATE TABLE Interviewee (
  Application_ID int NOT NULL references Application(Application_ID) ON DELETE RESTRICT ON UPDATE CASCADE,
  Interview_Time timestamp NOT NULL,
  Special_mark int,
  Dev_Feedback_ID int NOT NULL references Feedback(Feedback_ID) ON DELETE RESTRICT ON UPDATE CASCADE,
  HR_Feedback_ID int NOT NULL references Feedback(Feedback_ID) ON DELETE RESTRICT ON UPDATE CASCADE,
  PRIMARY KEY(Application_ID)
);

CREATE TABLE List (
  List_ID serial PRIMARY KEY,
  Name varchar(20) NOT NULL
);

CREATE TABLE List_Value (
  List_Value_ID serial PRIMARY KEY,
  List_ID int NOT NULL references List(List_ID) ON DELETE RESTRICT ON UPDATE CASCADE,
  Value_Double float8,
  Value_Text text
  CHECK ((Value_Double IS NOT NULL) OR (Value_Text IS NOT NULL))
);

CREATE TABLE Field_Type (
  Field_Type_ID serial PRIMARY KEY,
  Name varchar(20) NOT NULL
);

CREATE TABLE Field (
  Field_ID serial PRIMARY KEY,
  CES_ID int NOT NULL references Course_Enrollment_Session(CES_ID) ON DELETE RESTRICT ON UPDATE CASCADE,
  Name varchar(20) NOT NULL,
  Field_Type_ID int NOT NULL references Field_Type(Field_Type_ID) ON DELETE RESTRICT ON UPDATE CASCADE,
  Multiple_Choice bool NOT NULL,
  List_ID int references List(List_ID) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE Field_Value (
  Field_ID int NOT NULL references Field(Field_ID) ON DELETE RESTRICT ON UPDATE CASCADE,
  Application_ID int NOT NULL references Application(Application_ID) ON DELETE RESTRICT ON UPDATE CASCADE,
  Value_Text text,
  Value_Double float8,
  Value_Date date,
  List_Value_ID int references List_Value(List_Value_ID) ON DELETE RESTRICT ON UPDATE CASCADE,
  PRIMARY KEY(Field_ID, Application_ID),
  CHECK ((Value_Double IS NOT NULL) OR (Value_Text IS NOT NULL) OR (Value_Date IS NOT NULL) OR (List_Value_ID IS NOT NULL))
);
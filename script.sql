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
  Name varchar(20) NOT NULL UNIQUE,
  Description text NOT NULL
);


CREATE TABLE System_User_Status (
  System_User_Status_ID serial PRIMARY KEY,
  Name varchar(20) NOT NULL UNIQUE
);

CREATE TABLE System_User (
  System_User_ID serial PRIMARY KEY,
  Email varchar(100) NOT NULL UNIQUE,
  Password char(60) NOT NULL,
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
  Reminders int NOT NULL CHECK (Reminders > 0),
  Interviewing_Time_Person int NOT NULL CHECK (Interviewing_Time_Person > 0),
  Interviewing_Time_Day int NOT NULL CHECK ((Interviewing_Time_Day > 0) AND (Interviewing_Time_Day < 24))
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
  rejected bool DEFAULT false,
  UNIQUE(System_User_ID, CES_ID)
);

CREATE TABLE Interviewee (
  Application_ID int NOT NULL references Application(Application_ID) ON DELETE RESTRICT ON UPDATE CASCADE,
  Interview_Time timestamp NOT NULL,
  Special_mark varchar(50),
  Dev_Feedback_ID int references Feedback(Feedback_ID) ON DELETE RESTRICT ON UPDATE CASCADE,
  HR_Feedback_ID int references Feedback(Feedback_ID) ON DELETE RESTRICT ON UPDATE CASCADE,
  PRIMARY KEY(Application_ID)
);

CREATE TABLE List (
  List_ID serial PRIMARY KEY,
  Name varchar(20) NOT NULL
);

CREATE TABLE List_Value (
  List_Value_ID serial PRIMARY KEY,
  List_ID int NOT NULL references List(List_ID) ON DELETE RESTRICT ON UPDATE CASCADE,
  Value_Text text NOT NULL
);

CREATE TABLE Field_Type (
  Field_Type_ID serial PRIMARY KEY,
  Name varchar(20) NOT NULL UNIQUE
);

CREATE TABLE Field (
  Field_ID serial PRIMARY KEY,
  Name varchar(50) NOT NULL,
  Field_Type_ID int NOT NULL references Field_Type(Field_Type_ID) ON DELETE RESTRICT ON UPDATE CASCADE,
  Multiple_Choice bool NOT NULL,
  Order_Num int NOT NULL CHECK (Order_Num > 0),
  List_ID int references List(List_ID) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE CES_Field(
  CES_ID int NOT NULL references Course_Enrollment_Session(CES_ID) ON DELETE RESTRICT ON UPDATE CASCADE,
  Field_ID int NOT NULL references Field(Field_ID) ON DELETE RESTRICT ON UPDATE CASCADE,
  PRIMARY KEY (CES_ID, Field_ID)
);

CREATE TABLE Field_Value (
  Field_ID int NOT NULL references Field(Field_ID) ON DELETE RESTRICT ON UPDATE CASCADE,
  Application_ID int NOT NULL references Application(Application_ID) ON DELETE RESTRICT ON UPDATE CASCADE,
  Value_Text text,
  Value_Double float8,
  Value_Date date,
  List_Value_ID int references List_Value(List_Value_ID) ON DELETE RESTRICT ON UPDATE CASCADE,
  CHECK ((Value_Double IS NOT NULL) OR (Value_Text IS NOT NULL) OR (Value_Date IS NOT NULL) OR (List_Value_ID IS NOT NULL))
);

INSERT INTO System_User_Status (Name) VALUES
  ('Active'),
  ('Inactive');

INSERT INTO Role (Name, Description) VALUES
  ('ROLE_ADMIN', 'Admin of the system'),
  ('ROLE_STUDENT', 'Student who wants to/takes part in the course'),
  ('ROLE_HR', 'HR of Netcracker'),
  ('ROLE_DEV', 'Developer of Netcracker'),
  ('ROLE_BA', 'BA of Netcracker');

INSERT INTO System_User (Email, Password, Name, Surname, System_User_Status_ID) VALUES
  ('ion@gmail.com', '$2a$10$U2HGciX6QWTwus5SEGVKgeCu/OHH7DXSwwBVQaGjO7o.mKyC8J4pq','Ion','Ionets', 1),
  ('rom@gmail.com', '$2a$10$lS6mQkw2ejU.JZjgIFVQAeFemX0Dg9gO9k5Y.AkHW6VeckwNL3Qkm','Roman','Andriichuk',1),
  ('sasha@gmail.com', '$2a$10$bflfk70LoK2kf4DRA2TXo.M43YjvMKFYfEKgwaEqq2rnEleXBgOI.','Sasha','Beskorovaynaya',1),
  ('kirill@gmail.com', '$2a$10$9uck3uR.cTwo9RNye0IcF.Us09QlaOGetAcXxj0QOfdvrxwUVm3ES','Kirill','Tumanov',1),
  ('ivan1@gmail.com', '$2a$10$T6RBfaaPAbeZV4NWo6jN5.WQsr3L632HYwSs34gmF4AHLtxEXx18S','Ivan1','Ivanov1', 1),
  ('ivan2@gmail.com', '$2a$10$T6RBfaaPAbeZV4NWo6jN5.WQsr3L632HYwSs34gmF4AHLtxEXx18S','Ivan2','Ivanov2', 1),
  ('ivan3@gmail.com', '$2a$10$T6RBfaaPAbeZV4NWo6jN5.WQsr3L632HYwSs34gmF4AHLtxEXx18S','Ivan3','Ivanov3', 1),
  ('ivan4@gmail.com', '$2a$10$T6RBfaaPAbeZV4NWo6jN5.WQsr3L632HYwSs34gmF4AHLtxEXx18S','Ivan4','Ivanov4', 1),
  ('ivan5@gmail.com', '$2a$10$T6RBfaaPAbeZV4NWo6jN5.WQsr3L632HYwSs34gmF4AHLtxEXx18S','Ivan5','Ivanov5', 1),
  ('ivan6@gmail.com', '$2a$10$T6RBfaaPAbeZV4NWo6jN5.WQsr3L632HYwSs34gmF4AHLtxEXx18S','Ivan6','Ivanov6', 1),
  ('ivan7@gmail.com', '$2a$10$T6RBfaaPAbeZV4NWo6jN5.WQsr3L632HYwSs34gmF4AHLtxEXx18S','Ivan7','Ivanov7', 1),
  ('ivan8@gmail.com', '$2a$10$T6RBfaaPAbeZV4NWo6jN5.WQsr3L632HYwSs34gmF4AHLtxEXx18S','Ivan8','Ivanov8', 1),
  ('ivan9@gmail.com', '$2a$10$T6RBfaaPAbeZV4NWo6jN5.WQsr3L632HYwSs34gmF4AHLtxEXx18S','Ivan9','Ivanov9', 1),
  ('ivan10@gmail.com', '$2a$10$T6RBfaaPAbeZV4NWo6jN5.WQsr3L632HYwSs34gmF4AHLtxEXx18S','Ivan10','Ivanov10', 1),
  ('petr1@gmail.com','$2a$10$R6Ff5EaAuGnUFuh6G2abN.h6d2JUYG4NtzhuehdcICOKGZoKWib72','Petr1','Petrov1',1),
  ('petr2@gmail.com','$2a$10$R6Ff5EaAuGnUFuh6G2abN.h6d2JUYG4NtzhuehdcICOKGZoKWib72','Petr2','Petrov2',1),
  ('petr3@gmail.com','$2a$10$R6Ff5EaAuGnUFuh6G2abN.h6d2JUYG4NtzhuehdcICOKGZoKWib72','Petr3','Petrov3',1),
  ('petr4@gmail.com','$2a$10$R6Ff5EaAuGnUFuh6G2abN.h6d2JUYG4NtzhuehdcICOKGZoKWib72','Petr4','Petrov4',1),
  ('petr5@gmail.com','$2a$10$R6Ff5EaAuGnUFuh6G2abN.h6d2JUYG4NtzhuehdcICOKGZoKWib72','Petr5','Petrov5',1),
  ('petr6@gmail.com','$2a$10$R6Ff5EaAuGnUFuh6G2abN.h6d2JUYG4NtzhuehdcICOKGZoKWib72','Petr6','Petrov6',1),
  ('petr7@gmail.com','$2a$10$R6Ff5EaAuGnUFuh6G2abN.h6d2JUYG4NtzhuehdcICOKGZoKWib72','Petr7','Petrov7',1),
  ('petr8@gmail.com','$2a$10$R6Ff5EaAuGnUFuh6G2abN.h6d2JUYG4NtzhuehdcICOKGZoKWib72','Petr8','Petrov8',1),
  ('petr9@gmail.com','$2a$10$R6Ff5EaAuGnUFuh6G2abN.h6d2JUYG4NtzhuehdcICOKGZoKWib72','Petr9','Petrov9',1),
  ('petr10@gmail.com','$2a$10$R6Ff5EaAuGnUFuh6G2abN.h6d2JUYG4NtzhuehdcICOKGZoKWib72','Petr10','Petrov10',1),
  ('sidor1@gmail.com','$2a$10$NnkytOyN6DlR9YaopcjEuOMlUAA1nNo6fdckQJEyb4xHKY17yU2Wi','Sidor1','Sidorov1',1),
  ('sidor2@gmail.com','$2a$10$NnkytOyN6DlR9YaopcjEuOMlUAA1nNo6fdckQJEyb4xHKY17yU2Wi','Sidor2','Sidorov2',1),
  ('sidor3@gmail.com','$2a$10$NnkytOyN6DlR9YaopcjEuOMlUAA1nNo6fdckQJEyb4xHKY17yU2Wi','Sidor3','Sidorov3',1),
  ('sidor4@gmail.com','$2a$10$NnkytOyN6DlR9YaopcjEuOMlUAA1nNo6fdckQJEyb4xHKY17yU2Wi','Sidor4','Sidorov4',1),
  ('sidor5@gmail.com','$2a$10$NnkytOyN6DlR9YaopcjEuOMlUAA1nNo6fdckQJEyb4xHKY17yU2Wi','Sidor5','Sidorov5',1),
  ('sidor6@gmail.com','$2a$10$NnkytOyN6DlR9YaopcjEuOMlUAA1nNo6fdckQJEyb4xHKY17yU2Wi','Sidor6','Sidorov6',1),
  ('sidor7@gmail.com','$2a$10$NnkytOyN6DlR9YaopcjEuOMlUAA1nNo6fdckQJEyb4xHKY17yU2Wi','Sidor7','Sidorov7',1),
  ('sidor8@gmail.com','$2a$10$NnkytOyN6DlR9YaopcjEuOMlUAA1nNo6fdckQJEyb4xHKY17yU2Wi','Sidor8','Sidorov8',1),
  ('sidor9@gmail.com','$2a$10$NnkytOyN6DlR9YaopcjEuOMlUAA1nNo6fdckQJEyb4xHKY17yU2Wi','Sidor9','Sidorov9',1),
  ('sidor10@gmail.com','$2a$10$NnkytOyN6DlR9YaopcjEuOMlUAA1nNo6fdckQJEyb4xHKY17yU2Wi','Sidor10','Sidorov10',1),
  ('student1@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry','Student1','Student1',1),
  ('student2@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry','Student2','Student2',1),
  ('student3@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry','Student3','Student3',1),
  ('student4@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry','Student4','Student4',1),
  ('student5@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry','Student5','Student5',1),
  ('student6@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry','Student6','Student6',1),
  ('student7@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry','Student7','Student7',1),
  ('student8@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry','Student8','Student8',1),
  ('student9@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry','Student9','Student9',1),
  ('student10@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry','Student10','Student10',1),
  ('student11@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry','Student11','Student11',1),
  ('student12@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry','Student12','Student12',1),
  ('student13@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry','Student13','Student13',1),
  ('student14@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry','Student14','Student14',1),
  ('student15@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry','Student15','Student15',1),
  ('student16@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry','Student16','Student16',1),
  ('student17@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry','Student17','Student17',1),
  ('student18@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry','Student18','Student18',1),
  ('student19@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry','Student19','Student19',1),
  ('student20@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry','Student20','Student20',1),
  ('student21@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry','Student21','Student21',1),
  ('student22@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry','Student22','Student22',1),
  ('student23@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry','Student23','Student23',1),
  ('student24@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry','Student24','Student24',1),
  ('student25@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry','Student25','Student25',1),
  ('student26@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry','Student26','Student26',1),
  ('student27@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry','Student27','Student27',1),
  ('student28@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry','Student28','Student28',1),
  ('student29@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry','Student29','Student29',1),
  ('student30@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry','Student30','Student30',1);

INSERT INTO System_User_Role (System_User_ID, Role_ID) VALUES
  (1, (SELECT Role_ID FROM Role WHERE Name='ROLE_ADMIN')),
  (1, (SELECT Role_ID FROM Role WHERE Name='ROLE_DEV')),
  (2, (SELECT Role_ID FROM Role WHERE Name='ROLE_ADMIN')),
  (2, (SELECT Role_ID FROM Role WHERE Name='ROLE_DEV')),
  (3, (SELECT Role_ID FROM Role WHERE Name='ROLE_ADMIN')),
  (3, (SELECT Role_ID FROM Role WHERE Name='ROLE_DEV')),
  (4, (SELECT Role_ID FROM Role WHERE Name='ROLE_ADMIN')),
  (4, (SELECT Role_ID FROM Role WHERE Name='ROLE_BA')),
  (5, (SELECT Role_ID FROM Role WHERE Name='ROLE_BA')),
  (6, (SELECT Role_ID FROM Role WHERE Name='ROLE_BA')),
  (7, (SELECT Role_ID FROM Role WHERE Name='ROLE_BA')),
  (8, (SELECT Role_ID FROM Role WHERE Name='ROLE_BA')),
  (9, (SELECT Role_ID FROM Role WHERE Name='ROLE_BA')),
  (10, (SELECT Role_ID FROM Role WHERE Name='ROLE_BA')),
  (11, (SELECT Role_ID FROM Role WHERE Name='ROLE_BA')),
  (12, (SELECT Role_ID FROM Role WHERE Name='ROLE_BA')),
  (13, (SELECT Role_ID FROM Role WHERE Name='ROLE_BA')),
  (14, (SELECT Role_ID FROM Role WHERE Name='ROLE_BA')),
  (15, (SELECT Role_ID FROM Role WHERE Name='ROLE_DEV')),
  (16, (SELECT Role_ID FROM Role WHERE Name='ROLE_DEV')),
  (17, (SELECT Role_ID FROM Role WHERE Name='ROLE_DEV')),
  (18, (SELECT Role_ID FROM Role WHERE Name='ROLE_DEV')),
  (19, (SELECT Role_ID FROM Role WHERE Name='ROLE_DEV')),
  (20, (SELECT Role_ID FROM Role WHERE Name='ROLE_DEV')),
  (21, (SELECT Role_ID FROM Role WHERE Name='ROLE_DEV')),
  (22, (SELECT Role_ID FROM Role WHERE Name='ROLE_DEV')),
  (23, (SELECT Role_ID FROM Role WHERE Name='ROLE_DEV')),
  (24, (SELECT Role_ID FROM Role WHERE Name='ROLE_DEV')),
  (25, (SELECT Role_ID FROM Role WHERE Name='ROLE_HR')),
  (26, (SELECT Role_ID FROM Role WHERE Name='ROLE_HR')),
  (27, (SELECT Role_ID FROM Role WHERE Name='ROLE_HR')),
  (28, (SELECT Role_ID FROM Role WHERE Name='ROLE_HR')),
  (29, (SELECT Role_ID FROM Role WHERE Name='ROLE_HR')),
  (30, (SELECT Role_ID FROM Role WHERE Name='ROLE_HR')),
  (31, (SELECT Role_ID FROM Role WHERE Name='ROLE_HR')),
  (32, (SELECT Role_ID FROM Role WHERE Name='ROLE_HR')),
  (33, (SELECT Role_ID FROM Role WHERE Name='ROLE_HR')),
  (34, (SELECT Role_ID FROM Role WHERE Name='ROLE_HR')),
  (35, (SELECT Role_ID FROM Role WHERE Name='ROLE_STUDENT')),
  (36, (SELECT Role_ID FROM Role WHERE Name='ROLE_STUDENT')),
  (37, (SELECT Role_ID FROM Role WHERE Name='ROLE_STUDENT')),
  (38, (SELECT Role_ID FROM Role WHERE Name='ROLE_STUDENT')),
  (39, (SELECT Role_ID FROM Role WHERE Name='ROLE_STUDENT')),
  (40, (SELECT Role_ID FROM Role WHERE Name='ROLE_STUDENT')),
  (41, (SELECT Role_ID FROM Role WHERE Name='ROLE_STUDENT')),
  (42, (SELECT Role_ID FROM Role WHERE Name='ROLE_STUDENT')),
  (43, (SELECT Role_ID FROM Role WHERE Name='ROLE_STUDENT')),
  (44, (SELECT Role_ID FROM Role WHERE Name='ROLE_STUDENT')),
  (45, (SELECT Role_ID FROM Role WHERE Name='ROLE_STUDENT')),
  (46, (SELECT Role_ID FROM Role WHERE Name='ROLE_STUDENT')),
  (47, (SELECT Role_ID FROM Role WHERE Name='ROLE_STUDENT')),
  (48, (SELECT Role_ID FROM Role WHERE Name='ROLE_STUDENT')),
  (49, (SELECT Role_ID FROM Role WHERE Name='ROLE_STUDENT')),
  (50, (SELECT Role_ID FROM Role WHERE Name='ROLE_STUDENT')),
  (51, (SELECT Role_ID FROM Role WHERE Name='ROLE_STUDENT')),
  (52, (SELECT Role_ID FROM Role WHERE Name='ROLE_STUDENT')),
  (53, (SELECT Role_ID FROM Role WHERE Name='ROLE_STUDENT')),
  (54, (SELECT Role_ID FROM Role WHERE Name='ROLE_STUDENT')),
  (55, (SELECT Role_ID FROM Role WHERE Name='ROLE_STUDENT')),
  (56, (SELECT Role_ID FROM Role WHERE Name='ROLE_STUDENT')),
  (57, (SELECT Role_ID FROM Role WHERE Name='ROLE_STUDENT')),
  (58, (SELECT Role_ID FROM Role WHERE Name='ROLE_STUDENT')),
  (59, (SELECT Role_ID FROM Role WHERE Name='ROLE_STUDENT')),
  (60, (SELECT Role_ID FROM Role WHERE Name='ROLE_STUDENT')),
  (61, (SELECT Role_ID FROM Role WHERE Name='ROLE_STUDENT')),
  (62, (SELECT Role_ID FROM Role WHERE Name='ROLE_STUDENT')),
  (63, (SELECT Role_ID FROM Role WHERE Name='ROLE_STUDENT')),
  (64, (SELECT Role_ID FROM Role WHERE Name='ROLE_STUDENT'));

INSERT INTO CES_Status (Name) VALUES
  ('Pending'),
  ('RegistrationOngoing'),
  ('PostRegistration'),
  ('InterviewingOngoing'),
  ('PostInterviewing'),
  ('Closed');

INSERT INTO Course_Enrollment_Session (Year, Start_Registration_Date, End_Registration_Date,
                                       Quota, CES_Status_ID, Reminders, Interviewing_Time_Person, Interviewing_Time_Day) VALUES
  (2016, CURRENT_DATE, CURRENT_DATE+5,100,1,
   72,10,4);

INSERT INTO Interviewer_Participation (System_User_ID,CES_ID) VALUES
  (1,1),
  (5,1),
  (10,1),
  (15,1),
  (20,1),
  (25,1),
  (30,1);

INSERT INTO Application(System_User_ID, CES_ID) VALUES
  (35,1),
  (36,1),
  (37,1);

/*INSERT INTO interviewee (application_id, interview_time) VALUES
  (1,'2001-09-28 03:00:00'),
  (2,'2001-09-28 03:00:00'),
  (3,'2001-09-28 03:00:00'),
  (4,'2001-09-28 03:00:00'),
  (5,'2001-09-28 03:00:00'),
  (6,'2001-09-28 03:00:00'),
  (7,'2001-09-28 03:00:00'),
  (8,'2001-09-28 03:00:00');

INSERT INTO Feedback (Score, Comment, Interviewer_ID) VALUES
  (75, 'Good', 15),
  (70, 'Good', 25),
  (80, 'Good', 16),
  (50, 'Medium', 26),
  (75, 'Good', 17),
  (25, 'Bad', 27),
  (30, 'Bad', 18),
  (70, 'Good', 28),
  (75, 'Good', 19),
  (30, 'Bad', 29),
  (75, 'Good', 20),
  (30, 'Bad', 30),
  (80, 'Good', 21),
  (20, 'Bad', 31),
  (65, 'Medium', 22),
  (90, 'Very good', 32),
  (85, 'Good', 23),
  (45, 'Medium', 33),
  (65, 'Medium', 24),
  (80, 'Good', 34),
  (95, 'Very good', 15),
  (40, 'Bad', 25);*/

INSERT INTO Field_Type (Name) VALUES
  ('number'),
  ('text'),
  ('textarea'),
  ('select'),
  ('checkbox'),
  ('radio'),
  ('tel'),
  ('date');

INSERT INTO List (Name) VALUES
  ('Marks'),
  ('Interests'),
  ('Information'),
  ('University');

INSERT INTO List_Value (List_ID, Value_Text) VALUES
  ((SELECT List_ID FROM List WHERE Name = 'Marks'),'0'),
  ((SELECT List_ID FROM List WHERE Name = 'Marks'),'1'),
  ((SELECT List_ID FROM List WHERE Name = 'Marks'),'2'),
  ((SELECT List_ID FROM List WHERE Name = 'Marks'),'3'),
  ((SELECT List_ID FROM List WHERE Name = 'Marks'),'4'),
  ((SELECT List_ID FROM List WHERE Name = 'Marks'),'5'),
  ((SELECT List_ID FROM List WHERE Name = 'Marks'),'6'),
  ((SELECT List_ID FROM List WHERE Name = 'Marks'),'7'),
  ((SELECT List_ID FROM List WHERE Name = 'Marks'),'8'),
  ((SELECT List_ID FROM List WHERE Name = 'Marks'),'9'),
  ((SELECT List_ID FROM List WHERE Name = 'Marks'),'10');

INSERT INTO List_Value (List_ID, Value_Text) VALUES
  ((SELECT List_ID FROM List WHERE Name = 'Interests'),'Research'),
  ((SELECT List_ID FROM List WHERE Name = 'Interests'),'Practice'),
  ((SELECT List_ID FROM List WHERE Name = 'Interests'),'New knowledge'),
  ((SELECT List_ID FROM List WHERE Name = 'Interests'),'Work in NC');

INSERT INTO List_Value (List_ID, Value_Text) VALUES
  ((SELECT List_ID FROM List WHERE Name = 'Information'),'VK'),
  ((SELECT List_ID FROM List WHERE Name = 'Information'),'Facebook'),
  ((SELECT List_ID FROM List WHERE Name = 'Information'),'Our site'),
  ((SELECT List_ID FROM List WHERE Name = 'Information'),'Other people'),
  ((SELECT List_ID FROM List WHERE Name = 'Information'),'TV');

INSERT INTO List_Value (List_ID, Value_Text) VALUES
  ((SELECT List_ID FROM List WHERE Name = 'University'),'KPI'),
  ((SELECT List_ID FROM List WHERE Name = 'University'),'KNU'),
  ((SELECT List_ID FROM List WHERE Name = 'University'),'NAUKMA');



INSERT INTO Field (Name, Field_Type_ID, Multiple_Choice, Order_Num, List_ID) VALUES
  ('Sql level', (SELECT Field_Type_ID FROM Field_Type WHERE Name = 'radio'), false, 1, (SELECT List_ID FROM List WHERE Name = 'Marks'));
INSERT INTO CES_Field (CES_ID, Field_ID) VALUES ((SELECT currval('course_enrollment_session_ces_id_seq')), (SELECT currval('field_field_id_seq')));

INSERT INTO Field (Name, Field_Type_ID, Multiple_Choice, Order_Num, List_ID) VALUES
  ('Java level', (SELECT Field_Type_ID FROM Field_Type WHERE Name = 'radio'), false, 2, (SELECT List_ID FROM List WHERE Name = 'Marks'));
INSERT INTO CES_Field (CES_ID, Field_ID) VALUES ((SELECT currval('course_enrollment_session_ces_id_seq')), (SELECT currval('field_field_id_seq')));

INSERT INTO Field (Name, Field_Type_ID, Multiple_Choice, Order_Num, List_ID) VALUES
  ('Your interests', (SELECT Field_Type_ID FROM Field_Type WHERE Name = 'checkbox'), true, 3,(SELECT List_ID FROM List WHERE Name = 'Interests'));
INSERT INTO CES_Field (CES_ID, Field_ID) VALUES ((SELECT currval('course_enrollment_session_ces_id_seq')), (SELECT currval('field_field_id_seq')));

INSERT INTO Field (Name, Field_Type_ID, Multiple_Choice, Order_Num, List_ID) VALUES
  ('Where have you found information?', (SELECT Field_Type_ID FROM Field_Type WHERE Name = 'radio'), false, 4, (SELECT List_ID FROM List WHERE Name = 'Information'));
INSERT INTO CES_Field (CES_ID, Field_ID) VALUES ((SELECT currval('course_enrollment_session_ces_id_seq')), (SELECT currval('field_field_id_seq')));

INSERT INTO Field (Name, Field_Type_ID, Multiple_Choice, Order_Num, List_ID) VALUES
  ('Phone number', (SELECT Field_Type_ID FROM Field_Type WHERE Name = 'tel'), false, 5, NULL);
INSERT INTO CES_Field (CES_ID, Field_ID) VALUES ((SELECT currval('course_enrollment_session_ces_id_seq')), (SELECT currval('field_field_id_seq')));

INSERT INTO Field (Name, Field_Type_ID, Multiple_Choice, Order_Num, List_ID) VALUES
  ('University', (SELECT Field_Type_ID FROM Field_Type WHERE Name = 'select'), false, 6, (SELECT List_ID FROM List WHERE Name = 'University'));
INSERT INTO CES_Field (CES_ID, Field_ID) VALUES ((SELECT currval('course_enrollment_session_ces_id_seq')), (SELECT currval('field_field_id_seq')));

INSERT INTO Field (Name, Field_Type_ID, Multiple_Choice, Order_Num, List_ID) VALUES
  ('Text field', (SELECT Field_Type_ID FROM Field_Type WHERE Name = 'text'), false, 7, NULL);
INSERT INTO CES_Field (CES_ID, Field_ID) VALUES ((SELECT currval('course_enrollment_session_ces_id_seq')), (SELECT currval('field_field_id_seq')));

INSERT INTO Field (Name, Field_Type_ID, Multiple_Choice, Order_Num, List_ID) VALUES
  ('Kak dela?', (SELECT Field_Type_ID FROM Field_Type WHERE Name = 'textarea'), false, 8, NULL);
INSERT INTO CES_Field (CES_ID, Field_ID) VALUES ((SELECT currval('course_enrollment_session_ces_id_seq')), (SELECT currval('field_field_id_seq')));

INSERT INTO Field_Value (Field_ID, Application_ID, Value_Text, Value_Double, Value_Date, List_Value_ID) VALUES
  (1,1,
   NULL,NULL,NULL, (SELECT List_Value_ID FROM List_Value lv JOIN List l on lv.List_ID = l.List_ID WHERE (l.Name = 'Marks' AND lv.Value_Text = '8'))),

  (2,1,
   NULL,NULL,NULL, (SELECT List_Value_ID FROM List_Value lv JOIN List l on lv.List_ID = l.List_ID WHERE (l.Name = 'Marks' AND lv.Value_Text = '5'))),

  (3,1,
   NULL,NULL,NULL, (SELECT List_Value_ID FROM List_Value lv JOIN List l on lv.List_ID = l.List_ID WHERE (l.Name = 'Interests' AND lv.Value_Text = 'Research'))),

  (3,1,
   NULL,NULL,NULL, (SELECT List_Value_ID FROM List_Value lv JOIN List l on lv.List_ID = l.List_ID WHERE (l.Name = 'Interests' AND lv.Value_Text = 'Practice'))),

  (4,1,
   NULL,NULL,NULL, (SELECT List_Value_ID FROM List_Value lv JOIN List l on lv.List_ID = l.List_ID WHERE (l.Name = 'Information' AND lv.Value_Text = 'VK'))),

  (6,1,
   NULL,NULL,NULL, (SELECT List_Value_ID FROM List_Value lv JOIN List l on lv.List_ID = l.List_ID WHERE (l.Name = 'University' AND lv.Value_Text = 'KPI'))),

  (7,1,
   'Hello',NULL,NULL, NULL),

  (5,1,
   '12345',NULL,NULL, NULL),

  (8,1,
   'Very goooood',NULL,NULL, NULL);

INSERT INTO Field_Value (Field_ID, Application_ID, Value_Text, Value_Double, Value_Date, List_Value_ID) VALUES
  (1,2,
   NULL,NULL,NULL, (SELECT List_Value_ID FROM List_Value lv JOIN List l on lv.List_ID = l.List_ID WHERE (l.Name = 'Marks' AND lv.Value_Text = '7'))),

  (2,2,
   NULL,NULL,NULL, (SELECT List_Value_ID FROM List_Value lv JOIN List l on lv.List_ID = l.List_ID WHERE (l.Name = 'Marks' AND lv.Value_Text = '7'))),

  (3,2,
   NULL,NULL,NULL, (SELECT List_Value_ID FROM List_Value lv JOIN List l on lv.List_ID = l.List_ID WHERE (l.Name = 'Interests' AND lv.Value_Text = 'Research'))),

  (3,2,
   NULL,NULL,NULL, (SELECT List_Value_ID FROM List_Value lv JOIN List l on lv.List_ID = l.List_ID WHERE (l.Name = 'Interests' AND lv.Value_Text = 'Practice'))),

  (4,2,
   NULL,NULL,NULL, (SELECT List_Value_ID FROM List_Value lv JOIN List l on lv.List_ID = l.List_ID WHERE (l.Name = 'Information' AND lv.Value_Text = 'VK'))),

  (6,2,
   NULL,NULL,NULL, (SELECT List_Value_ID FROM List_Value lv JOIN List l on lv.List_ID = l.List_ID WHERE (l.Name = 'University' AND lv.Value_Text = 'KPI'))),

  (7,2,
   'Hi',NULL,NULL, NULL),

  (5,2,
   '333333',NULL,NULL, NULL),

  (8,2,
   'Very medium',NULL,NULL, NULL);

INSERT INTO Field_Value (Field_ID, Application_ID, Value_Text, Value_Double, Value_Date, List_Value_ID) VALUES
  (1,3,
   NULL,NULL,NULL, (SELECT List_Value_ID FROM List_Value lv JOIN List l on lv.List_ID = l.List_ID WHERE (l.Name = 'Marks' AND lv.Value_Text = '1'))),

  (2,3,
   NULL,NULL,NULL, (SELECT List_Value_ID FROM List_Value lv JOIN List l on lv.List_ID = l.List_ID WHERE (l.Name = 'Marks' AND lv.Value_Text = '1'))),

  (3,3,
   NULL,NULL,NULL, (SELECT List_Value_ID FROM List_Value lv JOIN List l on lv.List_ID = l.List_ID WHERE (l.Name = 'Interests' AND lv.Value_Text = 'Research'))),

  (3,3,
   NULL,NULL,NULL, (SELECT List_Value_ID FROM List_Value lv JOIN List l on lv.List_ID = l.List_ID WHERE (l.Name = 'Interests' AND lv.Value_Text = 'Practice'))),

  (4,3,
   NULL,NULL,NULL, (SELECT List_Value_ID FROM List_Value lv JOIN List l on lv.List_ID = l.List_ID WHERE (l.Name = 'Information' AND lv.Value_Text = 'VK'))),

  (6,3,
   NULL,NULL,NULL, (SELECT List_Value_ID FROM List_Value lv JOIN List l on lv.List_ID = l.List_ID WHERE (l.Name = 'University' AND lv.Value_Text = 'KNU'))),

  (7,3,
   'Goodbye',NULL,NULL, NULL),

  (5,3,
   '6666666',NULL,NULL, NULL),

  (8,3,
   'Very baaaad',NULL,NULL, NULL);

INSERT INTO email_template (head_template, body_template) VALUES
  ('NetCracker', 'Доброго дня, $name $surname!

Повідомляємо, що у цьому році, на жаль, Вас не запрошено на курси $courseType у НЦ НТУУ "КПІ" NetCracker.
Спробуйте свої сили у наступному році!

З повагою, NetCracker.

Якщо ви отримали цього листа випадково та не розумієте, про що йдеться, будь ласка, не реагуйте на це повідомлення.'),

  ('Запрошення на курси NetCracker', 'Вітаємо, $name $surname!

Повідомляємо, що Вас запрошено на курси $courseType у НЦ НТУУ "КПІ" NetCracker.
За додатковою інформацією звертатися: $contactStudent.

З повагою, NetCracker.

Якщо ви отримали цього листа випадково та не розумієте, про що йдеться, будь ласка, не реагуйте на це повідомлення.'),
  ('Нагадування про співбесіду у NetCracker', 'Доброго дня, $name $surname!

Нагадуємо, що $date о $hours:$minutes Ви проводите співбесіду на курси $courseType, що проходитиме у $location (подивитися на карті: $googleMaps).
За додатковою інформацією звертатися: $contactInterviewers.

З повагою, NetCracker.

Якщо ви отримали цього листа випадково та не розумієте, про що йдеться, будь ласка, не реагуйте на це повідомлення.'),
  ('Запрошення на співбесіду у NetCracker','Вітаємо, $name $surname!

Нагадуємо, що $date о $hours:$minutes у Вас співбесіда на курси $courseType, що проходитиме у $location (подивитися на карті: $googleMaps).
За додатковою інформацією звертатися: $contactStudent.

З повагою, NetCracker.

Якщо ви отримали цього листа випадково та не розумієте, про що йдеться, будь ласка, не реагуйте на це повідомлення.');
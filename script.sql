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

INSERT INTO Role (Name, Description) VALUES
('ROLE_ADMIN', 'Admin of the system'),
('ROLE_STUDENT', 'Student who wants to/takes part in the course'),
('ROLE_HR', 'HR of Netcracker'),
('ROLE_DEV', 'Developer of Netcracker'),
('ROLE_BA', 'BA of Netcracker');


CREATE TABLE System_User_Status (
  System_User_Status_ID serial PRIMARY KEY,
  Name varchar(20) NOT NULL UNIQUE
);

INSERT INTO System_User_Status (Name) VALUES
('Active'),
('Inactive');


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

INSERT INTO System_User (Email, Password, Name, Surname, System_User_Status_ID) VALUES
('ion@gmail.com', '$2a$10$U2HGciX6QWTwus5SEGVKgeCu/OHH7DXSwwBVQaGjO7o.mKyC8J4pq','Ion','Ionets', (SELECT System_User_Status_ID FROM System_User_Status WHERE Name='Active')),
('rom@gmail.com', '$2a$10$lS6mQkw2ejU.JZjgIFVQAeFemX0Dg9gO9k5Y.AkHW6VeckwNL3Qkm','Roman','Andriichuk',(SELECT System_User_Status_ID FROM System_User_Status WHERE Name='Active')),
('sasha@gmail.com', '$2a$10$bflfk70LoK2kf4DRA2TXo.M43YjvMKFYfEKgwaEqq2rnEleXBgOI.','Sasha','Beskorovaynaya',(SELECT System_User_Status_ID FROM System_User_Status WHERE Name='Active')),
('kirill@gmail.com', '$2a$10$9uck3uR.cTwo9RNye0IcF.Us09QlaOGetAcXxj0QOfdvrxwUVm3ES','Kirill','Tumanov',(SELECT System_User_Status_ID FROM System_User_Status WHERE Name='Active')),
('hr@gmail.com', '$2a$10$c6ecTMnSeEgrLZxKOaIR1eFIQ5wUkNNWIw0kj1rowiR7gUDUkZFnG','HR','HRets',(SELECT System_User_Status_ID FROM System_User_Status WHERE Name='Active')),
('student@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry','Student','Studentets',(SELECT System_User_Status_ID FROM System_User_Status WHERE Name='Active'));

INSERT INTO System_User_Role (System_User_ID, Role_ID) VALUES
((SELECT System_User_ID FROM System_User WHERE Email='ion@gmail.com'), (SELECT Role_ID FROM Role WHERE Name='ROLE_ADMIN')),
((SELECT System_User_ID FROM System_User WHERE Email='ion@gmail.com'), (SELECT Role_ID FROM Role WHERE Name='ROLE_DEV')),
((SELECT System_User_ID FROM System_User WHERE Email='rom@gmail.com'), (SELECT Role_ID FROM Role WHERE Name='ROLE_DEV')),
((SELECT System_User_ID FROM System_User WHERE Email='sasha@gmail.com'), (SELECT Role_ID FROM Role WHERE Name='ROLE_DEV')),
((SELECT System_User_ID FROM System_User WHERE Email='kirill@gmail.com'), (SELECT Role_ID FROM Role WHERE Name='ROLE_BA')),
((SELECT System_User_ID FROM System_User WHERE Email='hr@gmail.com'), (SELECT Role_ID FROM Role WHERE Name='ROLE_HR')),
((SELECT System_User_ID FROM System_User WHERE Email='student@gmail.com'), (SELECT Role_ID FROM Role WHERE Name='ROLE_STUDENT'));


CREATE TABLE Feedback (
  Feedback_ID serial PRIMARY KEY,
  Score int NOT NULL,
  Comment text NOT NULL,
  Interviewer_ID int NOT NULL references System_User(System_User_id) ON DELETE RESTRICT ON UPDATE CASCADE
);

INSERT INTO Feedback (Score, Comment, Interviewer_ID) VALUES
(75, 'Norm', (SELECT System_User_ID FROM System_User WHERE Email='ion@gmail.com')),
(30, 'Not OK', (SELECT System_User_ID FROM System_User WHERE Email='hr@gmail.com'));

CREATE TABLE Email_Template (
  Email_Template_ID serial PRIMARY KEY,
  Body_Template text NOT NULL,
  Head_Template text NOT NULL
);

CREATE TABLE CES_Status (
  CES_Status_ID serial PRIMARY KEY,
  Name varchar(20) NOT NULL
);

INSERT INTO CES_Status (Name) VALUES
('Active'),
('Closed'),
('Pending');

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
  Interviewing_Time_Day int NOT NULL CHECK (Interviewing_Time_Day > 0)
);

INSERT INTO Course_Enrollment_Session (Year, Start_Registration_Date, End_Registration_Date, Start_Interviewing_Date,
End_Interviewing_Date, Quota, CES_Status_ID, Reminders, Interviewing_Time_Person, Interviewing_Time_Day) VALUES
(2016, CURRENT_DATE, CURRENT_DATE+5,CURRENT_DATE+10,CURRENT_DATE+15,100,(SELECT CES_Status_ID FROM CES_Status WHERE Name='Active'),
72,10,100);

CREATE TABLE Interviewer_Participation (
  System_User_ID int NOT NULL references System_User(System_User_ID)  ON DELETE RESTRICT ON UPDATE CASCADE,
  CES_ID int NOT NULL references Course_Enrollment_Session(CES_ID)  ON DELETE RESTRICT ON UPDATE CASCADE,
  PRIMARY KEY(System_User_ID, CES_ID)
);

INSERT INTO Interviewer_Participation (System_User_ID,CES_ID) VALUES
((SELECT System_User_ID FROM System_User WHERE Email='ion@gmail.com'),(SELECT currval('course_enrollment_session_ces_id_seq'))),
((SELECT System_User_ID FROM System_User WHERE Email='hr@gmail.com'),(SELECT currval('course_enrollment_session_ces_id_seq')));

CREATE TABLE Application (
  Application_ID serial PRIMARY KEY,
  System_User_ID int NOT NULL references System_User(System_User_ID)  ON DELETE RESTRICT ON UPDATE CASCADE,
  CES_ID int NOT NULL references Course_Enrollment_Session(CES_ID) ON DELETE RESTRICT ON UPDATE CASCADE,
  rejected bool,
  UNIQUE(System_User_ID, CES_ID)
);

INSERT INTO Application(System_User_ID, CES_ID) VALUES
((SELECT System_User_ID FROM System_User WHERE Email='student@gmail.com'),(SELECT currval('course_enrollment_session_ces_id_seq')));

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
  ((SELECT Field_ID FROM Field WHERE Name = 'Sql level'),
   (SELECT a.Application_ID FROM Application a JOIN System_User u ON a.System_User_ID = u.System_User_ID WHERE u.Email = 'student@gmail.com'),
   NULL,NULL,NULL, (SELECT List_Value_ID FROM List_Value lv JOIN List l on lv.List_ID = l.List_ID WHERE (l.Name = 'Marks' AND lv.Value_Text = '8'))),

  ((SELECT Field_ID FROM Field WHERE Name = 'Java level'),
   (SELECT a.Application_ID FROM Application a JOIN System_User u ON a.System_User_ID = u.System_User_ID WHERE u.Email = 'student@gmail.com'),
   NULL,NULL,NULL, (SELECT List_Value_ID FROM List_Value lv JOIN List l on lv.List_ID = l.List_ID WHERE (l.Name = 'Marks' AND lv.Value_Text = '5'))),

  ((SELECT Field_ID FROM Field WHERE Name = 'Your interests'),
   (SELECT a.Application_ID FROM Application a JOIN System_User u ON a.System_User_ID = u.System_User_ID WHERE u.Email = 'student@gmail.com'),
   NULL,NULL,NULL, (SELECT List_Value_ID FROM List_Value lv JOIN List l on lv.List_ID = l.List_ID WHERE (l.Name = 'Interests' AND lv.Value_Text = 'Research'))),

  ((SELECT Field_ID FROM Field WHERE Name = 'Your interests'),
   (SELECT a.Application_ID FROM Application a JOIN System_User u ON a.System_User_ID = u.System_User_ID WHERE u.Email = 'student@gmail.com'),
   NULL,NULL,NULL, (SELECT List_Value_ID FROM List_Value lv JOIN List l on lv.List_ID = l.List_ID WHERE (l.Name = 'Interests' AND lv.Value_Text = 'Practice'))),


  ((SELECT Field_ID FROM Field WHERE Name = 'Where have you found information?'),
   (SELECT a.Application_ID FROM Application a JOIN System_User u ON a.System_User_ID = u.System_User_ID WHERE u.Email = 'student@gmail.com'),
   NULL,NULL,NULL, (SELECT List_Value_ID FROM List_Value lv JOIN List l on lv.List_ID = l.List_ID WHERE (l.Name = 'Information' AND lv.Value_Text = 'VK'))),

  ((SELECT Field_ID FROM Field WHERE Name = 'University'),
   (SELECT a.Application_ID FROM Application a JOIN System_User u ON a.System_User_ID = u.System_User_ID WHERE u.Email = 'student@gmail.com'),
   NULL,NULL,NULL, (SELECT List_Value_ID FROM List_Value lv JOIN List l on lv.List_ID = l.List_ID WHERE (l.Name = 'University' AND lv.Value_Text = 'KPI'))),

  ((SELECT Field_ID FROM Field WHERE Name = 'Text field'),
   (SELECT a.Application_ID FROM Application a JOIN System_User u ON a.System_User_ID = u.System_User_ID WHERE u.Email = 'student@gmail.com'),
   'Hello',NULL,NULL, NULL),

  ((SELECT Field_ID FROM Field WHERE Name = 'Phone number'),
   (SELECT a.Application_ID FROM Application a JOIN System_User u ON a.System_User_ID = u.System_User_ID WHERE u.Email = 'student@gmail.com'),
   '12345',NULL,NULL, NULL),

  ((SELECT Field_ID FROM Field WHERE Name = 'Kak dela?'),
   (SELECT a.Application_ID FROM Application a JOIN System_User u ON a.System_User_ID = u.System_User_ID WHERE u.Email = 'student@gmail.com'),
   'Very goooood',NULL,NULL, NULL);
--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.12
-- Dumped by pg_dump version 9.5.1

-- Started on 2016-05-31 11:32:47

DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 11756)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2156 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 190 (class 1259 OID 27417)
-- Name: application; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE application (
    application_id integer NOT NULL,
    system_user_id integer NOT NULL,
    ces_id integer NOT NULL,
    rejected boolean DEFAULT false
);


ALTER TABLE application OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 27415)
-- Name: application_application_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE application_application_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE application_application_id_seq OWNER TO postgres;

--
-- TOC entry 2157 (class 0 OID 0)
-- Dependencies: 189
-- Name: application_application_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE application_application_id_seq OWNED BY application.application_id;


--
-- TOC entry 200 (class 1259 OID 27509)
-- Name: ces_field; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE ces_field (
    ces_id integer NOT NULL,
    field_id integer NOT NULL
);


ALTER TABLE ces_field OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 27374)
-- Name: ces_status; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE ces_status (
    ces_status_id integer NOT NULL,
    name character varying(20) NOT NULL
);


ALTER TABLE ces_status OWNER TO postgres;

--
-- TOC entry 184 (class 1259 OID 27372)
-- Name: ces_status_ces_status_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE ces_status_ces_status_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ces_status_ces_status_id_seq OWNER TO postgres;

--
-- TOC entry 2158 (class 0 OID 0)
-- Dependencies: 184
-- Name: ces_status_ces_status_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE ces_status_ces_status_id_seq OWNED BY ces_status.ces_status_id;


--
-- TOC entry 187 (class 1259 OID 27382)
-- Name: course_enrollment_session; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE course_enrollment_session (
    ces_id integer NOT NULL,
    year integer NOT NULL,
    start_registration_date date NOT NULL,
    end_registration_date date,
    start_interviewing_date date,
    end_interviewing_date date,
    quota integer NOT NULL,
    ces_status_id integer NOT NULL,
    reminders integer NOT NULL,
    interviewing_time_person integer NOT NULL,
    interviewing_time_day integer NOT NULL,
    CONSTRAINT course_enrollment_session_check CHECK ((end_registration_date > start_registration_date)),
    CONSTRAINT course_enrollment_session_check1 CHECK ((start_interviewing_date > end_registration_date)),
    CONSTRAINT course_enrollment_session_check2 CHECK ((end_interviewing_date > start_interviewing_date)),
    CONSTRAINT course_enrollment_session_interviewing_time_day_check CHECK ((interviewing_time_day > 0)),
    CONSTRAINT course_enrollment_session_interviewing_time_person_check CHECK ((interviewing_time_person > 0)),
    CONSTRAINT course_enrollment_session_quota_check CHECK ((quota > 0)),
    CONSTRAINT course_enrollment_session_reminders_check CHECK ((reminders > 0))
);


ALTER TABLE course_enrollment_session OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 27380)
-- Name: course_enrollment_session_ces_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE course_enrollment_session_ces_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE course_enrollment_session_ces_id_seq OWNER TO postgres;

--
-- TOC entry 2159 (class 0 OID 0)
-- Dependencies: 186
-- Name: course_enrollment_session_ces_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE course_enrollment_session_ces_id_seq OWNED BY course_enrollment_session.ces_id;


--
-- TOC entry 183 (class 1259 OID 27363)
-- Name: email_template; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE email_template (
    email_template_id integer NOT NULL,
    body_template text NOT NULL,
    head_template text NOT NULL
);


ALTER TABLE email_template OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 27361)
-- Name: email_template_email_template_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE email_template_email_template_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE email_template_email_template_id_seq OWNER TO postgres;

--
-- TOC entry 2160 (class 0 OID 0)
-- Dependencies: 182
-- Name: email_template_email_template_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE email_template_email_template_id_seq OWNED BY email_template.email_template_id;


--
-- TOC entry 181 (class 1259 OID 27347)
-- Name: feedback; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE feedback (
    feedback_id integer NOT NULL,
    score integer NOT NULL,
    comment text NOT NULL,
    interviewer_id integer NOT NULL
);


ALTER TABLE feedback OWNER TO postgres;

--
-- TOC entry 180 (class 1259 OID 27345)
-- Name: feedback_feedback_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE feedback_feedback_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE feedback_feedback_id_seq OWNER TO postgres;

--
-- TOC entry 2161 (class 0 OID 0)
-- Dependencies: 180
-- Name: feedback_feedback_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE feedback_feedback_id_seq OWNED BY feedback.feedback_id;


--
-- TOC entry 199 (class 1259 OID 27492)
-- Name: field; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE field (
    field_id integer NOT NULL,
    name character varying(50) NOT NULL,
    field_type_id integer NOT NULL,
    multiple_choice boolean NOT NULL,
    order_num integer NOT NULL,
    list_id integer,
    CONSTRAINT field_order_num_check CHECK ((order_num > 0))
);


ALTER TABLE field OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 27490)
-- Name: field_field_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE field_field_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE field_field_id_seq OWNER TO postgres;

--
-- TOC entry 2162 (class 0 OID 0)
-- Dependencies: 198
-- Name: field_field_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE field_field_id_seq OWNED BY field.field_id;


--
-- TOC entry 197 (class 1259 OID 27482)
-- Name: field_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE field_type (
    field_type_id integer NOT NULL,
    name character varying(20) NOT NULL
);


ALTER TABLE field_type OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 27480)
-- Name: field_type_field_type_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE field_type_field_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE field_type_field_type_id_seq OWNER TO postgres;

--
-- TOC entry 2163 (class 0 OID 0)
-- Dependencies: 196
-- Name: field_type_field_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE field_type_field_type_id_seq OWNED BY field_type.field_type_id;


--
-- TOC entry 201 (class 1259 OID 27524)
-- Name: field_value; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE field_value (
    field_id integer NOT NULL,
    application_id integer NOT NULL,
    value_text text,
    value_double double precision,
    value_date date,
    list_value_id integer,
    CONSTRAINT field_value_check CHECK (((((value_double IS NOT NULL) OR (value_text IS NOT NULL)) OR (value_date IS NOT NULL)) OR (list_value_id IS NOT NULL)))
);


ALTER TABLE field_value OWNER TO postgres;

--
-- TOC entry 191 (class 1259 OID 27436)
-- Name: interviewee; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE interviewee (
    application_id integer NOT NULL,
    interview_time timestamp without time zone NOT NULL,
    special_mark character varying(50),
    dev_feedback_id integer,
    hr_feedback_id integer
);


ALTER TABLE interviewee OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 27400)
-- Name: interviewer_participation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE interviewer_participation (
    system_user_id integer NOT NULL,
    ces_id integer NOT NULL
);


ALTER TABLE interviewer_participation OWNER TO postgres;

--
-- TOC entry 193 (class 1259 OID 27458)
-- Name: list; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE list (
    list_id integer NOT NULL,
    name character varying(20) NOT NULL
);


ALTER TABLE list OWNER TO postgres;

--
-- TOC entry 192 (class 1259 OID 27456)
-- Name: list_list_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE list_list_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE list_list_id_seq OWNER TO postgres;

--
-- TOC entry 2164 (class 0 OID 0)
-- Dependencies: 192
-- Name: list_list_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE list_list_id_seq OWNED BY list.list_id;


--
-- TOC entry 195 (class 1259 OID 27466)
-- Name: list_value; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE list_value (
    list_value_id integer NOT NULL,
    list_id integer NOT NULL,
    value_text text NOT NULL
);


ALTER TABLE list_value OWNER TO postgres;

--
-- TOC entry 194 (class 1259 OID 27464)
-- Name: list_value_list_value_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE list_value_list_value_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE list_value_list_value_id_seq OWNER TO postgres;

--
-- TOC entry 2165 (class 0 OID 0)
-- Dependencies: 194
-- Name: list_value_list_value_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE list_value_list_value_id_seq OWNED BY list_value.list_value_id;


--
-- TOC entry 172 (class 1259 OID 27282)
-- Name: report_template; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE report_template (
    report_id integer NOT NULL,
    query text NOT NULL,
    name character varying(20) NOT NULL
);


ALTER TABLE report_template OWNER TO postgres;

--
-- TOC entry 171 (class 1259 OID 27280)
-- Name: report_template_report_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE report_template_report_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE report_template_report_id_seq OWNER TO postgres;

--
-- TOC entry 2166 (class 0 OID 0)
-- Dependencies: 171
-- Name: report_template_report_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE report_template_report_id_seq OWNED BY report_template.report_id;


--
-- TOC entry 174 (class 1259 OID 27293)
-- Name: role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE role (
    role_id integer NOT NULL,
    name character varying(20) NOT NULL,
    description text NOT NULL
);


ALTER TABLE role OWNER TO postgres;

--
-- TOC entry 173 (class 1259 OID 27291)
-- Name: role_role_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE role_role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE role_role_id_seq OWNER TO postgres;

--
-- TOC entry 2167 (class 0 OID 0)
-- Dependencies: 173
-- Name: role_role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE role_role_id_seq OWNED BY role.role_id;


--
-- TOC entry 178 (class 1259 OID 27316)
-- Name: system_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE system_user (
    system_user_id integer NOT NULL,
    email character varying(100) NOT NULL,
    password character(60) NOT NULL,
    name character varying(30) NOT NULL,
    surname character varying(30) NOT NULL,
    system_user_status_id integer NOT NULL
);


ALTER TABLE system_user OWNER TO postgres;

--
-- TOC entry 179 (class 1259 OID 27330)
-- Name: system_user_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE system_user_role (
    system_user_id integer NOT NULL,
    role_id integer NOT NULL
);


ALTER TABLE system_user_role OWNER TO postgres;

--
-- TOC entry 176 (class 1259 OID 27306)
-- Name: system_user_status; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE system_user_status (
    system_user_status_id integer NOT NULL,
    name character varying(20) NOT NULL
);


ALTER TABLE system_user_status OWNER TO postgres;

--
-- TOC entry 175 (class 1259 OID 27304)
-- Name: system_user_status_system_user_status_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE system_user_status_system_user_status_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE system_user_status_system_user_status_id_seq OWNER TO postgres;

--
-- TOC entry 2168 (class 0 OID 0)
-- Dependencies: 175
-- Name: system_user_status_system_user_status_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE system_user_status_system_user_status_id_seq OWNED BY system_user_status.system_user_status_id;


--
-- TOC entry 177 (class 1259 OID 27314)
-- Name: system_user_system_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE system_user_system_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE system_user_system_user_id_seq OWNER TO postgres;

--
-- TOC entry 2169 (class 0 OID 0)
-- Dependencies: 177
-- Name: system_user_system_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE system_user_system_user_id_seq OWNED BY system_user.system_user_id;


--
-- TOC entry 1940 (class 2604 OID 27420)
-- Name: application_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY application ALTER COLUMN application_id SET DEFAULT nextval('application_application_id_seq'::regclass);


--
-- TOC entry 1931 (class 2604 OID 27377)
-- Name: ces_status_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ces_status ALTER COLUMN ces_status_id SET DEFAULT nextval('ces_status_ces_status_id_seq'::regclass);


--
-- TOC entry 1932 (class 2604 OID 27385)
-- Name: ces_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY course_enrollment_session ALTER COLUMN ces_id SET DEFAULT nextval('course_enrollment_session_ces_id_seq'::regclass);


--
-- TOC entry 1930 (class 2604 OID 27366)
-- Name: email_template_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY email_template ALTER COLUMN email_template_id SET DEFAULT nextval('email_template_email_template_id_seq'::regclass);


--
-- TOC entry 1929 (class 2604 OID 27350)
-- Name: feedback_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY feedback ALTER COLUMN feedback_id SET DEFAULT nextval('feedback_feedback_id_seq'::regclass);


--
-- TOC entry 1945 (class 2604 OID 27495)
-- Name: field_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY field ALTER COLUMN field_id SET DEFAULT nextval('field_field_id_seq'::regclass);


--
-- TOC entry 1944 (class 2604 OID 27485)
-- Name: field_type_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY field_type ALTER COLUMN field_type_id SET DEFAULT nextval('field_type_field_type_id_seq'::regclass);


--
-- TOC entry 1942 (class 2604 OID 27461)
-- Name: list_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY list ALTER COLUMN list_id SET DEFAULT nextval('list_list_id_seq'::regclass);


--
-- TOC entry 1943 (class 2604 OID 27469)
-- Name: list_value_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY list_value ALTER COLUMN list_value_id SET DEFAULT nextval('list_value_list_value_id_seq'::regclass);


--
-- TOC entry 1925 (class 2604 OID 27285)
-- Name: report_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY report_template ALTER COLUMN report_id SET DEFAULT nextval('report_template_report_id_seq'::regclass);


--
-- TOC entry 1926 (class 2604 OID 27296)
-- Name: role_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY role ALTER COLUMN role_id SET DEFAULT nextval('role_role_id_seq'::regclass);


--
-- TOC entry 1928 (class 2604 OID 27319)
-- Name: system_user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY system_user ALTER COLUMN system_user_id SET DEFAULT nextval('system_user_system_user_id_seq'::regclass);


--
-- TOC entry 1927 (class 2604 OID 27309)
-- Name: system_user_status_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY system_user_status ALTER COLUMN system_user_status_id SET DEFAULT nextval('system_user_status_system_user_status_id_seq'::regclass);


--
-- TOC entry 2138 (class 0 OID 27417)
-- Dependencies: 190
-- Data for Name: application; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO application VALUES (1, 35, 1, false);
INSERT INTO application VALUES (8, 35, 3, false);
INSERT INTO application VALUES (9, 72, 3, false);
INSERT INTO application VALUES (2, 36, 1, false);
INSERT INTO application VALUES (3, 37, 1, false);
INSERT INTO application VALUES (5, 44, 1, false);


--
-- TOC entry 2170 (class 0 OID 0)
-- Dependencies: 189
-- Name: application_application_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('application_application_id_seq', 9, true);


--
-- TOC entry 2148 (class 0 OID 27509)
-- Dependencies: 200
-- Data for Name: ces_field; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO ces_field VALUES (1, 1);
INSERT INTO ces_field VALUES (1, 2);
INSERT INTO ces_field VALUES (1, 3);
INSERT INTO ces_field VALUES (1, 4);
INSERT INTO ces_field VALUES (1, 5);
INSERT INTO ces_field VALUES (1, 6);
INSERT INTO ces_field VALUES (1, 7);
INSERT INTO ces_field VALUES (1, 8);
INSERT INTO ces_field VALUES (1, 10);
INSERT INTO ces_field VALUES (1, 11);
INSERT INTO ces_field VALUES (1, 12);
INSERT INTO ces_field VALUES (1, 13);
INSERT INTO ces_field VALUES (3, 10);
INSERT INTO ces_field VALUES (3, 11);
INSERT INTO ces_field VALUES (3, 12);
INSERT INTO ces_field VALUES (3, 13);
INSERT INTO ces_field VALUES (3, 15);
INSERT INTO ces_field VALUES (3, 16);
INSERT INTO ces_field VALUES (3, 17);
INSERT INTO ces_field VALUES (3, 18);
INSERT INTO ces_field VALUES (3, 19);
INSERT INTO ces_field VALUES (3, 20);
INSERT INTO ces_field VALUES (3, 21);
INSERT INTO ces_field VALUES (3, 22);
INSERT INTO ces_field VALUES (3, 23);
INSERT INTO ces_field VALUES (3, 24);
INSERT INTO ces_field VALUES (3, 25);
INSERT INTO ces_field VALUES (3, 27);
INSERT INTO ces_field VALUES (3, 28);
INSERT INTO ces_field VALUES (3, 30);


--
-- TOC entry 2133 (class 0 OID 27374)
-- Dependencies: 185
-- Data for Name: ces_status; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO ces_status VALUES (1, 'Pending');
INSERT INTO ces_status VALUES (2, 'RegistrationOngoing');
INSERT INTO ces_status VALUES (3, 'PostRegistration');
INSERT INTO ces_status VALUES (4, 'InterviewingOngoing');
INSERT INTO ces_status VALUES (5, 'PostInterviewing');
INSERT INTO ces_status VALUES (6, 'Closed');


--
-- TOC entry 2171 (class 0 OID 0)
-- Dependencies: 184
-- Name: ces_status_ces_status_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('ces_status_ces_status_id_seq', 6, true);


--
-- TOC entry 2135 (class 0 OID 27382)
-- Dependencies: 187
-- Data for Name: course_enrollment_session; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO course_enrollment_session VALUES (3, 2016, '2016-06-03', '2016-06-09', NULL, NULL, 200, 3, 10, 10, 10);
INSERT INTO course_enrollment_session VALUES (1, 2016, '2016-05-29', '2016-05-30', '2016-06-09', '2016-06-14', 7, 6, 24, 10, 1);
INSERT INTO course_enrollment_session VALUES (2, 2016, '2016-06-01', '2016-06-02', '2016-06-03', NULL, 12, 6, 13, 12, 6);


--
-- TOC entry 2172 (class 0 OID 0)
-- Dependencies: 186
-- Name: course_enrollment_session_ces_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('course_enrollment_session_ces_id_seq', 3, true);


--
-- TOC entry 2131 (class 0 OID 27363)
-- Dependencies: 183
-- Data for Name: email_template; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO email_template VALUES (35, 'Welcome dear, $name $surname. We are happy to inform you that you''ve successfully participated to our courses!', 'registration');
INSERT INTO email_template VALUES (36, 'Hello $name, $surname. We are happy to inform you that you''re successfully passed interview and we want to see you as our junior.', 'joboffer');
INSERT INTO email_template VALUES (37, 'Dear $name, $surname. We''re sorry but yours level is not enough for passing into our courses. Hope you will come back in next year.', 'rejection');
INSERT INTO email_template VALUES (38, 'Hello $name, $surname. We are happy to inform you that you''re successfully passed interview and we want to see you on our courses.', 'courseapply');


--
-- TOC entry 2173 (class 0 OID 0)
-- Dependencies: 182
-- Name: email_template_email_template_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('email_template_email_template_id_seq', 39, true);


--
-- TOC entry 2129 (class 0 OID 27347)
-- Dependencies: 181
-- Data for Name: feedback; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO feedback VALUES (1, 10, 'Not good', 1);
INSERT INTO feedback VALUES (2, 1, 'Fu', 4);


--
-- TOC entry 2174 (class 0 OID 0)
-- Dependencies: 180
-- Name: feedback_feedback_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('feedback_feedback_id_seq', 2, true);


--
-- TOC entry 2147 (class 0 OID 27492)
-- Dependencies: 199
-- Data for Name: field; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO field VALUES (1, 'Sql level', 6, false, 1, 1);
INSERT INTO field VALUES (2, 'Java level', 6, false, 2, 1);
INSERT INTO field VALUES (3, 'Your interests', 5, true, 3, 2);
INSERT INTO field VALUES (4, 'Where have you found information?', 6, false, 4, 3);
INSERT INTO field VALUES (5, 'Phone number', 7, false, 5, NULL);
INSERT INTO field VALUES (6, 'University', 4, false, 6, 4);
INSERT INTO field VALUES (7, 'Text field', 2, false, 7, NULL);
INSERT INTO field VALUES (8, 'Kak dela?', 3, false, 8, NULL);
INSERT INTO field VALUES (14, 'Faculty and department', 3, false, 1, NULL);
INSERT INTO field VALUES (26, 'Your English level', 4, false, 23, 13);
INSERT INTO field VALUES (10, 'University course', 4, false, 1, 6);
INSERT INTO field VALUES (11, 'Graduation year', 4, false, 2, 7);
INSERT INTO field VALUES (12, 'Faculty and department', 3, false, 3, NULL);
INSERT INTO field VALUES (13, 'Average mark during study', 1, false, 4, NULL);
INSERT INTO field VALUES (15, 'Your interests in training center', 5, true, 5, 8);
INSERT INTO field VALUES (16, 'Your professional interests', 5, true, 6, 9);
INSERT INTO field VALUES (17, 'When are you able to get to work', 6, false, 7, 10);
INSERT INTO field VALUES (18, 'How much hours per week are you able to work', 4, false, 8, 11);
INSERT INTO field VALUES (20, 'Your C++ skills', 1, false, 9, NULL);
INSERT INTO field VALUES (21, 'Your java skills', 1, false, 10, NULL);
INSERT INTO field VALUES (22, 'Your javascript skill', 1, false, 11, NULL);
INSERT INTO field VALUES (23, 'Your SQL skill', 1, false, 12, NULL);
INSERT INTO field VALUES (24, 'Your GUI skills', 1, false, 13, NULL);
INSERT INTO field VALUES (25, 'Your English level', 4, false, 14, 12);
INSERT INTO field VALUES (27, 'Where have you found info about training center', 6, false, 15, 14);
INSERT INTO field VALUES (19, 'Your hobbies and interests', 3, false, 16, NULL);
INSERT INTO field VALUES (28, 'Why should we take you', 3, false, 17, NULL);
INSERT INTO field VALUES (29, 'Тестовый вопрос', 2, false, 18, NULL);
INSERT INTO field VALUES (30, '1325321', 1, false, 18, NULL);


--
-- TOC entry 2175 (class 0 OID 0)
-- Dependencies: 198
-- Name: field_field_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('field_field_id_seq', 30, true);


--
-- TOC entry 2145 (class 0 OID 27482)
-- Dependencies: 197
-- Data for Name: field_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO field_type VALUES (1, 'number');
INSERT INTO field_type VALUES (2, 'text');
INSERT INTO field_type VALUES (3, 'textarea');
INSERT INTO field_type VALUES (4, 'select');
INSERT INTO field_type VALUES (5, 'checkbox');
INSERT INTO field_type VALUES (6, 'radio');
INSERT INTO field_type VALUES (7, 'tel');
INSERT INTO field_type VALUES (8, 'date');


--
-- TOC entry 2176 (class 0 OID 0)
-- Dependencies: 196
-- Name: field_type_field_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('field_type_field_type_id_seq', 8, true);


--
-- TOC entry 2149 (class 0 OID 27524)
-- Dependencies: 201
-- Data for Name: field_value; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO field_value VALUES (1, 1, NULL, NULL, NULL, 9);
INSERT INTO field_value VALUES (2, 1, NULL, NULL, NULL, 6);
INSERT INTO field_value VALUES (3, 1, NULL, NULL, NULL, 12);
INSERT INTO field_value VALUES (3, 1, NULL, NULL, NULL, 13);
INSERT INTO field_value VALUES (4, 1, NULL, NULL, NULL, 16);
INSERT INTO field_value VALUES (6, 1, NULL, NULL, NULL, 21);
INSERT INTO field_value VALUES (7, 1, 'Hello', NULL, NULL, NULL);
INSERT INTO field_value VALUES (5, 1, '12345', NULL, NULL, NULL);
INSERT INTO field_value VALUES (8, 1, 'Very goooood', NULL, NULL, NULL);
INSERT INTO field_value VALUES (1, 2, NULL, NULL, NULL, 8);
INSERT INTO field_value VALUES (2, 2, NULL, NULL, NULL, 8);
INSERT INTO field_value VALUES (3, 2, NULL, NULL, NULL, 12);
INSERT INTO field_value VALUES (3, 2, NULL, NULL, NULL, 13);
INSERT INTO field_value VALUES (4, 2, NULL, NULL, NULL, 16);
INSERT INTO field_value VALUES (6, 2, NULL, NULL, NULL, 21);
INSERT INTO field_value VALUES (7, 2, 'Hi', NULL, NULL, NULL);
INSERT INTO field_value VALUES (5, 2, '333333', NULL, NULL, NULL);
INSERT INTO field_value VALUES (8, 2, 'Very medium', NULL, NULL, NULL);
INSERT INTO field_value VALUES (1, 3, NULL, NULL, NULL, 2);
INSERT INTO field_value VALUES (2, 3, NULL, NULL, NULL, 2);
INSERT INTO field_value VALUES (3, 3, NULL, NULL, NULL, 12);
INSERT INTO field_value VALUES (3, 3, NULL, NULL, NULL, 13);
INSERT INTO field_value VALUES (4, 3, NULL, NULL, NULL, 16);
INSERT INTO field_value VALUES (6, 3, NULL, NULL, NULL, 22);
INSERT INTO field_value VALUES (7, 3, 'Goodbye', NULL, NULL, NULL);
INSERT INTO field_value VALUES (5, 3, '6666666', NULL, NULL, NULL);
INSERT INTO field_value VALUES (8, 3, 'Very baaaad', NULL, NULL, NULL);
INSERT INTO field_value VALUES (10, 8, NULL, NULL, NULL, 27);
INSERT INTO field_value VALUES (11, 8, NULL, NULL, NULL, 35);
INSERT INTO field_value VALUES (12, 8, '123', NULL, NULL, NULL);
INSERT INTO field_value VALUES (13, 8, NULL, 0.299999999999999989, NULL, NULL);
INSERT INTO field_value VALUES (15, 8, NULL, NULL, NULL, 42);
INSERT INTO field_value VALUES (16, 8, NULL, NULL, NULL, 46);
INSERT INTO field_value VALUES (17, 8, NULL, NULL, NULL, 52);
INSERT INTO field_value VALUES (18, 8, NULL, NULL, NULL, 54);
INSERT INTO field_value VALUES (20, 8, NULL, 5, NULL, NULL);
INSERT INTO field_value VALUES (21, 8, NULL, 5, NULL, NULL);
INSERT INTO field_value VALUES (1, 5, NULL, NULL, NULL, 11);
INSERT INTO field_value VALUES (2, 5, NULL, NULL, NULL, 11);
INSERT INTO field_value VALUES (3, 5, NULL, NULL, NULL, 14);
INSERT INTO field_value VALUES (4, 5, NULL, NULL, NULL, 19);
INSERT INTO field_value VALUES (5, 5, '+380660000000', NULL, NULL, NULL);
INSERT INTO field_value VALUES (6, 5, NULL, NULL, NULL, 21);
INSERT INTO field_value VALUES (7, 5, 'I''m ok', NULL, NULL, NULL);
INSERT INTO field_value VALUES (8, 5, 'norm', NULL, NULL, NULL);
INSERT INTO field_value VALUES (22, 8, NULL, 5, NULL, NULL);
INSERT INTO field_value VALUES (23, 8, NULL, 5, NULL, NULL);
INSERT INTO field_value VALUES (24, 8, NULL, 5, NULL, NULL);
INSERT INTO field_value VALUES (25, 8, NULL, NULL, NULL, 62);
INSERT INTO field_value VALUES (27, 8, NULL, NULL, NULL, 73);
INSERT INTO field_value VALUES (19, 8, '123', NULL, NULL, NULL);
INSERT INTO field_value VALUES (28, 8, '123', NULL, NULL, NULL);
INSERT INTO field_value VALUES (30, 8, NULL, 0.400000000000000022, NULL, NULL);
INSERT INTO field_value VALUES (10, 9, NULL, NULL, NULL, 27);
INSERT INTO field_value VALUES (11, 9, NULL, NULL, NULL, 34);
INSERT INTO field_value VALUES (12, 9, 'yghj', NULL, NULL, NULL);
INSERT INTO field_value VALUES (13, 9, NULL, 1, NULL, NULL);
INSERT INTO field_value VALUES (15, 9, NULL, NULL, NULL, 41);
INSERT INTO field_value VALUES (15, 9, NULL, NULL, NULL, 42);
INSERT INTO field_value VALUES (16, 9, NULL, NULL, NULL, 45);
INSERT INTO field_value VALUES (16, 9, NULL, NULL, NULL, 48);
INSERT INTO field_value VALUES (17, 9, NULL, NULL, NULL, 50);
INSERT INTO field_value VALUES (18, 9, NULL, NULL, NULL, 55);
INSERT INTO field_value VALUES (20, 9, NULL, 1, NULL, NULL);
INSERT INTO field_value VALUES (21, 9, NULL, 2, NULL, NULL);
INSERT INTO field_value VALUES (22, 9, NULL, 2, NULL, NULL);
INSERT INTO field_value VALUES (23, 9, NULL, 3, NULL, NULL);
INSERT INTO field_value VALUES (24, 9, NULL, 3, NULL, NULL);
INSERT INTO field_value VALUES (25, 9, NULL, NULL, NULL, 68);
INSERT INTO field_value VALUES (27, 9, NULL, NULL, NULL, 74);
INSERT INTO field_value VALUES (19, 9, 'итьртсммьтимсчч', NULL, NULL, NULL);
INSERT INTO field_value VALUES (28, 9, 'чиссрротмрссмрмопсап', NULL, NULL, NULL);
INSERT INTO field_value VALUES (30, 9, NULL, 4, NULL, NULL);


--
-- TOC entry 2139 (class 0 OID 27436)
-- Dependencies: 191
-- Data for Name: interviewee; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO interviewee VALUES (1, '2001-09-28 03:00:00', 'job offer', NULL, NULL);
INSERT INTO interviewee VALUES (2, '2001-09-28 03:00:00', 'rejected', NULL, NULL);
INSERT INTO interviewee VALUES (3, '2001-09-28 03:00:00', 'take on courses', NULL, NULL);
INSERT INTO interviewee VALUES (5, '2016-05-31 23:00:00', NULL, NULL, NULL);


--
-- TOC entry 2136 (class 0 OID 27400)
-- Dependencies: 188
-- Data for Name: interviewer_participation; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO interviewer_participation VALUES (15, 1);
INSERT INTO interviewer_participation VALUES (20, 1);
INSERT INTO interviewer_participation VALUES (25, 1);
INSERT INTO interviewer_participation VALUES (30, 1);
INSERT INTO interviewer_participation VALUES (7, 1);
INSERT INTO interviewer_participation VALUES (8, 1);
INSERT INTO interviewer_participation VALUES (9, 1);
INSERT INTO interviewer_participation VALUES (33, 1);
INSERT INTO interviewer_participation VALUES (14, 1);
INSERT INTO interviewer_participation VALUES (4, 1);
INSERT INTO interviewer_participation VALUES (5, 1);
INSERT INTO interviewer_participation VALUES (6, 1);
INSERT INTO interviewer_participation VALUES (10, 1);
INSERT INTO interviewer_participation VALUES (2, 1);


--
-- TOC entry 2141 (class 0 OID 27458)
-- Dependencies: 193
-- Data for Name: list; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO list VALUES (1, 'Marks');
INSERT INTO list VALUES (2, 'Interests');
INSERT INTO list VALUES (3, 'Information');
INSERT INTO list VALUES (4, 'University');
INSERT INTO list VALUES (6, 'univer_course');
INSERT INTO list VALUES (7, 'graduation_year');
INSERT INTO list VALUES (8, 'nc_interests');
INSERT INTO list VALUES (9, 'prof_interests');
INSERT INTO list VALUES (10, 'able_to_work');
INSERT INTO list VALUES (11, 'work_hours_per_week');
INSERT INTO list VALUES (12, 'english_level');
INSERT INTO list VALUES (13, 'english_level');
INSERT INTO list VALUES (14, 'where_found_info');


--
-- TOC entry 2177 (class 0 OID 0)
-- Dependencies: 192
-- Name: list_list_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('list_list_id_seq', 14, true);


--
-- TOC entry 2143 (class 0 OID 27466)
-- Dependencies: 195
-- Data for Name: list_value; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO list_value VALUES (1, 1, '0');
INSERT INTO list_value VALUES (2, 1, '1');
INSERT INTO list_value VALUES (3, 1, '2');
INSERT INTO list_value VALUES (4, 1, '3');
INSERT INTO list_value VALUES (5, 1, '4');
INSERT INTO list_value VALUES (6, 1, '5');
INSERT INTO list_value VALUES (7, 1, '6');
INSERT INTO list_value VALUES (8, 1, '7');
INSERT INTO list_value VALUES (9, 1, '8');
INSERT INTO list_value VALUES (10, 1, '9');
INSERT INTO list_value VALUES (11, 1, '10');
INSERT INTO list_value VALUES (12, 2, 'Research');
INSERT INTO list_value VALUES (13, 2, 'Practice');
INSERT INTO list_value VALUES (14, 2, 'New knowledge');
INSERT INTO list_value VALUES (15, 2, 'Work in NC');
INSERT INTO list_value VALUES (16, 3, 'VK');
INSERT INTO list_value VALUES (17, 3, 'Facebook');
INSERT INTO list_value VALUES (18, 3, 'Our site');
INSERT INTO list_value VALUES (19, 3, 'Other people');
INSERT INTO list_value VALUES (20, 3, 'TV');
INSERT INTO list_value VALUES (21, 4, 'KPI');
INSERT INTO list_value VALUES (22, 4, 'KNU');
INSERT INTO list_value VALUES (23, 4, 'NAUKMA');
INSERT INTO list_value VALUES (27, 6, '1');
INSERT INTO list_value VALUES (28, 6, '2');
INSERT INTO list_value VALUES (29, 6, '3');
INSERT INTO list_value VALUES (30, 6, '4');
INSERT INTO list_value VALUES (31, 6, '5');
INSERT INTO list_value VALUES (32, 6, '6');
INSERT INTO list_value VALUES (33, 6, 'Graduated');
INSERT INTO list_value VALUES (34, 7, '2013');
INSERT INTO list_value VALUES (35, 7, '2014');
INSERT INTO list_value VALUES (36, 7, '2015');
INSERT INTO list_value VALUES (37, 7, '2016');
INSERT INTO list_value VALUES (38, 7, '2017');
INSERT INTO list_value VALUES (39, 7, '2018');
INSERT INTO list_value VALUES (40, 7, '2019');
INSERT INTO list_value VALUES (41, 8, 'Job in NetCracker');
INSERT INTO list_value VALUES (42, 8, 'Experience');
INSERT INTO list_value VALUES (43, 8, 'Interesting projects');
INSERT INTO list_value VALUES (44, 8, 'New knowledge');
INSERT INTO list_value VALUES (45, 9, 'Backend development');
INSERT INTO list_value VALUES (46, 9, 'Frontend development');
INSERT INTO list_value VALUES (47, 9, 'DB development');
INSERT INTO list_value VALUES (48, 9, 'Business analysis');
INSERT INTO list_value VALUES (49, 9, 'QA');
INSERT INTO list_value VALUES (50, 10, 'Just at once');
INSERT INTO list_value VALUES (51, 10, 'After NC courses');
INSERT INTO list_value VALUES (52, 10, 'After university');
INSERT INTO list_value VALUES (53, 10, 'In nearest term');
INSERT INTO list_value VALUES (54, 11, '>20');
INSERT INTO list_value VALUES (55, 11, '20-25');
INSERT INTO list_value VALUES (56, 11, '25-35');
INSERT INTO list_value VALUES (57, 11, 'Fool time');
INSERT INTO list_value VALUES (58, 12, 'Beginner');
INSERT INTO list_value VALUES (59, 12, 'Elementary');
INSERT INTO list_value VALUES (60, 12, 'Pre-Intermediate');
INSERT INTO list_value VALUES (61, 13, 'Beginner');
INSERT INTO list_value VALUES (62, 12, 'Intermediate');
INSERT INTO list_value VALUES (63, 13, 'Elementary');
INSERT INTO list_value VALUES (64, 12, 'Upper intermediate');
INSERT INTO list_value VALUES (65, 13, 'Pre-Intermediate');
INSERT INTO list_value VALUES (66, 12, 'Advanced');
INSERT INTO list_value VALUES (67, 13, 'Intermediate');
INSERT INTO list_value VALUES (68, 12, 'Proficient');
INSERT INTO list_value VALUES (69, 13, 'Upper intermediate');
INSERT INTO list_value VALUES (70, 13, 'Advanced');
INSERT INTO list_value VALUES (71, 13, 'Proficient');
INSERT INTO list_value VALUES (72, 14, 'From friends');
INSERT INTO list_value VALUES (73, 14, 'From NC employees');
INSERT INTO list_value VALUES (74, 14, 'From official site');
INSERT INTO list_value VALUES (75, 14, 'From university');


--
-- TOC entry 2178 (class 0 OID 0)
-- Dependencies: 194
-- Name: list_value_list_value_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('list_value_list_value_id_seq', 75, true);


--
-- TOC entry 2120 (class 0 OID 27282)
-- Dependencies: 172
-- Data for Name: report_template; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO report_template VALUES (90, 'SELECT
app.application_id as id, CONCAT(su.name,'' '',su.surname) as Student, su.email as student_email,
CONCAT(sudev.name,'' '',sudev.surname) as dev, feeddev.score as dev_mark, feeddev.comment as dev_comment,
CONCAT(suhrba.name,'' '',suhrba.surname) as hrba, feedhrba.score as hrba_mark, feedhrba.comment as hrba_comment,
inter.special_mark as special_mark 
FROM public.application app
JOIN public.system_user su ON su.system_user_id = app.system_user_id
JOIN public.interviewee inter ON inter.application_id = app.application_id
JOIN public.feedback feeddev ON feeddev.feedback_id = inter.dev_feedback_id
JOIN public.system_user sudev ON sudev.system_user_id = feeddev.interviewer_id 
JOIN public.feedback feedhrba ON feedhrba.feedback_id = inter.hr_feedback_id
JOIN public.system_user suhrba ON suhrba.system_user_id = feedhrba.interviewer_id 
WHERE app.rejected IS TRUE', 'Intervie Result');
INSERT INTO report_template VALUES (91, 'SELECT 
COUNT(*) as interviewee_amount,
(COUNT(*) * ces.interviewing_time_person) as inter_time,
AVG(feeddev.score) as AVG_DEV_MARK,
AVG(feedhrba.score) as AVG_HRBA_MARK
FROM public.application app
JOIN public.system_user su ON su.system_user_id = app.system_user_id
JOIN public.course_enrollment_session ces ON ces.ces_id = app.ces_id
JOIN public.interviewee inter ON inter.application_id = app.application_id
JOIN public.feedback feeddev ON feeddev.feedback_id = inter.dev_feedback_id
JOIN public.system_user sudev ON sudev.system_user_id = feeddev.interviewer_id 
JOIN public.feedback feedhrba ON feedhrba.feedback_id = inter.hr_feedback_id
JOIN public.system_user suhrba ON suhrba.system_user_id = feedhrba.interviewer_id 
WHERE ces.ces_id = 1
GROUP BY ces.interviewing_time_person', 'CES Information');


--
-- TOC entry 2179 (class 0 OID 0)
-- Dependencies: 171
-- Name: report_template_report_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('report_template_report_id_seq', 91, true);


--
-- TOC entry 2122 (class 0 OID 27293)
-- Dependencies: 174
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO role VALUES (1, 'ROLE_ADMIN', 'Admin of the system');
INSERT INTO role VALUES (2, 'ROLE_STUDENT', 'Student who wants to/takes part in the course');
INSERT INTO role VALUES (3, 'ROLE_HR', 'HR of Netcracker');
INSERT INTO role VALUES (4, 'ROLE_DEV', 'Developer of Netcracker');
INSERT INTO role VALUES (5, 'ROLE_BA', 'BA of Netcracker');


--
-- TOC entry 2180 (class 0 OID 0)
-- Dependencies: 173
-- Name: role_role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('role_role_id_seq', 5, true);


--
-- TOC entry 2126 (class 0 OID 27316)
-- Dependencies: 178
-- Data for Name: system_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO system_user VALUES (20, 'petr6@gmail.com', '$2a$10$R6Ff5EaAuGnUFuh6G2abN.h6d2JUYG4NtzhuehdcICOKGZoKWib72', 'Petr6', 'Petrov6', 1);
INSERT INTO system_user VALUES (21, 'petr7@gmail.com', '$2a$10$R6Ff5EaAuGnUFuh6G2abN.h6d2JUYG4NtzhuehdcICOKGZoKWib72', 'Petr7', 'Petrov7', 1);
INSERT INTO system_user VALUES (22, 'petr8@gmail.com', '$2a$10$R6Ff5EaAuGnUFuh6G2abN.h6d2JUYG4NtzhuehdcICOKGZoKWib72', 'Petr8', 'Petrov8', 1);
INSERT INTO system_user VALUES (23, 'petr9@gmail.com', '$2a$10$R6Ff5EaAuGnUFuh6G2abN.h6d2JUYG4NtzhuehdcICOKGZoKWib72', 'Petr9', 'Petrov9', 1);
INSERT INTO system_user VALUES (24, 'petr10@gmail.com', '$2a$10$R6Ff5EaAuGnUFuh6G2abN.h6d2JUYG4NtzhuehdcICOKGZoKWib72', 'Petr10', 'Petrov10', 1);
INSERT INTO system_user VALUES (25, 'sidor1@gmail.com', '$2a$10$NnkytOyN6DlR9YaopcjEuOMlUAA1nNo6fdckQJEyb4xHKY17yU2Wi', 'Sidor1', 'Sidorov1', 1);
INSERT INTO system_user VALUES (26, 'sidor2@gmail.com', '$2a$10$NnkytOyN6DlR9YaopcjEuOMlUAA1nNo6fdckQJEyb4xHKY17yU2Wi', 'Sidor2', 'Sidorov2', 1);
INSERT INTO system_user VALUES (27, 'sidor3@gmail.com', '$2a$10$NnkytOyN6DlR9YaopcjEuOMlUAA1nNo6fdckQJEyb4xHKY17yU2Wi', 'Sidor3', 'Sidorov3', 1);
INSERT INTO system_user VALUES (28, 'sidor4@gmail.com', '$2a$10$NnkytOyN6DlR9YaopcjEuOMlUAA1nNo6fdckQJEyb4xHKY17yU2Wi', 'Sidor4', 'Sidorov4', 1);
INSERT INTO system_user VALUES (29, 'sidor5@gmail.com', '$2a$10$NnkytOyN6DlR9YaopcjEuOMlUAA1nNo6fdckQJEyb4xHKY17yU2Wi', 'Sidor5', 'Sidorov5', 1);
INSERT INTO system_user VALUES (30, 'sidor6@gmail.com', '$2a$10$NnkytOyN6DlR9YaopcjEuOMlUAA1nNo6fdckQJEyb4xHKY17yU2Wi', 'Sidor6', 'Sidorov6', 1);
INSERT INTO system_user VALUES (31, 'sidor7@gmail.com', '$2a$10$NnkytOyN6DlR9YaopcjEuOMlUAA1nNo6fdckQJEyb4xHKY17yU2Wi', 'Sidor7', 'Sidorov7', 1);
INSERT INTO system_user VALUES (32, 'sidor8@gmail.com', '$2a$10$NnkytOyN6DlR9YaopcjEuOMlUAA1nNo6fdckQJEyb4xHKY17yU2Wi', 'Sidor8', 'Sidorov8', 1);
INSERT INTO system_user VALUES (33, 'sidor9@gmail.com', '$2a$10$NnkytOyN6DlR9YaopcjEuOMlUAA1nNo6fdckQJEyb4xHKY17yU2Wi', 'Sidor9', 'Sidorov9', 1);
INSERT INTO system_user VALUES (34, 'sidor10@gmail.com', '$2a$10$NnkytOyN6DlR9YaopcjEuOMlUAA1nNo6fdckQJEyb4xHKY17yU2Wi', 'Sidor10', 'Sidorov10', 1);
INSERT INTO system_user VALUES (35, 'student1@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry', 'Student1', 'Student1', 1);
INSERT INTO system_user VALUES (36, 'student2@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry', 'Student2', 'Student2', 1);
INSERT INTO system_user VALUES (37, 'student3@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry', 'Student3', 'Student3', 1);
INSERT INTO system_user VALUES (38, 'student4@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry', 'Student4', 'Student4', 1);
INSERT INTO system_user VALUES (39, 'student5@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry', 'Student5', 'Student5', 1);
INSERT INTO system_user VALUES (40, 'student6@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry', 'Student6', 'Student6', 1);
INSERT INTO system_user VALUES (41, 'student7@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry', 'Student7', 'Student7', 1);
INSERT INTO system_user VALUES (42, 'student8@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry', 'Student8', 'Student8', 1);
INSERT INTO system_user VALUES (43, 'student9@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry', 'Student9', 'Student9', 1);
INSERT INTO system_user VALUES (44, 'student10@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry', 'Student10', 'Student10', 1);
INSERT INTO system_user VALUES (45, 'student11@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry', 'Student11', 'Student11', 1);
INSERT INTO system_user VALUES (46, 'student12@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry', 'Student12', 'Student12', 1);
INSERT INTO system_user VALUES (47, 'student13@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry', 'Student13', 'Student13', 1);
INSERT INTO system_user VALUES (48, 'student14@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry', 'Student14', 'Student14', 1);
INSERT INTO system_user VALUES (49, 'student15@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry', 'Student15', 'Student15', 1);
INSERT INTO system_user VALUES (50, 'student16@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry', 'Student16', 'Student16', 1);
INSERT INTO system_user VALUES (51, 'student17@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry', 'Student17', 'Student17', 1);
INSERT INTO system_user VALUES (52, 'student18@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry', 'Student18', 'Student18', 1);
INSERT INTO system_user VALUES (53, 'student19@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry', 'Student19', 'Student19', 1);
INSERT INTO system_user VALUES (54, 'student20@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry', 'Student20', 'Student20', 1);
INSERT INTO system_user VALUES (55, 'student21@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry', 'Student21', 'Student21', 1);
INSERT INTO system_user VALUES (56, 'student22@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry', 'Student22', 'Student22', 1);
INSERT INTO system_user VALUES (57, 'student23@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry', 'Student23', 'Student23', 1);
INSERT INTO system_user VALUES (58, 'student24@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry', 'Student24', 'Student24', 1);
INSERT INTO system_user VALUES (59, 'student25@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry', 'Student25', 'Student25', 1);
INSERT INTO system_user VALUES (60, 'student26@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry', 'Student26', 'Student26', 1);
INSERT INTO system_user VALUES (5, 'ivan1@gmail.com', '$2a$10$T6RBfaaPAbeZV4NWo6jN5.WQsr3L632HYwSs34gmF4AHLtxEXx18S', 'Ivan1', 'Ivanov1', 1);
INSERT INTO system_user VALUES (7, 'ivan3@gmail.com', '$2a$10$T6RBfaaPAbeZV4NWo6jN5.WQsr3L632HYwSs34gmF4AHLtxEXx18S', 'Ivan3', 'Ivanov3', 1);
INSERT INTO system_user VALUES (9, 'ivan5@gmail.com', '$2a$10$T6RBfaaPAbeZV4NWo6jN5.WQsr3L632HYwSs34gmF4AHLtxEXx18S', 'Ivan5', 'Ivanov5', 1);
INSERT INTO system_user VALUES (6, 'ivan2@gmail.com', '$2a$10$T6RBfaaPAbeZV4NWo6jN5.WQsr3L632HYwSs34gmF4AHLtxEXx18S', 'Ivan2', 'Ivanov2', 1);
INSERT INTO system_user VALUES (2, 'rom@gmail.com', '$2a$10$lS6mQkw2ejU.JZjgIFVQAeFemX0Dg9gO9k5Y.AkHW6VeckwNL3Qkm', 'Roman', 'Andriichuk', 1);
INSERT INTO system_user VALUES (11, 'ivan7@gmail.com', '$2a$10$T6RBfaaPAbeZV4NWo6jN5.WQsr3L632HYwSs34gmF4AHLtxEXx18S', 'Ivan7', 'Ivanov7', 1);
INSERT INTO system_user VALUES (10, 'ivan6@gmail.com', '$2a$10$T6RBfaaPAbeZV4NWo6jN5.WQsr3L632HYwSs34gmF4AHLtxEXx18S', 'Ivan6', 'Ivanov6', 1);
INSERT INTO system_user VALUES (4, 'kirill@gmail.com', '$2a$10$9uck3uR.cTwo9RNye0IcF.Us09QlaOGetAcXxj0QOfdvrxwUVm3ES', 'Kirill', 'Tumanov', 1);
INSERT INTO system_user VALUES (12, 'ivan8@gmail.com', '$2a$10$T6RBfaaPAbeZV4NWo6jN5.WQsr3L632HYwSs34gmF4AHLtxEXx18S', 'Ivan8', 'Ivanov8', 1);
INSERT INTO system_user VALUES (13, 'ivan9@gmail.com', '$2a$10$T6RBfaaPAbeZV4NWo6jN5.WQsr3L632HYwSs34gmF4AHLtxEXx18S', 'Ivan9', 'Ivanov9', 1);
INSERT INTO system_user VALUES (14, 'ivan10@gmail.com', '$2a$10$T6RBfaaPAbeZV4NWo6jN5.WQsr3L632HYwSs34gmF4AHLtxEXx18S', 'Ivan10', 'Ivanov10', 1);
INSERT INTO system_user VALUES (15, 'petr1@gmail.com', '$2a$10$R6Ff5EaAuGnUFuh6G2abN.h6d2JUYG4NtzhuehdcICOKGZoKWib72', 'Petr1', 'Petrov1', 1);
INSERT INTO system_user VALUES (16, 'petr2@gmail.com', '$2a$10$R6Ff5EaAuGnUFuh6G2abN.h6d2JUYG4NtzhuehdcICOKGZoKWib72', 'Petr2', 'Petrov2', 1);
INSERT INTO system_user VALUES (17, 'petr3@gmail.com', '$2a$10$R6Ff5EaAuGnUFuh6G2abN.h6d2JUYG4NtzhuehdcICOKGZoKWib72', 'Petr3', 'Petrov3', 1);
INSERT INTO system_user VALUES (18, 'petr4@gmail.com', '$2a$10$R6Ff5EaAuGnUFuh6G2abN.h6d2JUYG4NtzhuehdcICOKGZoKWib72', 'Petr4', 'Petrov4', 1);
INSERT INTO system_user VALUES (19, 'petr5@gmail.com', '$2a$10$R6Ff5EaAuGnUFuh6G2abN.h6d2JUYG4NtzhuehdcICOKGZoKWib72', 'Petr5', 'Petrov5', 1);
INSERT INTO system_user VALUES (3, 'sasha@gmail.com', '$2a$10$bflfk70LoK2kf4DRA2TXo.M43YjvMKFYfEKgwaEqq2rnEleXBgOI.', 'Sasha', 'Beskorovaynaya', 1);
INSERT INTO system_user VALUES (61, 'student27@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry', 'Student27', 'Student27', 1);
INSERT INTO system_user VALUES (62, 'student28@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry', 'Student28', 'Student28', 1);
INSERT INTO system_user VALUES (63, 'student29@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry', 'Student29', 'Student29', 1);
INSERT INTO system_user VALUES (64, 'student30@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry', 'Student30', 'Student30', 1);
INSERT INTO system_user VALUES (65, 'admin@g.com', '$2a$10$47.G7G70OowkssCrNBpV2uPa9A9RqWm9aoPjulcgxI6ep7grXRpcO', 'myadmin', 'admin', 1);
INSERT INTO system_user VALUES (66, 'ddddd@yandex.ru', '$2a$10$fd2drK6fAqQhcWN7hf6Vje5rCwxkNJycWyBPqr8zXkpmwpkpVxP5q', 'Fdsfsdfsdfsf', 'Fdfsdfdsfsf', 1);
INSERT INTO system_user VALUES (67, 'sdfsfdsf@gfdgd.coi', '$2a$10$huBUae.CKwfJmfEB.Ch8..rhiQgxloUV34OKcNJQE2hEJyRhPN4em', 'Fdsfsdfdsfsf', 'Fdsfsfsf', 1);
INSERT INTO system_user VALUES (68, 'ionfsdfsdf@gmail.com', '$2a$10$pUUrATyQHkCt6XbkTOh4HeGB0px2/hm1QmPduSs80Sm5IXu5ySRgu', 'Ion', 'Ion', 1);
INSERT INTO system_user VALUES (69, 'babababa@gmail.com', '$2a$10$y28yRXacC.9JM3XUT2Em5e0j6mJKUXa9zuAFoLYaoMLKfjFz3I1ey', 'NewBa', 'NewBa', 1);
INSERT INTO system_user VALUES (1, 'ion@gmail.com', '$2a$10$U2HGciX6QWTwus5SEGVKgeCu/OHH7DXSwwBVQaGjO7o.mKyC8J4pq', 'Ion', 'Ionets', 1);
INSERT INTO system_user VALUES (8, 'ivan4@gmail.com', '$2a$10$T6RBfaaPAbeZV4NWo6jN5.WQsr3L632HYwSs34gmF4AHLtxEXx18S', 'Ivan4', 'Ivanov4', 1);
INSERT INTO system_user VALUES (72, 'tneduts@gmail.com', '$2a$10$KC7zDGuzNR7vhJm832zQk./mimLVv5BsLGx3dcNIkl3y7TmsNSE02', 'Tneduts', 'Tneduts', 1);
INSERT INTO system_user VALUES (70, 'kostharl@gmail.com', '$2a$10$XyZhiZZDxt/dbaz2agLnxOmqckS6q8lUteOSugymb1eiI7kBWDPiS', 'Konstantin', 'Konstantin', 1);


--
-- TOC entry 2127 (class 0 OID 27330)
-- Dependencies: 179
-- Data for Name: system_user_role; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO system_user_role VALUES (1, 1);
INSERT INTO system_user_role VALUES (1, 4);
INSERT INTO system_user_role VALUES (5, 5);
INSERT INTO system_user_role VALUES (6, 5);
INSERT INTO system_user_role VALUES (7, 5);
INSERT INTO system_user_role VALUES (8, 5);
INSERT INTO system_user_role VALUES (9, 5);
INSERT INTO system_user_role VALUES (10, 5);
INSERT INTO system_user_role VALUES (11, 5);
INSERT INTO system_user_role VALUES (12, 5);
INSERT INTO system_user_role VALUES (13, 5);
INSERT INTO system_user_role VALUES (14, 5);
INSERT INTO system_user_role VALUES (15, 4);
INSERT INTO system_user_role VALUES (16, 4);
INSERT INTO system_user_role VALUES (17, 4);
INSERT INTO system_user_role VALUES (18, 4);
INSERT INTO system_user_role VALUES (19, 4);
INSERT INTO system_user_role VALUES (20, 4);
INSERT INTO system_user_role VALUES (21, 4);
INSERT INTO system_user_role VALUES (22, 4);
INSERT INTO system_user_role VALUES (23, 4);
INSERT INTO system_user_role VALUES (24, 4);
INSERT INTO system_user_role VALUES (25, 3);
INSERT INTO system_user_role VALUES (26, 3);
INSERT INTO system_user_role VALUES (27, 3);
INSERT INTO system_user_role VALUES (28, 3);
INSERT INTO system_user_role VALUES (29, 3);
INSERT INTO system_user_role VALUES (30, 3);
INSERT INTO system_user_role VALUES (31, 3);
INSERT INTO system_user_role VALUES (32, 3);
INSERT INTO system_user_role VALUES (33, 3);
INSERT INTO system_user_role VALUES (34, 3);
INSERT INTO system_user_role VALUES (35, 2);
INSERT INTO system_user_role VALUES (36, 2);
INSERT INTO system_user_role VALUES (37, 2);
INSERT INTO system_user_role VALUES (38, 2);
INSERT INTO system_user_role VALUES (39, 2);
INSERT INTO system_user_role VALUES (40, 2);
INSERT INTO system_user_role VALUES (41, 2);
INSERT INTO system_user_role VALUES (42, 2);
INSERT INTO system_user_role VALUES (43, 2);
INSERT INTO system_user_role VALUES (44, 2);
INSERT INTO system_user_role VALUES (45, 2);
INSERT INTO system_user_role VALUES (46, 2);
INSERT INTO system_user_role VALUES (47, 2);
INSERT INTO system_user_role VALUES (48, 2);
INSERT INTO system_user_role VALUES (49, 2);
INSERT INTO system_user_role VALUES (50, 2);
INSERT INTO system_user_role VALUES (51, 2);
INSERT INTO system_user_role VALUES (52, 2);
INSERT INTO system_user_role VALUES (53, 2);
INSERT INTO system_user_role VALUES (54, 2);
INSERT INTO system_user_role VALUES (55, 2);
INSERT INTO system_user_role VALUES (56, 2);
INSERT INTO system_user_role VALUES (57, 2);
INSERT INTO system_user_role VALUES (58, 2);
INSERT INTO system_user_role VALUES (59, 2);
INSERT INTO system_user_role VALUES (60, 2);
INSERT INTO system_user_role VALUES (61, 2);
INSERT INTO system_user_role VALUES (62, 2);
INSERT INTO system_user_role VALUES (63, 2);
INSERT INTO system_user_role VALUES (64, 2);
INSERT INTO system_user_role VALUES (65, 1);
INSERT INTO system_user_role VALUES (66, 1);
INSERT INTO system_user_role VALUES (66, 3);
INSERT INTO system_user_role VALUES (67, 4);
INSERT INTO system_user_role VALUES (67, 1);
INSERT INTO system_user_role VALUES (68, 5);
INSERT INTO system_user_role VALUES (69, 5);
INSERT INTO system_user_role VALUES (70, 2);
INSERT INTO system_user_role VALUES (72, 2);


--
-- TOC entry 2124 (class 0 OID 27306)
-- Dependencies: 176
-- Data for Name: system_user_status; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO system_user_status VALUES (1, 'Active');
INSERT INTO system_user_status VALUES (2, 'Inactive');


--
-- TOC entry 2181 (class 0 OID 0)
-- Dependencies: 175
-- Name: system_user_status_system_user_status_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('system_user_status_system_user_status_id_seq', 2, true);


--
-- TOC entry 2182 (class 0 OID 0)
-- Dependencies: 177
-- Name: system_user_system_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('system_user_system_user_id_seq', 73, true);


--
-- TOC entry 1975 (class 2606 OID 27423)
-- Name: application_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY application
    ADD CONSTRAINT application_pkey PRIMARY KEY (application_id);


--
-- TOC entry 1977 (class 2606 OID 27425)
-- Name: application_system_user_id_ces_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY application
    ADD CONSTRAINT application_system_user_id_ces_id_key UNIQUE (system_user_id, ces_id);


--
-- TOC entry 1991 (class 2606 OID 27513)
-- Name: ces_field_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ces_field
    ADD CONSTRAINT ces_field_pkey PRIMARY KEY (ces_id, field_id);


--
-- TOC entry 1969 (class 2606 OID 27379)
-- Name: ces_status_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ces_status
    ADD CONSTRAINT ces_status_pkey PRIMARY KEY (ces_status_id);


--
-- TOC entry 1971 (class 2606 OID 27394)
-- Name: course_enrollment_session_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY course_enrollment_session
    ADD CONSTRAINT course_enrollment_session_pkey PRIMARY KEY (ces_id);


--
-- TOC entry 1967 (class 2606 OID 27371)
-- Name: email_template_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY email_template
    ADD CONSTRAINT email_template_pkey PRIMARY KEY (email_template_id);


--
-- TOC entry 1965 (class 2606 OID 27355)
-- Name: feedback_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY feedback
    ADD CONSTRAINT feedback_pkey PRIMARY KEY (feedback_id);


--
-- TOC entry 1989 (class 2606 OID 27498)
-- Name: field_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY field
    ADD CONSTRAINT field_pkey PRIMARY KEY (field_id);


--
-- TOC entry 1985 (class 2606 OID 27489)
-- Name: field_type_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY field_type
    ADD CONSTRAINT field_type_name_key UNIQUE (name);


--
-- TOC entry 1987 (class 2606 OID 27487)
-- Name: field_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY field_type
    ADD CONSTRAINT field_type_pkey PRIMARY KEY (field_type_id);


--
-- TOC entry 1979 (class 2606 OID 27440)
-- Name: interviewee_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY interviewee
    ADD CONSTRAINT interviewee_pkey PRIMARY KEY (application_id);


--
-- TOC entry 1973 (class 2606 OID 27404)
-- Name: interviewer_participation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY interviewer_participation
    ADD CONSTRAINT interviewer_participation_pkey PRIMARY KEY (system_user_id, ces_id);


--
-- TOC entry 1981 (class 2606 OID 27463)
-- Name: list_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY list
    ADD CONSTRAINT list_pkey PRIMARY KEY (list_id);


--
-- TOC entry 1983 (class 2606 OID 27474)
-- Name: list_value_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY list_value
    ADD CONSTRAINT list_value_pkey PRIMARY KEY (list_value_id);


--
-- TOC entry 1949 (class 2606 OID 27290)
-- Name: report_template_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY report_template
    ADD CONSTRAINT report_template_pkey PRIMARY KEY (report_id);


--
-- TOC entry 1951 (class 2606 OID 27303)
-- Name: role_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY role
    ADD CONSTRAINT role_name_key UNIQUE (name);


--
-- TOC entry 1953 (class 2606 OID 27301)
-- Name: role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY role
    ADD CONSTRAINT role_pkey PRIMARY KEY (role_id);


--
-- TOC entry 1959 (class 2606 OID 27323)
-- Name: system_user_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY system_user
    ADD CONSTRAINT system_user_email_key UNIQUE (email);


--
-- TOC entry 1961 (class 2606 OID 27321)
-- Name: system_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY system_user
    ADD CONSTRAINT system_user_pkey PRIMARY KEY (system_user_id);


--
-- TOC entry 1963 (class 2606 OID 27334)
-- Name: system_user_role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY system_user_role
    ADD CONSTRAINT system_user_role_pkey PRIMARY KEY (system_user_id, role_id);


--
-- TOC entry 1955 (class 2606 OID 27313)
-- Name: system_user_status_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY system_user_status
    ADD CONSTRAINT system_user_status_name_key UNIQUE (name);


--
-- TOC entry 1957 (class 2606 OID 27311)
-- Name: system_user_status_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY system_user_status
    ADD CONSTRAINT system_user_status_pkey PRIMARY KEY (system_user_status_id);


--
-- TOC entry 2000 (class 2606 OID 27431)
-- Name: application_ces_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY application
    ADD CONSTRAINT application_ces_id_fkey FOREIGN KEY (ces_id) REFERENCES course_enrollment_session(ces_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 1999 (class 2606 OID 27426)
-- Name: application_system_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY application
    ADD CONSTRAINT application_system_user_id_fkey FOREIGN KEY (system_user_id) REFERENCES system_user(system_user_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2007 (class 2606 OID 27514)
-- Name: ces_field_ces_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ces_field
    ADD CONSTRAINT ces_field_ces_id_fkey FOREIGN KEY (ces_id) REFERENCES course_enrollment_session(ces_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2008 (class 2606 OID 27519)
-- Name: ces_field_field_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ces_field
    ADD CONSTRAINT ces_field_field_id_fkey FOREIGN KEY (field_id) REFERENCES field(field_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 1996 (class 2606 OID 27395)
-- Name: course_enrollment_session_ces_status_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY course_enrollment_session
    ADD CONSTRAINT course_enrollment_session_ces_status_id_fkey FOREIGN KEY (ces_status_id) REFERENCES ces_status(ces_status_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 1995 (class 2606 OID 27356)
-- Name: feedback_interviewer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY feedback
    ADD CONSTRAINT feedback_interviewer_id_fkey FOREIGN KEY (interviewer_id) REFERENCES system_user(system_user_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2005 (class 2606 OID 27499)
-- Name: field_field_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY field
    ADD CONSTRAINT field_field_type_id_fkey FOREIGN KEY (field_type_id) REFERENCES field_type(field_type_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2006 (class 2606 OID 27504)
-- Name: field_list_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY field
    ADD CONSTRAINT field_list_id_fkey FOREIGN KEY (list_id) REFERENCES list(list_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2010 (class 2606 OID 27536)
-- Name: field_value_application_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY field_value
    ADD CONSTRAINT field_value_application_id_fkey FOREIGN KEY (application_id) REFERENCES application(application_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2009 (class 2606 OID 27531)
-- Name: field_value_field_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY field_value
    ADD CONSTRAINT field_value_field_id_fkey FOREIGN KEY (field_id) REFERENCES field(field_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2011 (class 2606 OID 27541)
-- Name: field_value_list_value_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY field_value
    ADD CONSTRAINT field_value_list_value_id_fkey FOREIGN KEY (list_value_id) REFERENCES list_value(list_value_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2001 (class 2606 OID 27441)
-- Name: interviewee_application_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY interviewee
    ADD CONSTRAINT interviewee_application_id_fkey FOREIGN KEY (application_id) REFERENCES application(application_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2002 (class 2606 OID 27446)
-- Name: interviewee_dev_feedback_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY interviewee
    ADD CONSTRAINT interviewee_dev_feedback_id_fkey FOREIGN KEY (dev_feedback_id) REFERENCES feedback(feedback_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2003 (class 2606 OID 27451)
-- Name: interviewee_hr_feedback_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY interviewee
    ADD CONSTRAINT interviewee_hr_feedback_id_fkey FOREIGN KEY (hr_feedback_id) REFERENCES feedback(feedback_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 1998 (class 2606 OID 27410)
-- Name: interviewer_participation_ces_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY interviewer_participation
    ADD CONSTRAINT interviewer_participation_ces_id_fkey FOREIGN KEY (ces_id) REFERENCES course_enrollment_session(ces_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 1997 (class 2606 OID 27405)
-- Name: interviewer_participation_system_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY interviewer_participation
    ADD CONSTRAINT interviewer_participation_system_user_id_fkey FOREIGN KEY (system_user_id) REFERENCES system_user(system_user_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2004 (class 2606 OID 27475)
-- Name: list_value_list_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY list_value
    ADD CONSTRAINT list_value_list_id_fkey FOREIGN KEY (list_id) REFERENCES list(list_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 1994 (class 2606 OID 27340)
-- Name: system_user_role_role_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY system_user_role
    ADD CONSTRAINT system_user_role_role_id_fkey FOREIGN KEY (role_id) REFERENCES role(role_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 1993 (class 2606 OID 27335)
-- Name: system_user_role_system_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY system_user_role
    ADD CONSTRAINT system_user_role_system_user_id_fkey FOREIGN KEY (system_user_id) REFERENCES system_user(system_user_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 1992 (class 2606 OID 27324)
-- Name: system_user_system_user_status_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY system_user
    ADD CONSTRAINT system_user_system_user_status_id_fkey FOREIGN KEY (system_user_status_id) REFERENCES system_user_status(system_user_status_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2155 (class 0 OID 0)
-- Dependencies: 7
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2016-05-31 11:33:09

--
-- PostgreSQL database dump complete
--


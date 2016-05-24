--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.12
-- Dumped by pg_dump version 9.5.1

-- Started on 2016-05-11 13:15:46

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
DROP SCHEMA public cascade;
CREATE SCHEMA public;

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2155 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 190 (class 1259 OID 18725)
-- Name: application; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE application (
    application_id integer NOT NULL,
    system_user_id integer NOT NULL,
    ces_id integer NOT NULL,
    rejected boolean
);


ALTER TABLE application OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 18723)
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
-- TOC entry 2156 (class 0 OID 0)
-- Dependencies: 189
-- Name: application_application_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE application_application_id_seq OWNED BY application.application_id;


--
-- TOC entry 200 (class 1259 OID 18816)
-- Name: ces_field; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE ces_field (
    ces_id integer NOT NULL,
    field_id integer NOT NULL
);


ALTER TABLE ces_field OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 18682)
-- Name: ces_status; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE ces_status (
    ces_status_id integer NOT NULL,
    name character varying(20) NOT NULL
);


ALTER TABLE ces_status OWNER TO postgres;

--
-- TOC entry 184 (class 1259 OID 18680)
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
-- TOC entry 2157 (class 0 OID 0)
-- Dependencies: 184
-- Name: ces_status_ces_status_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE ces_status_ces_status_id_seq OWNED BY ces_status.ces_status_id;


--
-- TOC entry 187 (class 1259 OID 18690)
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
-- TOC entry 186 (class 1259 OID 18688)
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
-- TOC entry 2158 (class 0 OID 0)
-- Dependencies: 186
-- Name: course_enrollment_session_ces_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE course_enrollment_session_ces_id_seq OWNED BY course_enrollment_session.ces_id;


--
-- TOC entry 183 (class 1259 OID 18671)
-- Name: email_template; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE email_template (
    email_template_id integer NOT NULL,
    body_template text NOT NULL,
    head_template text NOT NULL
);


ALTER TABLE email_template OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 18669)
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
-- TOC entry 2159 (class 0 OID 0)
-- Dependencies: 182
-- Name: email_template_email_template_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE email_template_email_template_id_seq OWNED BY email_template.email_template_id;


--
-- TOC entry 181 (class 1259 OID 18655)
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
-- TOC entry 180 (class 1259 OID 18653)
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
-- TOC entry 2160 (class 0 OID 0)
-- Dependencies: 180
-- Name: feedback_feedback_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE feedback_feedback_id_seq OWNED BY feedback.feedback_id;


--
-- TOC entry 199 (class 1259 OID 18799)
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
-- TOC entry 198 (class 1259 OID 18797)
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
-- TOC entry 2161 (class 0 OID 0)
-- Dependencies: 198
-- Name: field_field_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE field_field_id_seq OWNED BY field.field_id;


--
-- TOC entry 197 (class 1259 OID 18789)
-- Name: field_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE field_type (
    field_type_id integer NOT NULL,
    name character varying(20) NOT NULL
);


ALTER TABLE field_type OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 18787)
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
-- TOC entry 2162 (class 0 OID 0)
-- Dependencies: 196
-- Name: field_type_field_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE field_type_field_type_id_seq OWNED BY field_type.field_type_id;


--
-- TOC entry 201 (class 1259 OID 18831)
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
-- TOC entry 191 (class 1259 OID 18743)
-- Name: interviewee; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE interviewee (
    application_id integer NOT NULL,
    interview_time timestamp without time zone NOT NULL,
    special_mark character varying(100),
    dev_feedback_id integer,
    hr_feedback_id integer
);


ALTER TABLE interviewee OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 18708)
-- Name: interviewer_participation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE interviewer_participation (
    system_user_id integer NOT NULL,
    ces_id integer NOT NULL
);


ALTER TABLE interviewer_participation OWNER TO postgres;

--
-- TOC entry 193 (class 1259 OID 18765)
-- Name: list; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE list (
    list_id integer NOT NULL,
    name character varying(20) NOT NULL
);


ALTER TABLE list OWNER TO postgres;

--
-- TOC entry 192 (class 1259 OID 18763)
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
-- TOC entry 2163 (class 0 OID 0)
-- Dependencies: 192
-- Name: list_list_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE list_list_id_seq OWNED BY list.list_id;


--
-- TOC entry 195 (class 1259 OID 18773)
-- Name: list_value; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE list_value (
    list_value_id integer NOT NULL,
    list_id integer NOT NULL,
    value_text text NOT NULL
);


ALTER TABLE list_value OWNER TO postgres;

--
-- TOC entry 194 (class 1259 OID 18771)
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
-- TOC entry 2164 (class 0 OID 0)
-- Dependencies: 194
-- Name: list_value_list_value_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE list_value_list_value_id_seq OWNED BY list_value.list_value_id;


--
-- TOC entry 172 (class 1259 OID 18591)
-- Name: report_template; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE report_template (
    report_id integer NOT NULL,
    query text NOT NULL,
    name character varying(20) NOT NULL
);


ALTER TABLE report_template OWNER TO postgres;

--
-- TOC entry 171 (class 1259 OID 18589)
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
-- TOC entry 2165 (class 0 OID 0)
-- Dependencies: 171
-- Name: report_template_report_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE report_template_report_id_seq OWNED BY report_template.report_id;


--
-- TOC entry 174 (class 1259 OID 18602)
-- Name: role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE role (
    role_id integer NOT NULL,
    name character varying(20) NOT NULL,
    description text NOT NULL
);


ALTER TABLE role OWNER TO postgres;

--
-- TOC entry 173 (class 1259 OID 18600)
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
-- TOC entry 2166 (class 0 OID 0)
-- Dependencies: 173
-- Name: role_role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE role_role_id_seq OWNED BY role.role_id;


--
-- TOC entry 178 (class 1259 OID 18625)
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
-- TOC entry 179 (class 1259 OID 18638)
-- Name: system_user_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE system_user_role (
    system_user_id integer NOT NULL,
    role_id integer NOT NULL
);


ALTER TABLE system_user_role OWNER TO postgres;

--
-- TOC entry 176 (class 1259 OID 18615)
-- Name: system_user_status; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE system_user_status (
    system_user_status_id integer NOT NULL,
    name character varying(20) NOT NULL
);


ALTER TABLE system_user_status OWNER TO postgres;

--
-- TOC entry 175 (class 1259 OID 18613)
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
-- TOC entry 2167 (class 0 OID 0)
-- Dependencies: 175
-- Name: system_user_status_system_user_status_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE system_user_status_system_user_status_id_seq OWNED BY system_user_status.system_user_status_id;


--
-- TOC entry 177 (class 1259 OID 18623)
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
-- TOC entry 2168 (class 0 OID 0)
-- Dependencies: 177
-- Name: system_user_system_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE system_user_system_user_id_seq OWNED BY system_user.system_user_id;


--
-- TOC entry 1940 (class 2604 OID 18728)
-- Name: application_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY application ALTER COLUMN application_id SET DEFAULT nextval('application_application_id_seq'::regclass);


--
-- TOC entry 1931 (class 2604 OID 18685)
-- Name: ces_status_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ces_status ALTER COLUMN ces_status_id SET DEFAULT nextval('ces_status_ces_status_id_seq'::regclass);


--
-- TOC entry 1932 (class 2604 OID 18693)
-- Name: ces_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY course_enrollment_session ALTER COLUMN ces_id SET DEFAULT nextval('course_enrollment_session_ces_id_seq'::regclass);


--
-- TOC entry 1930 (class 2604 OID 18674)
-- Name: email_template_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY email_template ALTER COLUMN email_template_id SET DEFAULT nextval('email_template_email_template_id_seq'::regclass);


--
-- TOC entry 1929 (class 2604 OID 18658)
-- Name: feedback_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY feedback ALTER COLUMN feedback_id SET DEFAULT nextval('feedback_feedback_id_seq'::regclass);


--
-- TOC entry 1944 (class 2604 OID 18802)
-- Name: field_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY field ALTER COLUMN field_id SET DEFAULT nextval('field_field_id_seq'::regclass);


--
-- TOC entry 1943 (class 2604 OID 18792)
-- Name: field_type_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY field_type ALTER COLUMN field_type_id SET DEFAULT nextval('field_type_field_type_id_seq'::regclass);


--
-- TOC entry 1941 (class 2604 OID 18768)
-- Name: list_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY list ALTER COLUMN list_id SET DEFAULT nextval('list_list_id_seq'::regclass);


--
-- TOC entry 1942 (class 2604 OID 18776)
-- Name: list_value_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY list_value ALTER COLUMN list_value_id SET DEFAULT nextval('list_value_list_value_id_seq'::regclass);


--
-- TOC entry 1925 (class 2604 OID 18594)
-- Name: report_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY report_template ALTER COLUMN report_id SET DEFAULT nextval('report_template_report_id_seq'::regclass);


--
-- TOC entry 1926 (class 2604 OID 18605)
-- Name: role_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY role ALTER COLUMN role_id SET DEFAULT nextval('role_role_id_seq'::regclass);


--
-- TOC entry 1928 (class 2604 OID 18628)
-- Name: system_user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY system_user ALTER COLUMN system_user_id SET DEFAULT nextval('system_user_system_user_id_seq'::regclass);


--
-- TOC entry 1927 (class 2604 OID 18618)
-- Name: system_user_status_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY system_user_status ALTER COLUMN system_user_status_id SET DEFAULT nextval('system_user_status_system_user_status_id_seq'::regclass);


--
-- TOC entry 2137 (class 0 OID 18725)
-- Dependencies: 190
-- Data for Name: application; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO application (application_id, system_user_id, ces_id, rejected) VALUES (9, 19, 1, true);
INSERT INTO application (application_id, system_user_id, ces_id, rejected) VALUES (11, 20, 1, true);
INSERT INTO application (application_id, system_user_id, ces_id, rejected) VALUES (13, 21, 1, true);
INSERT INTO application (application_id, system_user_id, ces_id, rejected) VALUES (17, 22, 1, true);
INSERT INTO application (application_id, system_user_id, ces_id, rejected) VALUES (19, 23, 1, true);
INSERT INTO application (application_id, system_user_id, ces_id, rejected) VALUES (20, 24, 1, true);
INSERT INTO application (application_id, system_user_id, ces_id, rejected) VALUES (25, 26, 1, true);
INSERT INTO application (application_id, system_user_id, ces_id, rejected) VALUES (28, 28, 1, true);
INSERT INTO application (application_id, system_user_id, ces_id, rejected) VALUES (33, 29, 1, true);
INSERT INTO application (application_id, system_user_id, ces_id, rejected) VALUES (7, 5, 1, true);
INSERT INTO application (application_id, system_user_id, ces_id, rejected) VALUES (35, 1, 1, true);
INSERT INTO application (application_id, system_user_id, ces_id, rejected) VALUES (36, 2, 1, true);
INSERT INTO application (application_id, system_user_id, ces_id, rejected) VALUES (38, 17, 1, true);
INSERT INTO application (application_id, system_user_id, ces_id, rejected) VALUES (41, 30, 1, true);
INSERT INTO application (application_id, system_user_id, ces_id, rejected) VALUES (1, 6, 1, true);
INSERT INTO application (application_id, system_user_id, ces_id, rejected) VALUES (2, 9, 1, true);
INSERT INTO application (application_id, system_user_id, ces_id, rejected) VALUES (4, 15, 1, true);


--
-- TOC entry 2169 (class 0 OID 0)
-- Dependencies: 189
-- Name: application_application_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('application_application_id_seq', 41, true);


--
-- TOC entry 2147 (class 0 OID 18816)
-- Dependencies: 200
-- Data for Name: ces_field; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO ces_field (ces_id, field_id) VALUES (1, 1);
INSERT INTO ces_field (ces_id, field_id) VALUES (1, 2);
INSERT INTO ces_field (ces_id, field_id) VALUES (1, 3);
INSERT INTO ces_field (ces_id, field_id) VALUES (1, 4);
INSERT INTO ces_field (ces_id, field_id) VALUES (1, 5);
INSERT INTO ces_field (ces_id, field_id) VALUES (1, 6);
INSERT INTO ces_field (ces_id, field_id) VALUES (1, 7);
INSERT INTO ces_field (ces_id, field_id) VALUES (1, 8);


--
-- TOC entry 2132 (class 0 OID 18682)
-- Dependencies: 185
-- Data for Name: ces_status; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO ces_status (ces_status_id, name) VALUES (1, 'Active');
INSERT INTO ces_status (ces_status_id, name) VALUES (2, 'Closed');
INSERT INTO ces_status (ces_status_id, name) VALUES (3, 'Pending');


--
-- TOC entry 2170 (class 0 OID 0)
-- Dependencies: 184
-- Name: ces_status_ces_status_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('ces_status_ces_status_id_seq', 3, true);


--
-- TOC entry 2134 (class 0 OID 18690)
-- Dependencies: 187
-- Data for Name: course_enrollment_session; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO course_enrollment_session (ces_id, year, start_registration_date, end_registration_date, start_interviewing_date, end_interviewing_date, quota, ces_status_id, reminders, interviewing_time_person, interviewing_time_day) VALUES (1, 2016, '2016-05-10', '2016-05-15', '2016-05-20', '2016-05-25', 100, 1, 240, 10, 100);


--
-- TOC entry 2171 (class 0 OID 0)
-- Dependencies: 186
-- Name: course_enrollment_session_ces_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('course_enrollment_session_ces_id_seq', 1, true);


--
-- TOC entry 2130 (class 0 OID 18671)
-- Dependencies: 183
-- Data for Name: email_template; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO email_template (email_template_id, body_template, head_template) VALUES (1, 'wwerwerwerw', 'welcomewer');
INSERT INTO email_template (email_template_id, body_template, head_template) VALUES (2, 'fgnfgnfgn', 'gfnfgnhfghn');
INSERT INTO email_template (email_template_id, body_template, head_template) VALUES (3, 'fgndfgnggn', 'nfgngngfn');


--
-- TOC entry 2172 (class 0 OID 0)
-- Dependencies: 182
-- Name: email_template_email_template_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('email_template_email_template_id_seq', 3, true);


--
-- TOC entry 2128 (class 0 OID 18655)
-- Dependencies: 181
-- Data for Name: feedback; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO feedback (feedback_id, score, comment, interviewer_id) VALUES (1, 75, 'Norm', 1);
INSERT INTO feedback (feedback_id, score, comment, interviewer_id) VALUES (2, 30, 'Not OK', 5);
INSERT INTO feedback (feedback_id, score, comment, interviewer_id) VALUES (3, 4, 'kjnhlkjlk', 1);
INSERT INTO feedback (feedback_id, score, comment, interviewer_id) VALUES (4, 0, '', 1);
INSERT INTO feedback (feedback_id, score, comment, interviewer_id) VALUES (5, 0, 'jkhlhj', 1);
INSERT INTO feedback (feedback_id, score, comment, interviewer_id) VALUES (6, 0, 'jkhlhj', 1);
INSERT INTO feedback (feedback_id, score, comment, interviewer_id) VALUES (7, 18, 'jkhlhj', 1);
INSERT INTO feedback (feedback_id, score, comment, interviewer_id) VALUES (8, 0, 'lrkgjsolrkjflks', 5);
INSERT INTO feedback (feedback_id, score, comment, interviewer_id) VALUES (9, 0, 'lrkgjsolrkjflks', 5);


--
-- TOC entry 2173 (class 0 OID 0)
-- Dependencies: 180
-- Name: feedback_feedback_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('feedback_feedback_id_seq', 9, true);


--
-- TOC entry 2146 (class 0 OID 18799)
-- Dependencies: 199
-- Data for Name: field; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO field (field_id, name, field_type_id, multiple_choice, order_num, list_id) VALUES (1, 'Sql level', 6, false, 1, 1);
INSERT INTO field (field_id, name, field_type_id, multiple_choice, order_num, list_id) VALUES (2, 'Java level', 6, false, 2, 1);
INSERT INTO field (field_id, name, field_type_id, multiple_choice, order_num, list_id) VALUES (3, 'Your interests', 5, true, 3, 2);
INSERT INTO field (field_id, name, field_type_id, multiple_choice, order_num, list_id) VALUES (4, 'Where have you found information?', 6, false, 4, 3);
INSERT INTO field (field_id, name, field_type_id, multiple_choice, order_num, list_id) VALUES (5, 'Phone number', 7, false, 5, NULL);
INSERT INTO field (field_id, name, field_type_id, multiple_choice, order_num, list_id) VALUES (6, 'University', 4, false, 6, 4);
INSERT INTO field (field_id, name, field_type_id, multiple_choice, order_num, list_id) VALUES (7, 'Text field', 2, false, 7, NULL);
INSERT INTO field (field_id, name, field_type_id, multiple_choice, order_num, list_id) VALUES (8, 'Kak dela?', 3, false, 8, NULL);


--
-- TOC entry 2174 (class 0 OID 0)
-- Dependencies: 198
-- Name: field_field_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('field_field_id_seq', 8, true);


--
-- TOC entry 2144 (class 0 OID 18789)
-- Dependencies: 197
-- Data for Name: field_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO field_type (field_type_id, name) VALUES (1, 'number');
INSERT INTO field_type (field_type_id, name) VALUES (2, 'text');
INSERT INTO field_type (field_type_id, name) VALUES (3, 'textarea');
INSERT INTO field_type (field_type_id, name) VALUES (4, 'select');
INSERT INTO field_type (field_type_id, name) VALUES (5, 'checkbox');
INSERT INTO field_type (field_type_id, name) VALUES (6, 'radio');
INSERT INTO field_type (field_type_id, name) VALUES (7, 'tel');
INSERT INTO field_type (field_type_id, name) VALUES (8, 'date');


--
-- TOC entry 2175 (class 0 OID 0)
-- Dependencies: 196
-- Name: field_type_field_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('field_type_field_type_id_seq', 8, true);


--
-- TOC entry 2148 (class 0 OID 18831)
-- Dependencies: 201
-- Data for Name: field_value; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (1, 1, NULL, NULL, NULL, 9);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (2, 1, NULL, NULL, NULL, 6);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (3, 1, NULL, NULL, NULL, 12);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (3, 1, NULL, NULL, NULL, 13);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (4, 1, NULL, NULL, NULL, 16);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (6, 1, NULL, NULL, NULL, 21);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (7, 1, 'Hello', NULL, NULL, NULL);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (5, 1, '12345', NULL, NULL, NULL);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (8, 1, 'Very goooood', NULL, NULL, NULL);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (1, 4, NULL, NULL, NULL, 11);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (2, 4, NULL, NULL, NULL, 11);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (3, 4, NULL, NULL, NULL, 15);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (4, 4, NULL, NULL, NULL, 18);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (5, 4, '+380660000000', NULL, NULL, NULL);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (6, 4, NULL, NULL, NULL, 23);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (7, 4, 'ssss', NULL, NULL, NULL);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (8, 4, 'norm', NULL, NULL, NULL);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (1, 9, NULL, NULL, NULL, 6);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (2, 9, NULL, NULL, NULL, 11);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (3, 9, NULL, NULL, NULL, 12);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (3, 9, NULL, NULL, NULL, 13);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (4, 9, NULL, NULL, NULL, 16);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (5, 9, '+380999999999', NULL, NULL, NULL);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (7, 9, 'fdsfsf', NULL, NULL, NULL);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (8, 9, 'sdfsdfsfdsf', NULL, NULL, NULL);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (1, 11, NULL, NULL, NULL, 8);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (2, 11, NULL, NULL, NULL, 4);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (3, 11, NULL, NULL, NULL, 13);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (3, 11, NULL, NULL, NULL, 14);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (4, 11, NULL, NULL, NULL, 17);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (5, 11, '+380999999999', NULL, NULL, NULL);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (7, 11, 'fsdfsff', NULL, NULL, NULL);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (8, 11, 'cdfdsfsdfdf', NULL, NULL, NULL);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (1, 7, NULL, NULL, NULL, 11);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (2, 7, NULL, NULL, NULL, 11);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (3, 7, NULL, NULL, NULL, 14);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (3, 7, NULL, NULL, NULL, 15);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (4, 7, NULL, NULL, NULL, 18);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (5, 7, '+380662281488', NULL, NULL, NULL);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (6, 7, NULL, NULL, NULL, 22);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (7, 7, 'aadw', NULL, NULL, NULL);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (8, 7, 'xxx', NULL, NULL, NULL);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (1, 20, NULL, NULL, NULL, 7);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (2, 20, NULL, NULL, NULL, 3);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (3, 20, NULL, NULL, NULL, 14);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (3, 20, NULL, NULL, NULL, 15);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (4, 20, NULL, NULL, NULL, 16);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (5, 20, '+380999999999', NULL, NULL, NULL);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (7, 20, 'sdfsdfsf', NULL, NULL, NULL);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (8, 20, 'fsdfdsfsfs', NULL, NULL, NULL);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (1, 25, NULL, NULL, NULL, 7);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (2, 25, NULL, NULL, NULL, 7);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (3, 25, NULL, NULL, NULL, 13);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (3, 25, NULL, NULL, NULL, 14);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (4, 25, NULL, NULL, NULL, 17);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (5, 25, '+380999999999', NULL, NULL, NULL);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (7, 25, 'sdfsdffds', NULL, NULL, NULL);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (8, 25, 'sdfsdfsdfsdfsfds', NULL, NULL, NULL);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (1, 17, NULL, NULL, NULL, 11);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (2, 17, NULL, NULL, NULL, 11);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (3, 17, NULL, NULL, NULL, 15);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (4, 17, NULL, NULL, NULL, 17);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (5, 17, '+380660000000', NULL, NULL, NULL);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (7, 17, 'aa', NULL, NULL, NULL);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (8, 17, 'wsw', NULL, NULL, NULL);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (1, 36, NULL, NULL, NULL, 11);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (2, 36, NULL, NULL, NULL, 11);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (3, 36, NULL, NULL, NULL, 14);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (3, 36, NULL, NULL, NULL, 15);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (4, 36, NULL, NULL, NULL, 18);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (5, 36, '+380662281488', NULL, NULL, NULL);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (6, 36, NULL, NULL, NULL, 22);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (7, 36, 'aadw', NULL, NULL, NULL);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (8, 36, 'xxx', NULL, NULL, NULL);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (1, 38, NULL, NULL, NULL, 11);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (2, 38, NULL, NULL, NULL, 11);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (3, 38, NULL, NULL, NULL, 14);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (3, 38, NULL, NULL, NULL, 15);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (4, 38, NULL, NULL, NULL, 18);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (5, 38, '+380662281488', NULL, NULL, NULL);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (6, 38, NULL, NULL, NULL, 22);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (7, 38, 'aadw', NULL, NULL, NULL);
INSERT INTO field_value (field_id, application_id, value_text, value_double, value_date, list_value_id) VALUES (8, 38, 'xxx', NULL, NULL, NULL);


--
-- TOC entry 2138 (class 0 OID 18743)
-- Dependencies: 191
-- Data for Name: interviewee; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO interviewee (application_id, interview_time, special_mark, dev_feedback_id, hr_feedback_id) VALUES (1, '2016-05-10 00:00:00', NULL, 7, 9);


--
-- TOC entry 2135 (class 0 OID 18708)
-- Dependencies: 188
-- Data for Name: interviewer_participation; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO interviewer_participation (system_user_id, ces_id) VALUES (1, 1);
INSERT INTO interviewer_participation (system_user_id, ces_id) VALUES (5, 1);


--
-- TOC entry 2140 (class 0 OID 18765)
-- Dependencies: 193
-- Data for Name: list; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO list (list_id, name) VALUES (1, 'Marks');
INSERT INTO list (list_id, name) VALUES (2, 'Interests');
INSERT INTO list (list_id, name) VALUES (3, 'Information');
INSERT INTO list (list_id, name) VALUES (4, 'University');


--
-- TOC entry 2176 (class 0 OID 0)
-- Dependencies: 192
-- Name: list_list_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('list_list_id_seq', 4, true);


--
-- TOC entry 2142 (class 0 OID 18773)
-- Dependencies: 195
-- Data for Name: list_value; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO list_value (list_value_id, list_id, value_text) VALUES (1, 1, '0');
INSERT INTO list_value (list_value_id, list_id, value_text) VALUES (2, 1, '1');
INSERT INTO list_value (list_value_id, list_id, value_text) VALUES (3, 1, '2');
INSERT INTO list_value (list_value_id, list_id, value_text) VALUES (4, 1, '3');
INSERT INTO list_value (list_value_id, list_id, value_text) VALUES (5, 1, '4');
INSERT INTO list_value (list_value_id, list_id, value_text) VALUES (6, 1, '5');
INSERT INTO list_value (list_value_id, list_id, value_text) VALUES (7, 1, '6');
INSERT INTO list_value (list_value_id, list_id, value_text) VALUES (8, 1, '7');
INSERT INTO list_value (list_value_id, list_id, value_text) VALUES (9, 1, '8');
INSERT INTO list_value (list_value_id, list_id, value_text) VALUES (10, 1, '9');
INSERT INTO list_value (list_value_id, list_id, value_text) VALUES (11, 1, '10');
INSERT INTO list_value (list_value_id, list_id, value_text) VALUES (12, 2, 'Research');
INSERT INTO list_value (list_value_id, list_id, value_text) VALUES (13, 2, 'Practice');
INSERT INTO list_value (list_value_id, list_id, value_text) VALUES (14, 2, 'New knowledge');
INSERT INTO list_value (list_value_id, list_id, value_text) VALUES (15, 2, 'Work in NC');
INSERT INTO list_value (list_value_id, list_id, value_text) VALUES (16, 3, 'VK');
INSERT INTO list_value (list_value_id, list_id, value_text) VALUES (17, 3, 'Facebook');
INSERT INTO list_value (list_value_id, list_id, value_text) VALUES (18, 3, 'Our site');
INSERT INTO list_value (list_value_id, list_id, value_text) VALUES (19, 3, 'Other people');
INSERT INTO list_value (list_value_id, list_id, value_text) VALUES (20, 3, 'TV');
INSERT INTO list_value (list_value_id, list_id, value_text) VALUES (21, 4, 'KPI');
INSERT INTO list_value (list_value_id, list_id, value_text) VALUES (22, 4, 'KNU');
INSERT INTO list_value (list_value_id, list_id, value_text) VALUES (23, 4, 'NAUKMA');


--
-- TOC entry 2177 (class 0 OID 0)
-- Dependencies: 194
-- Name: list_value_list_value_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('list_value_list_value_id_seq', 23, true);


--
-- TOC entry 2119 (class 0 OID 18591)
-- Dependencies: 172
-- Data for Name: report_template; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO report_template (report_id, query, name) VALUES (2, 'select * from public.system_user', 'user');
INSERT INTO report_template (report_id, query, name) VALUES (1, 'select * from public.role', 'role');


--
-- TOC entry 2178 (class 0 OID 0)
-- Dependencies: 171
-- Name: report_template_report_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('report_template_report_id_seq', 3, true);


--
-- TOC entry 2121 (class 0 OID 18602)
-- Dependencies: 174
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO role (role_id, name, description) VALUES (1, 'ROLE_ADMIN', 'Admin of the system');
INSERT INTO role (role_id, name, description) VALUES (2, 'ROLE_STUDENT', 'Student who wants to/takes part in the course');
INSERT INTO role (role_id, name, description) VALUES (3, 'ROLE_HR', 'HR of Netcracker');
INSERT INTO role (role_id, name, description) VALUES (4, 'ROLE_DEV', 'Developer of Netcracker');
INSERT INTO role (role_id, name, description) VALUES (5, 'ROLE_BA', 'BA of Netcracker');


--
-- TOC entry 2179 (class 0 OID 0)
-- Dependencies: 173
-- Name: role_role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('role_role_id_seq', 5, true);


--
-- TOC entry 2125 (class 0 OID 18625)
-- Dependencies: 178
-- Data for Name: system_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (1, 'ion@gmail.com', '$2a$10$U2HGciX6QWTwus5SEGVKgeCu/OHH7DXSwwBVQaGjO7o.mKyC8J4pq', 'Ion', 'Ionets', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (2, 'rom@gmail.com', '$2a$10$lS6mQkw2ejU.JZjgIFVQAeFemX0Dg9gO9k5Y.AkHW6VeckwNL3Qkm', 'Roman', 'Andriichuk', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (3, 'sasha@gmail.com', '$2a$10$bflfk70LoK2kf4DRA2TXo.M43YjvMKFYfEKgwaEqq2rnEleXBgOI.', 'Sasha', 'Beskorovaynaya', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (4, 'kirill@gmail.com', '$2a$10$9uck3uR.cTwo9RNye0IcF.Us09QlaOGetAcXxj0QOfdvrxwUVm3ES', 'Kirill', 'Tumanov', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (5, 'hr@gmail.com', '$2a$10$c6ecTMnSeEgrLZxKOaIR1eFIQ5wUkNNWIw0kj1rowiR7gUDUkZFnG', 'HR', 'HRets', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (6, 'student@gmail.com', '$2a$10$vIHkLgH6ZVOzwCwrYkl7JeZQGiTaGvcIj4Dnln6Xyynt7Fvf7tHry', 'Student', 'Studentets', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (7, 'jey@gmail.com', '$2a$10$CIr04rSFDxEVm8ryFX9gr.KAeX/xYgbcmUWgKG.DsTe56w7BBMyCG', 'Jason', 'Statham', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (8, 'kyryl@gmail.com', '$2a$10$xkRqkLiQ7as5skFadmjgF.5z3uzQOEzRlSMg2Ri55pJdWXh3UuBqe', 'kyrylkyryl', 'kyrylkyryl', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (9, 'kkk@gmail.com', '$2a$10$fDgkvkqzfEp9l8/O2P11KeGG3lRh2ZjX1WBWh2AQ.pFDy9wvIhrhO', 'Allah', 'Allah', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (10, 'dev@gmail.com', '$2a$10$61okqYxgFgrKZq786jYXce.wTBIV6Y631fzKNI.RQv.HJX3vQRcU.', 'Dev', 'Dev', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (11, 'admindev@gmail.com', '$2a$10$svRLJq5..DWltE98rV2VouiICxdZVONtcgx2UgVEp15DTkS3unZd6', 'AdminDev', 'AdminDev', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (12, 'adminhr@gmail.com', '$2a$10$pcmNGc1Hiz2g.zh5eDIaweIIhboACnd9E7lUoQ/paoy0KQPRIOY86', 'Adminhr', 'Adminhr', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (13, 'adminba@gmail.com', '$2a$10$V0Bki2Tpm/Zcrd/OobBPku84m2Q3t8tOKqG3FLMXmaHNhAsvCiKiq', 'Adminba', 'Adminba', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (14, 'admin@gmail.com', '$2a$10$43UP/XiBE3VZtvoMUhXu/eiMYLTNf03zQEq.Lm6c/JbKRKlj8FITC', 'Admin', 'Admin', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (15, 'ggg@gmail.com', '$2a$10$OUvvbHOgPnIe9.VIcGD.2uRgsXYWhU8jmThilFkuy00Tj01FMvm0O', 'Allah', 'Allah', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (16, 'admin1@gmail.com', '$2a$10$ajBLt0wGL5ISdocpbLXDHeZRwO8lVgYH1TJho180guAvp/QqZlE4O', 'Admin', 'Admin', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (17, 'barack@gmail.com', '$2a$10$vbD9MQzZIp4LtHjLPhtRjOScQHoiJQvOqplhf3WUEv/mGxICnPdiG', 'Barack', 'Obama', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (18, 'admin2@gmail.com', '$2a$10$BzXJMpuPJyPHXjClkBZq5.oJ8XGblWfhA7UDNXoo8TXH0DrjSS6cO', 'Admin', 'Admin', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (19, 'stud1@gmail.com', '$2a$10$X5icJscrH4NLD8orOfdaa.T5YghY2myehJl5f9e7GV0RFhOPzbHIy', 'Stud', 'Stud', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (20, 'stud133@gmail.com', '$2a$10$lTFAczhW7bfNfNhhSpofaOiL3xwifrRC2rZN1yXX.CD6qn1jurYO2', 'Stud', 'Stud', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (21, 'stud222@gmail.com', '$2a$10$R23WTVQGmuRTO.z0rbGFoOg8Jyr/bIR.oW.vQRN1Hci1bP8lNspzK', 'Stud', 'Stud', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (22, 'dog@gmail.com', '$2a$10$nkims/ErtASdCtqHwwVIdOlBgoZg/fVyBc9zAoIPoj94Ye10KQ16K', 'Sobaka', 'Sobaka', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (23, 'stud143@gmail.com', '$2a$10$hJB1X4k8zjuLb1BzFBLqhet3mvfDr3drm/JHWWNXpfKmszHwU0CRS', 'Stud', 'Stud', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (24, 'stud@yandex.ru', '$2a$10$9xRuhF7NSZWGQXxYojluIuyiRxDPIjhUHqzJmJ.AcYy7XuRwv4ZdS', 'Stud', 'Stud', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (25, 'fffffff@gmai.com', '$2a$10$AZGw0UHR./y0/jljMxE4vOKiyN58LZUiKlbqKXjtt3gZ.SxctghWO', 'Ffffff', 'Ffffff', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (26, 'gggggg@gmail.com', '$2a$10$Flpnwm9osclu2AG7CmHDTO.CEhrcbUdMX3CWpWLOK/.Up1MPL68Ea', 'Gggggg', 'Gggggg', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (27, 'hhhhhh@gmai.com', '$2a$10$7LF/D4MnAqjlniswz0z20u/uGf1TmGNzKH9fmAU/apLIfPEZAqKom', 'Hhhhhh', 'Ghhhhh', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (28, 'tttttt@gmail.com', '$2a$10$U/Hxe599A4CrT656roGZ4.OR.km.Y96KCNC6zgUsdM6WJVhlqA5UC', 'Fdfsdffsdf', 'Fsdfsdfsff', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (29, 'jihad@gmail.com', '$2a$10$UR0y1JHUF6iQLf98XGPRHO7TqjiP/tbqYDZRJHHgrW9BdDzWC0VwG', 'Murar', 'Jihad', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (30, 'kol@gmail.com', '$2a$10$UNHHoYStdLqZ/f47G8NH9u43BO3CAeHOInmnIM7wtyZWtaxVSwSkq', 'Kol', 'Kol', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (32, 'ko95@bk.ru', '$2a$10$/mBFOJlHR/4mNrPy4DlQnOcfoEG1a.nXJT4jzctu12aXM/00SrfZK', 'Kost', 'Kost', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (33, 'adddddd@gmail.com', '$2a$10$Hedq4d4jgKp5DQiGpeB0h.DEAnKmL2wm7AzZza2sUYLlBOeucRs4O', 'Admin', 'Admin', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (34, 'sdsdasda@g.com', '$2a$10$UMYk08Hvqjb2v6b9JG80ueG4XRpxPCSk7DJ/lIFqBD7a1T589kEeG', 'Adasdadd', 'Adasdadsad', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (35, 'sdfsfsdf@ggs.com', '$2a$10$M7HFTUoOhti8FYrwuixPqeNU2bOnv4FWVfXSKNxeG2TGEhpWOihLq', 'Ddddddddddd', 'Ddddddddddd', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (36, 'sdfsdf@fdsfs.com', '$2a$10$d.fHkTSsg/G/.4z9WntkwObSDPzOcIcuJ/g0bRnSkGoKg.IZW0NMK', 'Fsdfsdfsf', 'Fdsfsdfsfsf', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (37, 'sdfsdf@fsdf.com', '$2a$10$BO8eBaZ93qlR3V5ro..e0u6WwZVwpOPDwb.SxlPjnVod5l2uImBUC', 'Fdsfsfdsf', 'Fdsfsdfsdf', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (38, 'sdfsdffs@sfsdfs.com', '$2a$10$Rp3shA8V/mQupTjHLViWnOqnXQYs9ylzbBdDAIYRABCru2XSBCtum', 'Fsdfsdfsf', 'Fsdfsfdsdf', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (39, 'sdffd@fsdfsdf.com', '$2a$10$Dh36u.ZM0KbWHknsxXgYIumUKhyiZmJ5qYFiK0zdpe5WCntnQNFOi', 'Ffdssfsfdffsf', 'Ffdsfsfsf', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (41, 'koh95@bk.ru', '$2a$10$cp/ayutUdVhyezrTXuud/uX6gkpZzKcppAGv4ZrLSTeSrsSGmCNsC', 'Kos', 'Kos', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (40, 'galina@gmail.com', '$2a$10$TEJg4DbiytCXDFEpRtkOVeHv9WnnfnBQml6w6E0z/S7Msoomao7CK', 'Галина', 'Галинова', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (42, 'name@name.name', '$2a$10$6b520RpPbvcx6K2wKwDTC.eKWJv6v6BIcUgD3t9lC4CAUb9m52qrG', 'Name', 'Name', 1);
INSERT INTO system_user (system_user_id, email, password, name, surname, system_user_status_id) VALUES (31, 'kostharl@gmail.com', '$2a$10$/QlAxxXa4SvGCtvYuuUpCe382FwOYH/KVrYAy3vDKnar4NkaGc3Um', 'Kons', 'Kons', 1);


--
-- TOC entry 2126 (class 0 OID 18638)
-- Dependencies: 179
-- Data for Name: system_user_role; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO system_user_role (system_user_id, role_id) VALUES (1, 1);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (1, 4);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (2, 4);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (3, 4);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (4, 5);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (5, 3);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (6, 2);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (7, 1);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (7, 3);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (8, 1);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (8, 5);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (9, 2);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (10, 4);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (11, 4);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (11, 1);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (12, 1);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (12, 3);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (13, 1);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (13, 5);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (14, 1);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (14, 3);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (15, 2);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (16, 3);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (17, 2);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (18, 4);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (18, 1);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (19, 2);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (20, 2);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (21, 2);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (22, 2);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (23, 2);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (24, 2);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (25, 2);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (26, 2);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (27, 2);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (28, 2);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (29, 2);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (30, 2);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (31, 2);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (32, 2);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (33, 1);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (33, 3);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (34, 3);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (35, 3);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (36, 3);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (37, 3);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (38, 3);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (39, 3);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (40, 5);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (40, 4);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (40, 3);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (40, 1);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (41, 2);
INSERT INTO system_user_role (system_user_id, role_id) VALUES (42, 2);


--
-- TOC entry 2123 (class 0 OID 18615)
-- Dependencies: 176
-- Data for Name: system_user_status; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO system_user_status (system_user_status_id, name) VALUES (1, 'Active');
INSERT INTO system_user_status (system_user_status_id, name) VALUES (2, 'Inactive');


--
-- TOC entry 2180 (class 0 OID 0)
-- Dependencies: 175
-- Name: system_user_status_system_user_status_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('system_user_status_system_user_status_id_seq', 2, true);


--
-- TOC entry 2181 (class 0 OID 0)
-- Dependencies: 177
-- Name: system_user_system_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('system_user_system_user_id_seq', 42, true);


--
-- TOC entry 1974 (class 2606 OID 18730)
-- Name: application_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY application
    ADD CONSTRAINT application_pkey PRIMARY KEY (application_id);


--
-- TOC entry 1976 (class 2606 OID 18732)
-- Name: application_system_user_id_ces_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY application
    ADD CONSTRAINT application_system_user_id_ces_id_key UNIQUE (system_user_id, ces_id);


--
-- TOC entry 1990 (class 2606 OID 18820)
-- Name: ces_field_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ces_field
    ADD CONSTRAINT ces_field_pkey PRIMARY KEY (ces_id, field_id);


--
-- TOC entry 1968 (class 2606 OID 18687)
-- Name: ces_status_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ces_status
    ADD CONSTRAINT ces_status_pkey PRIMARY KEY (ces_status_id);


--
-- TOC entry 1970 (class 2606 OID 18702)
-- Name: course_enrollment_session_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY course_enrollment_session
    ADD CONSTRAINT course_enrollment_session_pkey PRIMARY KEY (ces_id);


--
-- TOC entry 1966 (class 2606 OID 18679)
-- Name: email_template_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY email_template
    ADD CONSTRAINT email_template_pkey PRIMARY KEY (email_template_id);


--
-- TOC entry 1964 (class 2606 OID 18663)
-- Name: feedback_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY feedback
    ADD CONSTRAINT feedback_pkey PRIMARY KEY (feedback_id);


--
-- TOC entry 1988 (class 2606 OID 18805)
-- Name: field_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY field
    ADD CONSTRAINT field_pkey PRIMARY KEY (field_id);


--
-- TOC entry 1984 (class 2606 OID 18796)
-- Name: field_type_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY field_type
    ADD CONSTRAINT field_type_name_key UNIQUE (name);


--
-- TOC entry 1986 (class 2606 OID 18794)
-- Name: field_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY field_type
    ADD CONSTRAINT field_type_pkey PRIMARY KEY (field_type_id);


--
-- TOC entry 1978 (class 2606 OID 18747)
-- Name: interviewee_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY interviewee
    ADD CONSTRAINT interviewee_pkey PRIMARY KEY (application_id);


--
-- TOC entry 1972 (class 2606 OID 18712)
-- Name: interviewer_participation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY interviewer_participation
    ADD CONSTRAINT interviewer_participation_pkey PRIMARY KEY (system_user_id, ces_id);


--
-- TOC entry 1980 (class 2606 OID 18770)
-- Name: list_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY list
    ADD CONSTRAINT list_pkey PRIMARY KEY (list_id);


--
-- TOC entry 1982 (class 2606 OID 18781)
-- Name: list_value_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY list_value
    ADD CONSTRAINT list_value_pkey PRIMARY KEY (list_value_id);


--
-- TOC entry 1948 (class 2606 OID 18599)
-- Name: report_template_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY report_template
    ADD CONSTRAINT report_template_pkey PRIMARY KEY (report_id);


--
-- TOC entry 1950 (class 2606 OID 18612)
-- Name: role_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY role
    ADD CONSTRAINT role_name_key UNIQUE (name);


--
-- TOC entry 1952 (class 2606 OID 18610)
-- Name: role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY role
    ADD CONSTRAINT role_pkey PRIMARY KEY (role_id);


--
-- TOC entry 1958 (class 2606 OID 18632)
-- Name: system_user_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY system_user
    ADD CONSTRAINT system_user_email_key UNIQUE (email);


--
-- TOC entry 1960 (class 2606 OID 18630)
-- Name: system_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY system_user
    ADD CONSTRAINT system_user_pkey PRIMARY KEY (system_user_id);


--
-- TOC entry 1962 (class 2606 OID 18642)
-- Name: system_user_role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY system_user_role
    ADD CONSTRAINT system_user_role_pkey PRIMARY KEY (system_user_id, role_id);


--
-- TOC entry 1954 (class 2606 OID 18622)
-- Name: system_user_status_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY system_user_status
    ADD CONSTRAINT system_user_status_name_key UNIQUE (name);


--
-- TOC entry 1956 (class 2606 OID 18620)
-- Name: system_user_status_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY system_user_status
    ADD CONSTRAINT system_user_status_pkey PRIMARY KEY (system_user_status_id);


--
-- TOC entry 1999 (class 2606 OID 18738)
-- Name: application_ces_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY application
    ADD CONSTRAINT application_ces_id_fkey FOREIGN KEY (ces_id) REFERENCES course_enrollment_session(ces_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 1998 (class 2606 OID 18733)
-- Name: application_system_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY application
    ADD CONSTRAINT application_system_user_id_fkey FOREIGN KEY (system_user_id) REFERENCES system_user(system_user_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2006 (class 2606 OID 18821)
-- Name: ces_field_ces_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ces_field
    ADD CONSTRAINT ces_field_ces_id_fkey FOREIGN KEY (ces_id) REFERENCES course_enrollment_session(ces_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2007 (class 2606 OID 18826)
-- Name: ces_field_field_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ces_field
    ADD CONSTRAINT ces_field_field_id_fkey FOREIGN KEY (field_id) REFERENCES field(field_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 1995 (class 2606 OID 18703)
-- Name: course_enrollment_session_ces_status_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY course_enrollment_session
    ADD CONSTRAINT course_enrollment_session_ces_status_id_fkey FOREIGN KEY (ces_status_id) REFERENCES ces_status(ces_status_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 1994 (class 2606 OID 18664)
-- Name: feedback_interviewer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY feedback
    ADD CONSTRAINT feedback_interviewer_id_fkey FOREIGN KEY (interviewer_id) REFERENCES system_user(system_user_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2004 (class 2606 OID 18806)
-- Name: field_field_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY field
    ADD CONSTRAINT field_field_type_id_fkey FOREIGN KEY (field_type_id) REFERENCES field_type(field_type_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2005 (class 2606 OID 18811)
-- Name: field_list_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY field
    ADD CONSTRAINT field_list_id_fkey FOREIGN KEY (list_id) REFERENCES list(list_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2009 (class 2606 OID 18843)
-- Name: field_value_application_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY field_value
    ADD CONSTRAINT field_value_application_id_fkey FOREIGN KEY (application_id) REFERENCES application(application_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2008 (class 2606 OID 18838)
-- Name: field_value_field_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY field_value
    ADD CONSTRAINT field_value_field_id_fkey FOREIGN KEY (field_id) REFERENCES field(field_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2010 (class 2606 OID 18848)
-- Name: field_value_list_value_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY field_value
    ADD CONSTRAINT field_value_list_value_id_fkey FOREIGN KEY (list_value_id) REFERENCES list_value(list_value_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2000 (class 2606 OID 18748)
-- Name: interviewee_application_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY interviewee
    ADD CONSTRAINT interviewee_application_id_fkey FOREIGN KEY (application_id) REFERENCES application(application_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2001 (class 2606 OID 18753)
-- Name: interviewee_dev_feedback_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY interviewee
    ADD CONSTRAINT interviewee_dev_feedback_id_fkey FOREIGN KEY (dev_feedback_id) REFERENCES feedback(feedback_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2002 (class 2606 OID 18758)
-- Name: interviewee_hr_feedback_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY interviewee
    ADD CONSTRAINT interviewee_hr_feedback_id_fkey FOREIGN KEY (hr_feedback_id) REFERENCES feedback(feedback_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 1997 (class 2606 OID 18718)
-- Name: interviewer_participation_ces_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY interviewer_participation
    ADD CONSTRAINT interviewer_participation_ces_id_fkey FOREIGN KEY (ces_id) REFERENCES course_enrollment_session(ces_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 1996 (class 2606 OID 18713)
-- Name: interviewer_participation_system_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY interviewer_participation
    ADD CONSTRAINT interviewer_participation_system_user_id_fkey FOREIGN KEY (system_user_id) REFERENCES system_user(system_user_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2003 (class 2606 OID 18782)
-- Name: list_value_list_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY list_value
    ADD CONSTRAINT list_value_list_id_fkey FOREIGN KEY (list_id) REFERENCES list(list_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 1993 (class 2606 OID 18648)
-- Name: system_user_role_role_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY system_user_role
    ADD CONSTRAINT system_user_role_role_id_fkey FOREIGN KEY (role_id) REFERENCES role(role_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 1992 (class 2606 OID 18643)
-- Name: system_user_role_system_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY system_user_role
    ADD CONSTRAINT system_user_role_system_user_id_fkey FOREIGN KEY (system_user_id) REFERENCES system_user(system_user_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 1991 (class 2606 OID 18633)
-- Name: system_user_system_user_status_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY system_user
    ADD CONSTRAINT system_user_system_user_status_id_fkey FOREIGN KEY (system_user_status_id) REFERENCES system_user_status(system_user_status_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2154 (class 0 OID 0)
-- Dependencies: 7
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2016-05-11 13:16:53

--
-- PostgreSQL database dump complete
--


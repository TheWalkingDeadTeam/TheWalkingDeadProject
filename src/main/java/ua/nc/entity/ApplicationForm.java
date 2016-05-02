package ua.nc.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Neltarion on 23.04.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationForm {

    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private String university;
    private Integer course;
    private List programingLanguages;
    private List interests;
    private List hobbies;
    private String kakUznali;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }


    public List getProgramingLanguages() {
        return programingLanguages;
    }

    public void setProgramingLanguages(List programingLanguages) {
        this.programingLanguages = programingLanguages;
    }

    public List getInterests() {
        return interests;
    }

    public void setInterests(List interests) {
        this.interests = interests;
    }

    public List getHobbies() {
        return hobbies;
    }

    public void setHobbies(List hobbies) {
        this.hobbies = hobbies;
    }

    public String getKakUznali() {
        return kakUznali;
    }

    public void setKakUznali(String kakUznali) {
        this.kakUznali = kakUznali;
    }

    @Override
    public String toString() {
        return "ApplicationForm{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", university='" + university + '\'' +
                ", course=" + course +
                ", programingLanguages=" + programingLanguages +
                ", interests=" + interests +
                ", hobbies=" + hobbies +
                ", kakUznali='" + kakUznali + '\'' +
                '}';
    }
}

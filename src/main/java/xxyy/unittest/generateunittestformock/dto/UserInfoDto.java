package xxyy.unittest.generateunittestformock.dto;

import java.io.Serializable;
import java.util.Date;

public class UserInfoDto implements Serializable {


    private String name;

    private int age;

    private boolean gender;

    private String phoneNumber;

    private Date birthDay;

    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "UserInfoDto{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthDay=" + birthDay +
                ", address='" + address + '\'' +
                '}';
    }
}

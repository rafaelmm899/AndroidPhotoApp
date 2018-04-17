package com.softmedialtda.softmediaphotoapp.models;

import java.io.Serializable;

/**
 * Created by Agustin on 28/3/2018.
 */

public class User implements Serializable {
    private String username;
    private String password;
    private int id; //id de la tabla user
    private String firstName;
    private String secondName;
    private String surname;
    private String secondSurname;
    private String nDocument;
    private int idStudent;
    private String firstNameStudent;
    private String secondNameStudent;
    private String surnameStudent;
    private String secondSurnameStudent;
    private String nDocumentStudent;
    private String typeUser;
    private int idInstitution;
    private int idTypeUser; //id__per
    private String imageProfile;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, int id, String firstName, String secondName, String surname, String secondSurname, String nDocument,String typeUser,int idInstitution, int idTypeUser, String imageProfile) {
        this.username = username;
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.surname = surname;
        this.secondSurname = secondSurname;
        this.nDocument = nDocument;
        this.typeUser = typeUser;
        this.idInstitution = idInstitution;
        this.idTypeUser = idTypeUser;
        this.imageProfile = imageProfile;
    }

    public User(String username, int id, String firstName, String surname,String typeUser, String imageProfile) {
        this.username = username;
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.typeUser = typeUser;
        this.imageProfile = imageProfile;
    }

    public User(String username, int id, String firstName, String secondName, String surname, String secondSurname, String nDocument, int idStudent, String firstNameStudent, String secondNameStudent, String surnameStudent, String secondSurnameStudent, String nDocumentStudent,String typeUser,int idInstitution, int idTypeUser, String imageProfile) {
        this.username = username;
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.surname = surname;
        this.secondSurname = secondSurname;
        this.nDocument = nDocument;
        this.idStudent = idStudent;
        this.firstNameStudent = firstNameStudent;
        this.secondNameStudent = secondNameStudent;
        this.surnameStudent = surnameStudent;
        this.secondSurnameStudent = secondSurnameStudent;
        this.nDocumentStudent = nDocumentStudent;
        this.typeUser = typeUser;
        this.idInstitution = idInstitution;
        this.idTypeUser = idTypeUser;
        this.imageProfile = imageProfile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSecondSurname() {
        return secondSurname;
    }

    public void setSecondSurname(String secondSurname) {
        this.secondSurname = secondSurname;
    }

    public String getnDocument() {
        return nDocument;
    }

    public void setnDocument(String nDocument) {
        this.nDocument = nDocument;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public String getFirstNameStudent() {
        return firstNameStudent;
    }

    public void setFirstNameStudent(String firstNameStudent) {
        this.firstNameStudent = firstNameStudent;
    }

    public String getSecondNameStudent() {
        return secondNameStudent;
    }

    public void setSecondNameStudent(String secondNameStudent) {
        this.secondNameStudent = secondNameStudent;
    }

    public String getSurnameStudent() {
        return surnameStudent;
    }

    public void setSurnameStudent(String surnameStudent) {
        this.surnameStudent = surnameStudent;
    }

    public String getSecondSurnameStudent() {
        return secondSurnameStudent;
    }

    public void setSecondSurnameStudent(String secondSurnameStudent) {
        this.secondSurnameStudent = secondSurnameStudent;
    }

    public String getnDocumentStudent() {
        return nDocumentStudent;
    }

    public void setnDocumentStudent(String nDocumentStudent) {
        this.nDocumentStudent = nDocumentStudent;
    }

    public String getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }

    public int getIdInstitution() {
        return idInstitution;
    }

    public void setIdInstitution(int idInstitution) {
        this.idInstitution = idInstitution;
    }

    public int getIdTypeUser() {
        return idTypeUser;
    }

    public void setIdTypeUser(int idTypeUser) {
        this.idTypeUser = idTypeUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(String imageProfile) {
        this.imageProfile = imageProfile;
    }
}

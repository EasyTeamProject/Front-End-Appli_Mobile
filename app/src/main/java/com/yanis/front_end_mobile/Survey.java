package com.yanis.front_end_mobile;

public class Survey {
    String id;
    String id_event;
    String nameUser;
    String question;
    Answer answerOne;
    Answer answerTwo;

    public Survey(String id, String id_event, String nameUser, String question) {
        this.id = id;
        this.id_event = id_event;
        this.nameUser = nameUser;
        this.question = question;
    }

    public Survey(String id, String id_event, String nameUser, String question, Answer answerOne, Answer answerTwo) {
        this.id = id;
        this.id_event = id_event;
        this.nameUser = nameUser;
        this.question = question;
        this.answerOne = answerOne;
        this.answerTwo = answerTwo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_event() {
        return id_event;
    }

    public void setId_event(String id_event) {
        this.id_event = id_event;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Answer getAnswerOne() {
        return answerOne;
    }

    public void setAnswerOne(Answer answerOne) {
        this.answerOne = answerOne;
    }

    public Answer getAnswerTwo() {
        return answerTwo;
    }

    public void setAnswerTwo(Answer answerTwo) {
        this.answerTwo = answerTwo;
    }
}


class Answer{

    String answer;
    String number_answer;

    public Answer(String answer, String number_answer) {
        this.answer = answer;
        this.number_answer = number_answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getNumber_answer() {
        return number_answer;
    }

    public void setNumber_answer(String number_answer) {
        this.number_answer = number_answer;
    }
}
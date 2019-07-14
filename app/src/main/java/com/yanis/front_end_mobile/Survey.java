package com.yanis.front_end_mobile;

public class Survey {
    String id;
    String id_event;
    String question;
    String answerOne;
    long numberAnswerOne;
    String answerTwo;
    long numberAnswerTwo;

    public Survey(String id_event, String question, String answerOne, long numberAnswerOne, String answerTwo, long numberAnswerTwo) {
        this.id_event = id_event;
        this.question = question;
        this.answerOne = answerOne;
        this.numberAnswerOne = numberAnswerOne;
        this.answerTwo = answerTwo;
        this.numberAnswerTwo = numberAnswerTwo;
    }

    public Survey() {
    }

    public Survey(String id, String id_event, String question, String answerOne, long numberAnswerOne, String answerTwo, long numberAnswerTwo) {
        this.id = id;
        this.id_event = id_event;
        this.question = question;
        this.answerOne = answerOne;
        this.numberAnswerOne = numberAnswerOne;
        this.answerTwo = answerTwo;
        this.numberAnswerTwo = numberAnswerTwo;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerOne() {
        return answerOne;
    }

    public void setAnswerOne(String answerOne) {
        this.answerOne = answerOne;
    }

    public long getNumberAnswerOne() {
        return numberAnswerOne;
    }

    public void setNumberAnswerOne(long numberAnswerOne) {
        this.numberAnswerOne = numberAnswerOne;
    }

    public String getAnswerTwo() {
        return answerTwo;
    }

    public void setAnswerTwo(String answerTwo) {
        this.answerTwo = answerTwo;
    }

    public long getNumberAnswerTwo() {
        return numberAnswerTwo;
    }

    public void setNumberAnswerTwo(long numberAnswerTwo) {
        this.numberAnswerTwo = numberAnswerTwo;
    }
}

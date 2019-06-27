package com.yanis.front_end_mobile;

public class ExpenseRefund {
    private String id;
    private String namePerson1;
    private String namePerson2;
    private String price;

    public ExpenseRefund(String id, String namePerson1, String namePerson2, String price) {
        this.id = id;
        this.namePerson1 = namePerson1;
        this.namePerson2 = namePerson2;
        this.price = price;
    }

    public ExpenseRefund(String namePerson1, String namePerson2, String price) {

        this.namePerson1 = namePerson1;
        this.namePerson2 = namePerson2;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamePerson1() {
        return namePerson1;
    }

    public void setNamePerson1(String namePerson1) {
        this.namePerson1 = namePerson1;
    }

    public String getNamePerson2() {
        return namePerson2;
    }

    public void setNamePerson2(String namePerson2) {
        this.namePerson2 = namePerson2;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

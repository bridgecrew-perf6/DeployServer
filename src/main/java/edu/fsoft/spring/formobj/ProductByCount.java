package edu.fsoft.spring.formobj;

public class ProductByCount {
    public String text;
    public float count;

    public ProductByCount() {
    }

    public ProductByCount(String text, float count) {
        this.text = text;
        this.count = count;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
    }


}

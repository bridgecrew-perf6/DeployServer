package edu.fsoft.spring.formobj;

import edu.fsoft.spring.model.Product;

import java.util.ArrayList;
import java.util.List;

public class GlobalData {
    public static List<Product> cart;
    static {cart = new ArrayList<Product>();}
}

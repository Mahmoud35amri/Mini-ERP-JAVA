/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.minierp.util;


import java.util.concurrent.atomic.AtomicInteger;
public class IdGenerator {
    private static final AtomicInteger Counter = new AtomicInteger(0);
   

    public static String generateUserId() {
        return "U" + Counter.getAndIncrement();
    }
    public static String generateProductId() {
        return "P" + Counter.getAndIncrement();
    }
    public static String generateCompanyId() {
        return "C" + Counter.getAndIncrement();
    }
    public static String generateOrderId() {
        return "O" + Counter.getAndIncrement();
    }
    public static String generateClientId() {
        return "CL" + Counter.getAndIncrement();
    }
    public static String generateSupplierId() {
        return "S" + Counter.getAndIncrement();
    }
    public static String generateCategoryId() {
        return "CAT" + Counter.getAndIncrement();
    }
    public static String generateInvoiceId() {
        return "F" + Counter.getAndIncrement();
    }
    public static String generateOrderLineId() {
        return "OL" + Counter.getAndIncrement();
    }
}

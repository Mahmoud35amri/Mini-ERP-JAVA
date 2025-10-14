/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/module-info.java to edit this template
 */

module MiniERP {
    requires java.sql;       // pour la base de données
    exports com.minierp.model;
    exports com.minierp.controller;
    exports com.minierp.service;
    exports com.minierp.view;
    exports com.minierp.util;
}


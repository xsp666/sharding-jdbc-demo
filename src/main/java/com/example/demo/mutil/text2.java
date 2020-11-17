package com.example.demo.mutil;

import org.joda.time.DateTime;

public class text2 {
    public static void  createTable(){
        for (int i = 1; i <788 ; i++) {
            String tableNamePreFix ="t_store_ticketstock_"+new DateTime().plusDays(i).toString("yyyyMMdd");
            String result="create table "+tableNamePreFix +" like t_store_ticketstock_20201101;";
            System.out.println(result);
        }
    }

    public static void  createTableDistributorTicketStock(){
        for (int i = 1; i <788 ; i++) {
            String tableNamePreFix ="t_distributor_ticketstock"+new DateTime().plusDays(i).toString("yyyyMMdd");
            String result="create table "+tableNamePreFix +" like t_distributor_ticketstock_20201101;";
            System.out.println(result);
        }
    }
    public static void  createTableAuditBmScanCode(){
        for (int i = 1; i <788 ; i++) {
            String tableNamePreFix ="t_audit_bm_scan_code"+"_"+new DateTime().plusDays(i).toString("yyyyMMdd");
            String result="create table "+tableNamePreFix +" like t_audit_bm_scan_code;";
            System.out.println(result);
        }
    }

    public static void main(String[] args) {
        createTableAuditBmScanCode();
    }
}

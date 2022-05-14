package com.company.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;

public class AuditService {
    // singleton (lazy initialization)
    private static AuditService auditService;
    private AuditService(){}

    public static AuditService getAuditService(){
        if (auditService == null)
            auditService = new AuditService();
        return auditService;
    }

    public void writeAudit(String command){
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp= Timestamp.from(Instant.now());

        try (BufferedWriter buffer = new BufferedWriter(new FileWriter("D:\\facultate\\PAO\\TicketBookingManagementSystem\\Files\\audit.csv", true))) {
            buffer.write("\n" + command + "," + myFormat.format(timestamp));
            buffer.flush();

        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}

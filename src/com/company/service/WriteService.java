package com.company.service;

import com.company.entity.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class WriteService {
    // singleton (lazy initialization)
    private static WriteService writeService;
    private WriteService() {}

    public static WriteService getWriteService() {
        if (writeService == null)
            writeService = new WriteService();
        return writeService;
    }

    public void addUser(int id, String username, String password, String path){
        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(path, true))) {
            buffer.write("\n" +id + "," + username + "," + password);
            buffer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public <T extends Ticket> void writeTicket(int customerId, T ticket){
        String path = "D:\\facultate\\PAO\\TicketBookingManagementSystem\\Files\\tickets.csv";
        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(path, true))) {
            buffer.write("\n" +customerId + "," + ticket.getEvent().getEventId() + "," + ticket.getSeat() +
                        "," + ticket.getPrice());
            buffer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public <T extends Event> void writeToFavorites(int customerId, T event){
        String path = "D:\\facultate\\PAO\\TicketBookingManagementSystem\\Files\\favorites.csv";
        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(path, true))) {
            buffer.write("\n" + customerId + "," + event.getEventId());
            buffer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public <T extends Event> void deleteFromFavorites(int customerId, T event) throws IOException{
        String path = "D:\\facultate\\PAO\\TicketBookingManagementSystem\\Files\\favorites.csv";
        String auxPath = "D:\\facultate\\PAO\\TicketBookingManagementSystem\\Files\\aux_favorites.csv";

        File initialFile = new File(path);
        File auxFile = new File(auxPath);

        Files.copy(initialFile.toPath(), auxFile.toPath()); // copy data from first file to an auxiliary file

        try (BufferedWriter bufferWrite = new BufferedWriter(new FileWriter(path))){

            BufferedReader bufferRead = new BufferedReader(new FileReader(auxPath));
            // in favorites.csv file I write the lines that I don't want to delete
            String line = bufferRead.readLine();
            bufferWrite.write(line);
            bufferWrite.flush();

            char sep = ',';
            if (line.startsWith("sep")) {
                sep = line.charAt(line.length()-1);
                line = bufferRead.readLine(); // skip the sep
                bufferWrite.write("\n" + line);
                bufferWrite.flush();
            }
            line = bufferRead.readLine(); // skip the header
            while (line != null) {
                List<String> fields = Arrays.asList(line.split(String.valueOf(sep)));
                if (! fields.get(0).equals(String.valueOf(customerId))
                    || ! fields.get(1).equals(String.valueOf(event.getEventId()))) // I don't want to delete this line
                {
                    bufferWrite.write("\n" + line);
                    bufferWrite.flush();
                }
                line = bufferRead.readLine();
            }
            bufferRead.close();
            Files.deleteIfExists( Paths.get(auxPath));
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public <T extends Event> void writeEvent(T event) {
        String path = "D:\\facultate\\PAO\\TicketBookingManagementSystem\\Files\\";
        if (event instanceof Concert)
            path += "concerts.csv";
        else if (event instanceof TheatrePlay)
            path += "theatreplays.csv";

        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(path, true))) {
            buffer.write("\n" + event.getEventId() + "," + event.getHall().getName() + "," +
                          event.getStartingPrice() + "," + event.getNameEvent() + "," + event.getDescription() +
                          "," + event.getDay() + "," + event.getMonth() + "," + event.getYear() + "," +
                          event.getStartingHour() + "," + event.getEndingHour() + "," + event.getType() + ",");

            if (event instanceof Concert)
                buffer.write(((Concert) event).getStanding() ? "yes":"no");
            else if (event instanceof TheatrePlay)
                buffer.write(((TheatrePlay) event).getHasIntermission() ? "yes":"no");

            buffer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public <T extends Event> void deleteEvent(T event) {
        String path, auxPath;
        if (event instanceof Concert) {
             path = "D:\\facultate\\PAO\\TicketBookingManagementSystem\\Files\\concerts.csv";
             auxPath = "D:\\facultate\\PAO\\TicketBookingManagementSystem\\Files\\aux_concerts.csv";
        }
        else {
             path = "D:\\facultate\\PAO\\TicketBookingManagementSystem\\Files\\theatreplays.csv";
             auxPath = "D:\\facultate\\PAO\\TicketBookingManagementSystem\\Files\\aux_theatreplays.csv";
        }

        try {
            File initialFile = new File(path);
            File auxFile = new File(auxPath);

            Files.copy(initialFile.toPath(), auxFile.toPath()); // copy data from first file to an auxiliary file

            try (BufferedWriter bufferWrite = new BufferedWriter(new FileWriter(path))) {

                BufferedReader bufferRead = new BufferedReader(new FileReader(auxPath));
                // in the events file I write the lines that I don't want to delete
                String line = bufferRead.readLine();
                bufferWrite.write(line);
                bufferWrite.flush();

                char sep = ',';
                if (line.startsWith("sep")) {
                    sep = line.charAt(line.length() - 1);
                    line = bufferRead.readLine(); // skip the sep
                    bufferWrite.write("\n" + line);
                    bufferWrite.flush();
                }
                line = bufferRead.readLine(); // skip the header
                while (line != null) {
                    List<String> fields = Arrays.asList(line.split(String.valueOf(sep)));
                    if (! String.valueOf(event.getEventId()).equals(fields.get(0))) // I don't want to delete this line
                    {
                        bufferWrite.write("\n" + line);
                        bufferWrite.flush();
                    }
                    line = bufferRead.readLine();
                }
                bufferRead.close();
                Files.deleteIfExists(Paths.get(auxPath));
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}

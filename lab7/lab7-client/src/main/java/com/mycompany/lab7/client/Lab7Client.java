/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.lab7.client;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author valer
 */
public class Lab7Client {

    public static void main(String[] args) throws IOException {
        Scanner keyboard = new Scanner(System.in);
        
        int action = 0;
         
        while (action != 5) {
            System.out.println("""
                               Actions: 
                               1 - Add new track 
                               2 - Get all tracks 
                               3 - Find track 
                               4 - Delete track 
                               5 - Bye :)
                               """);
            
            action = keyboard.nextInt();
            
            switch (action) {
                case 1 ->   {  
                    System.out.println("Please enter new track name: ");
                    String name = keyboard.next();
                    String result = Service.cfreateNewTrack(name);
                    System.out.println("output: " + result.replace("><", ">\n<") + "\n");
                            }
                case 2 ->   {
                    String result = Service.getAllTracks();
                    System.out.println("output: " + result.replace("><", ">\n<") + "\n");
                            }
                case 3 ->   {
                    System.out.println("Please enter track id: ");
                    String id = keyboard.next();
                    String result = Service.getTrackById(id);
                    System.out.println("output: " + result.replace("><", ">\n<") + "\n");
                            }
                case 4 ->   {
                    System.out.println("Please enter track id: ");
                    String id = keyboard.next();
                    String result = Service.deleteTrackById(id);
                    System.out.println("output: " + result.replace("><", ">\n<") + "\n");
                            }
                case 5 ->   {}
                default ->  System.out.println("invalid character\n");
            }           
        }
    }
}

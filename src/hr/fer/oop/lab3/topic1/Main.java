package hr.fer.oop.lab3.topic1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

/**
 * Created by Luka on 03/12/14.
 */
public class Main {
    public static void main(String[] args) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int capacity = 0;
        SimpleHashtable<String, Integer> examMarks = null;

        while (true) {
            System.out.print("please provide capacity: ");
            try {
                String input = reader.readLine();
                capacity = Integer.parseInt(input);
                examMarks = new SimpleHashtable(capacity);

            } catch (NumberFormatException e) {
                System.out.println(e + " is not a number.");
                continue;

            } catch (IllegalArgumentException e) {
                System.out.println(e);
                continue;

            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
            break;
        }

        examMarks.put("Ivana", Integer.valueOf(2));
        examMarks.put("Ante", Integer.valueOf(2));
        examMarks.put("Jasna", Integer.valueOf(2));
        examMarks.put("Kristina", Integer.valueOf(5));
        examMarks.put("Ivana", Integer.valueOf(5));


        System.out.println(examMarks);

        for (String name : examMarks.keys())
            System.out.println("Ime = " + name);

        for (Integer grade : examMarks.values())
            System.out.println("Ocjena = " + grade);

        for (SimpleHashtable.TableEntry entry : examMarks){
            System.out.printf("%s => %s%n", entry.getKey(), entry.getValue());
        }

    }
}

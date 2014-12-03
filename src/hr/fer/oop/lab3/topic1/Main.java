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
        SimpleHashtable examMarks = null;

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

        for (Object entry : examMarks){
            SimpleHashtable.TableEntry pair = (SimpleHashtable.TableEntry) entry;
            System.out.printf("%s => %s%n", pair.getKey(), pair.getValue());
        }


        while (true) {
            System.out.print("What you want to do:  ");
            try {
                String input = reader.readLine();
                Object answer = examMarks.execute(input);

                if (answer != null)
                    System.out.println(answer);

            } catch (NumberFormatException e) {
                System.out.println(e + " is not a number.");
                continue;

            } catch (IllegalArgumentException e) {
                System.out.println(e);
                continue;

            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);

            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }
}

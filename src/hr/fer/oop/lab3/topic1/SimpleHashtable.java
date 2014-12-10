package hr.fer.oop.lab3.topic1;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * HashTable class with static nested class
 *
 * @author Luka
 * @version 1.00
 */
public class SimpleHashtable implements Iterable<SimpleHashtable.TableEntry> {

    private TableEntry[] table;
    private int size;

    private String[] methods = new String[] {"put", "get", "size", "containsKey", "containsValue", "iterate", "remove", "isEmpty", "quit"};

    public SimpleHashtable() {
        table = new TableEntry[16];
    }

    public SimpleHashtable(int capacity) {
        table = new TableEntry[calculateCapacity(capacity)];
    }


    @Override
    public Iterator<TableEntry> iterator() {

        return new HashTableIterator();
    }

    /**
     * @param capacity desired capacity of hash table
     * @return first greater potention of 2 (or equal)
     */
    private int calculateCapacity(int capacity) {
        if (capacity < 0)
            throw new IllegalArgumentException(capacity + " is not positive. I need positive capacity!");

        int currentPotentionOf2 = 2;

        while (currentPotentionOf2 < capacity)
            currentPotentionOf2 *= 2;

        return currentPotentionOf2;
    }

    /**
     * @param key key of the new entry
     * @param value value of the new entry
     */
    public void put(Object key, Object value) {

        check(key, value);


        int partitionNumber = generatePartition(key);

        TableEntry entry = table[partitionNumber];

        if (entry == null) {

            table[partitionNumber] = new TableEntry(key, value, null);

        } else if (entry.key == key) {
            entry.setValue(value);
            return;
        } else {
            while (entry.next != null) {
                if (entry.key == key) {
                    entry.setValue(value);
                    return;
                }
                entry = entry.next;
            }

            entry.next = new TableEntry(key, value, null);
        }
        size++;
    }

    /**
     * @param arguments array of arguments that we need to check
     * @exception NullPointerException
     * @exception IllegalArgumentException
     */
    private void check(Object...arguments) {
        for (int i=0; i<arguments.length; i++){
            if (arguments[i] == null)
                throw new NullPointerException(i + ". argument is null");

            if (arguments[i] instanceof String){
                String argument = String.valueOf(arguments[i]);
                if (argument.equals(""))
                    throw new IllegalArgumentException("empty argument provided.");
            }

            else if (arguments[i] instanceof Integer){
                Integer number = Integer.parseInt(String.valueOf(arguments[i]));
                if (number < 0)
                    throw new IllegalArgumentException("number cannot be negative")
;            }
        }
    }

    /**
     * @param key entry key
     * @return generated adress of the partition
     */
    private int generatePartition(Object key){
        return  Math.abs(key.hashCode()) % table.length;
    }



    /**
     * @param key entry key
     * @return value of entry with requested key
     */
    public Object get(Object key){

        check(key);

        if (!containsKey(key))
            throw new IllegalArgumentException("key not found");

        int partitionNumber = generatePartition(key);

        TableEntry currentEntry = table[partitionNumber];


        while (currentEntry != null){

            if (currentEntry.keyEquals(key)) {
                return currentEntry.value;
            }
            currentEntry = currentEntry.next;
        }

        return null;

    }


    public int size(){
        return size;
    }

    /**
     * @param key entry key
     * @return {@code true} if hash contains entry with requested
     */

    public boolean containsKey(Object key){

        int partition = generatePartition(key);

        TableEntry currentEntry = table[partition];

        while(currentEntry != null){
            if (currentEntry.keyEquals(key))
                return true;
            currentEntry = currentEntry.next;
        }

        return false;
    }

    /**
     * @param value entry value
     * @return {@code true} if hash contains entry with requested
     */
    public boolean containsValue(Object value){

        for (TableEntry entry : table){

            while (entry != null){
                if (entry.valueEquals(value)) return true;
                entry = entry.next;
            }
        }
        return false;
    }


    public void remove(Object key) {
        if (isEmpty())
            return;

        if (!containsKey(key))
            throw new IllegalArgumentException("key not found");


        int partitionNumber = generatePartition(key);

        TableEntry entry = table[partitionNumber];


        if (entry == null) {
            return;

        } else if (entry.keyEquals(key)) {
            table[partitionNumber] = entry.next;

        } else {
            while (entry.next != null) {

                if (entry.next.keyEquals(key)){
                    entry.next = entry.next.next;
                    break;
                }
                entry = entry.next;
            }
        }
        size--;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();

        for (int pretinac = 0; pretinac < table.length; pretinac++){
            stringBuilder.append("Pretinac ").append(pretinac).append(". ");

            TableEntry entry = table[pretinac];
            while (entry != null){
                stringBuilder.append(entry).append(" ");
                entry = entry.next;
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }


    /**
     * @param input input string
     * @return result of executing commands from input
     */
    public Object execute(String input) {

        check(input);

        String[] arguments = input.split(" ");

        String method = arguments[0];

        check(method);

        method = method.toLowerCase();

        switch (method){
            case "put":
                checkArguments(arguments, 3);
                put(arguments[1], arguments[2]);
                break;

            case "get":
                checkArguments(arguments, 2);
                return get(arguments[1]);

            case "containskey":
                checkArguments(arguments, 2);
                return containsKey(arguments[1]);

            case "containsvalue":
                checkArguments(arguments, 2);
                return containsValue(arguments[1]);

            case "remove":
                checkArguments(arguments, 2);
                remove(arguments[1]);
                break;

            case "size":
                return size;

            case "isempty":
                return isEmpty();

            case "print":
                return this;

            case "quit":
                System.exit(1);
                break;

            case "list":
                for (String singleMethod : methods)
                    System.out.println(singleMethod);
                break;

            case "iterate":
                for (Object entry : this){
                    SimpleHashtable.TableEntry pair = (SimpleHashtable.TableEntry) entry;
                    System.out.printf("%s => %s%n", pair.getKey(), pair.getValue());
                }
        }
        return null;
    }

    private static void checkArguments(String[] arguments, int numberOfArguments) {
        if (arguments.length < numberOfArguments)
            throw new IllegalArgumentException("not enough arguments provided.");

    }

    private class HashTableIterator implements Iterator<TableEntry> {
        private Object currentEntryKey;

        @Override
        public boolean hasNext() {
            try {
                Object oldCurrentEntryKey = currentEntryKey;
                next();
                currentEntryKey = oldCurrentEntryKey;
                return true;
            } catch (NoSuchElementException e) {
                return false;
            }
        }

        @Override
        public TableEntry next() {

            TableEntry nextEntry;

            if (currentEntryKey == null)
                nextEntry = firstAvailableElement();

            else {
                int pretinac = generatePartition(currentEntryKey);

                TableEntry entry = table[pretinac];

                while (!entry.keyEquals(currentEntryKey))
                    entry = entry.next;

                if (entry.next != null)
                    nextEntry = entry.next;
                else
                    nextEntry = firstAvailableElement(++pretinac);
            }
            currentEntryKey = nextEntry.getKey();
            return nextEntry;

        }

        private TableEntry firstAvailableElement() {
            return firstAvailableElement(0);
        }

        private TableEntry firstAvailableElement(int partitionNumber) {
            for (int pretinac = partitionNumber; pretinac < table.length; pretinac++){
                TableEntry entry = table[pretinac];
                if (entry != null){
                    return entry;
                }
            }
            throw new NoSuchElementException();
        }


        @Override
        public void remove() {
            try {
                SimpleHashtable.this.remove(currentEntryKey);
            } catch (IllegalArgumentException e) {
                throw new IllegalStateException();
            }
        }
    }


    public static class TableEntry {
        private Object key;
        private Object value;
        private TableEntry next;

        public TableEntry(Object key, Object value, TableEntry next){
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public TableEntry(){

        }

        public Object getKey(){
            return key;
        }

        public Object getValue(){
            return value;
        }

        public void setValue(Object value){
            this.value = value;
        }

        public String toString(){
            return key + ": " + value;
        }

        private boolean keyEquals(Object key){

            return this.key.toString().equalsIgnoreCase(key.toString());
        }

        private boolean valueEquals(Object value){

            return this.value.toString().equalsIgnoreCase(value.toString());
        }
    }
}

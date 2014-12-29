package hr.fer.oop.lab3.topic1;

import hr.fer.oop.lab3.topic1.shell.Exceptions.KeyNotFoundException;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * HashTable class with static nested class
 *
 * @author Luka
 * @version 1.00
 */
public class SimpleHashtable<K,V> implements Iterable<SimpleHashtable.TableEntry> {

    private TableEntry<K,V>[] table;
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
    public void put(K key, V value) {

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
    private int generatePartition(K key){
        return  Math.abs(key.hashCode()) % table.length;
    }



    /**
     * @param key entry key
     * @return value of entry with requested key
     */
    public Object get(K key){

        check(key);

        if (!containsKey(key))
            throw new KeyNotFoundException();

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
    public boolean containsKey(K key){

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
    public boolean containsValue(V value){

        for (TableEntry entry : table){

            while (entry != null){
                if (entry.valueEquals(value)) return true;
                entry = entry.next;
            }
        }
        return false;
    }


    public void remove(K key) {
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


    private static void checkArguments(String[] arguments, int numberOfArguments) {
        if (arguments.length < numberOfArguments)
            throw new IllegalArgumentException("not enough arguments provided.");

    }

    public Iterable<K> keys() {
        return new KeySet();
    }

    public Iterable<V> values() {
        return new ValueSet();
    }

    private class HashTableIterator implements Iterator<TableEntry> {
        private K currentEntryKey;

        @Override
        public boolean hasNext() {
            try {
                K oldCurrentEntryKey = currentEntryKey;
                next();
                currentEntryKey = oldCurrentEntryKey;
                return true;
            } catch (NoSuchElementException e) {
                return false;
            }
        }

        @Override
        public TableEntry<K,V> next() {

            TableEntry<K,V> nextEntry;

            if (currentEntryKey == null)
                nextEntry = firstAvailableElement();

            else {
                int pretinac = generatePartition(currentEntryKey);

                TableEntry<K,V> entry = table[pretinac];

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

        private TableEntry<K,V> firstAvailableElement() {
            return firstAvailableElement(0);
        }

        private TableEntry<K,V> firstAvailableElement(int partitionNumber) {
            for (int pretinac = partitionNumber; pretinac < table.length; pretinac++){
                TableEntry<K,V> entry = table[pretinac];
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


    public static class TableEntry<K,V> {
        private K key;
        private V value;
        private TableEntry<K,V> next;

        public TableEntry(K key, V value, TableEntry<K,V> next){
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public K getKey(){
            return key;
        }

        public V getValue(){
            return value;
        }

        public void setValue(V value){
            this.value = value;
        }

        public String toString(){
            return key + ": " + value;
        }

        private boolean keyEquals(K key){

            return this.key.toString().equalsIgnoreCase(key.toString());
        }

        private boolean valueEquals(Object value){
            return value.equals(this.value);
        }
    }

    private class KeySet implements Iterable<K> {
        @Override
        public Iterator<K> iterator() {
            return new Iterator<K>() {
                Iterator<SimpleHashtable.TableEntry> tableEntryIterator = SimpleHashtable.this.iterator();
                @Override
                public boolean hasNext() {
                    return tableEntryIterator.hasNext();
                }

                @Override
                public K next() {
                    return (K)tableEntryIterator.next().getKey();
                }
            };
        }
    }

    private class ValueSet implements Iterable<V> {
        @Override
        public Iterator<V> iterator() {
            return new Iterator<V>() {
                Iterator<SimpleHashtable.TableEntry> tableEntryIterator = SimpleHashtable.this.iterator();
                @Override
                public boolean hasNext() {
                    return tableEntryIterator.hasNext();
                }

                @Override
                public V next() {
                    return (V)tableEntryIterator.next().getValue();
                }
            };
        }
    }
}

package ConcurrentHashMapImpl;

public class Main {

    public static void main(String[] args) {
        ConcurrentHashMap<String, String> myMap = new ConcurrentHashMap<>();
        myMap.put("Marina", "22");
        myMap.put("Ivan", "34");
        myMap.put("Oleh", "22");

        myMap.print();
        System.out.println(myMap.size());
        System.out.println(myMap.get("Marina"));
        System.out.println(myMap.isEmpty());
    }

}
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String propertyFileName = args.length > 0 ? args[0] : "default.properties";
        String dir = System.getProperty("user.dir");
        String filePath = dir + File.separator + propertyFileName;


        Properties properties = loadProperties(filePath);
        int numTicks = Integer.parseInt((properties.getProperty("duration")));
        int numElevators = Integer.parseInt(properties.getProperty("elevators"));
        int numFloors = Integer.parseInt(properties.getProperty("floors"));
        float ratio = Float.parseFloat(properties.getProperty("passengers"));
        ElevatorSimulation simulation = new ElevatorSimulation(numElevators, numFloors, numTicks, ratio);
        simulation.runSimulation();
    }

    public static Properties loadProperties(String filePath) {
        Properties properties = new Properties();
        Map<String, String> map = new HashMap<>();
        map.put("structures", "linked");
        map.put("floors", "32");
        map.put("passengers", "0.03");
        map.put("elevators", "1");
        map.put("elevatorCapacity", "10");
        map.put("duration", "500");

        try (FileInputStream input = new FileInputStream(filePath);
             InputStreamReader inputStreamReader = new InputStreamReader(input);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
             String line;
             while ((line = bufferedReader.readLine()) != null) {
                 String[] array = line.split("=");
                 map.put(array[0], array[1]);
             }
             for (String key : map.keySet()) {
                 properties.setProperty(key, map.get(key));
             }
        } catch (IOException e) {
            for (String key : map.keySet()) {
                properties.setProperty(key, map.get(key));
            }
        }
        return properties;
    }
}
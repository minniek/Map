package LoadData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MapData{
    
    protected static String filename;
    public HashMap<String, HashMap<String, ArrayList>> States = new HashMap<String,HashMap<String, ArrayList>>();
    public static HashMap<String, HashMap<String, Integer>> reliability = new HashMap<String, HashMap<String, Integer>>();
    
    public MapData(String filename) throws IOException{
        MapData.filename = filename;
        loadFile();
    }
    
    public HashMap getStates(){
    	return States;
    }
    
    private void loadFile() throws FileNotFoundException, IOException{
        
        //Initialize variables
        String line, state, county; //for state & county info
        double longitude, latitude; //the x and y coordinates to be checked
        double x1, y1, x2, y2;      //coordinates for the current bounding rectangle
        String[] data;
        String delimiter = "\t";
        
        //Set coords to 0 by default (Initialize)
        x1 = y1 = x2 = y2 = 0;
        
        //Read from file
        FileReader file = new FileReader(MapData.filename);
        BufferedReader BF = new BufferedReader(file);
        
        int line_number = 0;
        while ((line = BF.readLine()) != null){
            
            //Add data to hashmap. The first line of the file contains no information... so only consider lines 2 -> end
            if (line_number > 0){
                
                data = line.split(delimiter);
                
                //Get state, county, longitude, and latitude
                state = data[0];
                county = data[1];
                longitude = Double.parseDouble(data[2]);
                latitude = Double.parseDouble(data[3]);
                
                //Make sure the longitude/latitude is NOT zero. Also, make sure that the county name is not "".
                if (longitude != 0 && county != null && county.length()!=0){
                    
                    //Check if the current state has already been added to the hashmap. If not, then execute this 'if' statement
                    if ((this.States == null || this.States.get(state) == null)){

                        //This hash map "counties" will be stored in the "State" hashmap
                        HashMap Counties = new HashMap<String, ArrayList>();

                        //This arraylist will contain the coordinates for said county
                        ArrayList<Double> coordinates = new ArrayList<Double>();
                        coordinates.add(longitude);
                        coordinates.add(latitude);

                        //Now add the county & arraylist to "Counties"
                        Counties.put(county, coordinates);

                        //Add Counties to "States" HashMap
                        this.States.put(state, Counties);
                        
                        //Add count to 'reliability' HashMap
                        HashMap Counties_Count = new HashMap<String, Integer>();
                        Counties_Count.put(county,1); //initialize count to 1

                        this.reliability.put(state,Counties_Count);
                        //this.reliability.put(state,Counties);
                    }
                    else {

                        //Get the info for the existing state name
                        HashMap county_info = (HashMap)this.States.get(state);
                        HashMap county_count = (HashMap)this.reliability.get(state);

                        //If the 'States' HashMap contains the current state, but NOT the current county...
                        if (county_info.get(county) == null){
                            
                            //Add coordinates to county_info
                            ArrayList coordinates = new ArrayList<Double>();
                            coordinates.add(longitude);
                            coordinates.add(latitude);
                            county_info.put(county, coordinates);
                            
                            //Initialize county count to 1
                            county_count.put(county, 1);
                            this.reliability.put(state,county_count);
                        }
                        else{
                            
                            //Get the current count from the 'reliability' HashMap

                        	//int current_count = (Integer)reliability.get(state).get(county);

                            int current_count = reliability.get(state).get(county);
                            
                            current_count++; //increment current count by 1
                            
                            //Now update count
                            county_count.remove(county);
                            county_count.put(county,current_count);
                            this.reliability.put(state,county_count);

                            //Get the existing coordinates from the hashmap
                            ArrayList existing_coordinates;
                            existing_coordinates = (ArrayList) county_info.get(county);

                            /* We need 2 pairs of coordinates to have a rectangle. If we only have
                               1 pair, we will need to add another. */
                            if (existing_coordinates.size() == 2){
                                
                                double tmp_x, tmp_y;
                                
                                //If x1 < longitude
                                if ((Double)existing_coordinates.get(0) < longitude){
                                    existing_coordinates.add(longitude);
                                }
                                else{
                                    tmp_x = (Double)existing_coordinates.get(0);
                                    tmp_y = (Double)existing_coordinates.get(1);
                                    
                                    existing_coordinates.clear();
                                    existing_coordinates.add(longitude);
                                    existing_coordinates.add(tmp_y);
                                    existing_coordinates.add(tmp_x);
                                }
                                
                                //If y1 < longitude
                                if ((Double)existing_coordinates.get(1) > latitude){
                                    existing_coordinates.add(latitude);
                                }
                                else{
                                    tmp_y = (Double)existing_coordinates.get(1);
                                    tmp_x = (Double)existing_coordinates.get(2);
                                    
                                    existing_coordinates.remove(1);
                                    existing_coordinates.remove(1);
                                    
                                    existing_coordinates.add(latitude);
                                    existing_coordinates.add(tmp_x);
                                    existing_coordinates.add(tmp_y);
                                }
                            }
                            
                            /*Case where we already have a rectangle. Check longitude 
                              & latitude to be added; then alter the bounding rectangle
                              if necessary. */
                            else{ 
                                
                                double tmp_x, tmp_y;
                                
                                //Existing bounding rectangle.
                                x1 = (Double)existing_coordinates.get(0);
                                y1 = (Double)existing_coordinates.get(1);
                                x2 = (Double)existing_coordinates.get(2);
                                y2 = (Double)existing_coordinates.get(3);
                                
                                if (longitude < x1){
                                    existing_coordinates.set(0,longitude);
                                }
                                else if (longitude > x2){
                                    existing_coordinates.set(2,longitude);
                                }
                                
                                if (latitude < y1){
                                    existing_coordinates.set(1,latitude);
                                }
                                else if (latitude > y2){
                                    existing_coordinates.set(3,latitude);
                                }
                                
                            }

                            //Update hashmap
                            county_info.put(county,existing_coordinates);
                        }

                        //Update
                        this.States.put(state, county_info);
                        this.reliability.put(state,county_count);
                    }
                
                }
                
            }
            
            line_number++;
        }
        
        //Save to file
        saveFile();
        
        System.out.println("HashMap created.");
        
    }
    
    private void saveFile() throws FileNotFoundException, IOException{
        
        //Create the filename (just concatenate filename and .dat)
        String file = "latest_map.dat";
        File output = new File(file);
        
        //Now save
        FileOutputStream fos = new FileOutputStream(output); 
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this.States);
        oos.close();
    }
    
    public static int getCount(String state, String county){
    	return reliability.get(state).get(county);
    }
    
}
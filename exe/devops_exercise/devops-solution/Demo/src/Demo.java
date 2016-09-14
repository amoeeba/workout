package demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demo {

	
	private void generateJsonFile(String filename){

	Map<String,Integer> routeLengthMap=new HashMap<String,Integer>();	
	List<Double> responseTimeList=new ArrayList<Double>();
	Path path = Paths.get(filename);
	int shortRouteCount=0;
	int longRouteCount=0;
	int veryLongRouteCount=0;
	int otherCount=0;
	int maxRouteLength=0;
	try (
			BufferedReader in = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
	    while(true) {
	        String line = in.readLine();
	        if (line == null) {
	            break;
	        }
	        
	        line=line.substring(line.lastIndexOf("\"")+2);
	        String [] items=line.split(" ");
	        int responseTime=Integer.parseInt(items[1].trim());
	        
	        try{
	        double routeLength=Integer.parseInt(items[3].trim());
	        if(routeLength>maxRouteLength)
	        maxRouteLength=(int) routeLength;
	        
	        routeLength=routeLength*0.001;
	        
	        if(routeLength<100){
	        	shortRouteCount=shortRouteCount+1;
		        routeLengthMap.put("shortroute",shortRouteCount);
		        responseTimeList.add(responseTime*0.001);
	        }else if(routeLength>=100 && routeLength<1000){
	        	longRouteCount=longRouteCount+1;
			    routeLengthMap.put("longroute",longRouteCount);
	        }else if(routeLength>=1000){
	        	veryLongRouteCount=veryLongRouteCount+1;
				routeLengthMap.put("verylongroute",veryLongRouteCount);
	        } 
	        }
	        catch(NumberFormatException nfe){
	        	otherCount=otherCount+1;
			routeLengthMap.put("other",otherCount);
	        }          
	    }
	    
	    for (String key : routeLengthMap.keySet()) {
	    	System.out.println("key: " + key + " value: " + routeLengthMap.get(key));
	    }
		System.out.println("MaxRouteLength: "+maxRouteLength);
	    double average=calculateAverage(responseTimeList);
	    System.out.println("average_response_time: "+average);
	    double standard_deviation_response_time=calculateSD(responseTimeList,average);
	    System.out.println("standard_deviation_response_time: "+standard_deviation_response_time);
	    Collections.sort(responseTimeList);
	    double percentile_response_time=responseTimeList.get(responseTimeList.size()*98/100);
	    System.out.println("98th_percentile_response_time: "+percentile_response_time);
	
	   String json= "\"request_count\":"+
	    "{"+
	    	"\"shortroute\":"+routeLengthMap.get("shortroute")+","+
	    	"\"longroute\":"+routeLengthMap.get("longroute")+","+
	    	"\"verylongroute\":"+routeLengthMap.get("verylongroute")+","+
	    	"\"other\":"+routeLengthMap.get("other")+
	    "},"+

		"\"shortroute_statistics\":"+
	    "{"+
	    "\"average_response_time\":"+average+","+
	    "\"standard_deviation_response_time\":"+standard_deviation_response_time+","+
	    "\"98th_percentile_response_time\":"+percentile_response_time+","+
	    "},"+

	    "\"max_route_length\":"+maxRouteLength;
	
	   System.out.println(json);
	   
	   try {
			File file = new File("result.json");
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(json);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	catch (IOException e){
	//e.printStackTrace();	
		System.out.println("Invalid File or Filename");
	}
	catch (Exception e){
		e.printStackTrace();	
			System.out.println("Error Occured while reading the file");
		}
	}
	
	
	private double calculateAverage(List <Double> responseTimeList) {
		double sum = 0;
		  if(!responseTimeList.isEmpty()) {
		    for (Double responsetime : responseTimeList) {
		        sum += responsetime;
		    }
		    return sum / responseTimeList.size();
		  }
		  return sum;
		}
	
	private double calculateSD(List <Double> responseTimeList,double average) {
		double sum = 0;  
		if(!responseTimeList.isEmpty()) {
		    for (Double responsetime : responseTimeList) {
		    	sum+= Math.pow((responsetime-average),2);
		    }
		    return Math.sqrt( sum / (responseTimeList.size()-1) );
		  }
		  return sum;
		}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Demo demo =new Demo();
		System.out.println("************************Program Started*****************");
		String filename="devops_logs.txt";
		demo.generateJsonFile(filename);
	}

}

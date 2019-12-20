package fileIoCSV;
 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
 
public class fileIO {
	
	private static long beforeTime;
	private static long afterTime;
	

	
	static ArrayList<Double> p0 = new ArrayList<Double>();
	static ArrayList<Double> p1 = new ArrayList<Double>();
	static ArrayList<Double> p2 = new ArrayList<Double>();
	static ArrayList<Double> p3 = new ArrayList<Double>();
	static ArrayList<Double> p4 = new ArrayList<Double>();
	static ArrayList<Double> p5 = new ArrayList<Double>();
	static ArrayList<Double> p6 = new ArrayList<Double>();
	static ArrayList<Double> p7 = new ArrayList<Double>();
	static ArrayList<Double> p8 = new ArrayList<Double>();
	static ArrayList<Double> p9 = new ArrayList<Double>();
	static int n=0;
    public static void main(String[] args){
        //출력 스트림 생성
    	beforeTime = System.currentTimeMillis(); //시작시간
    	
    	
        BufferedWriter bufWriter = null;
        try{
            bufWriter = Files.newBufferedWriter(Paths.get("testResult.csv"),Charset.forName("UTF-8"));
            ArrayList<ArrayList<Double>> group1 =  readCSV();
            //csv파일 읽기
       //     readCSV();
//            List<List<String>> allData = readCSV();
//            for(List<String> newLine : allData){
//            	
//            	List<String> list = newLine;
//            	
//                for(String data : list){
//              
//                bufWriter.newLine();
//            }
            ArrayList<Double> avgList =new ArrayList<Double>();
            ArrayList<Double> devList =new ArrayList<Double>();
            ArrayList<Double> medList =new ArrayList<Double>();
            Double avg = 0.0;
            Double min = 0.0;
            Double max = 0.0;
            Double dev = 0.0;
            for (int i = 0; i < group1.size(); i++) {
            	for (int j = 0; j < n-1; j++) {
            		avg += group1.get(i).get(j);
				}
            	avgList.add((Double)avg/(n-1)); //평균
            	avg = 0.0;
			}
            
            
            for (int i = 0; i < group1.size() ; i++) {
            	for (int j = 0; j < n-1; j++) {

            		dev +=(group1.get(i).get(j))-avgList.get(i)*(group1.get(i).get(j)-avgList.get(i)); //분산
            	}
            	devList.add(dev/(n-1)); //표준편차
            }	
               
            for (int i = 0; i < group1.size(); i++) {
                ArrayList<Double> sortArray = group1.get(i);
            	sortArray.sort(null);//오름차순 정렬

                int size = group1.get(i).size();
                Double result =0.0;

                if(size % 2 == 0){ //배열크기가 짝수일경우
                        int m = size / 2;
                        int l = size / 2 - 1;
                        result = (double) (group1.get(i).get(m) + group1.get(i).get(l) / 2); //중앙값 2개의  평균
                }
                else { //배열크기가 홀수인경우

                        int m =size / 2;

                        result = (double) group1.get(i).get(m); //중앙값

                }
                
                medList.add(result);
            } 
 
    
            
            
            bufWriter.write("total Line : "+n);
            bufWriter.newLine();
            bufWriter.write("     평균  최소  최대  표준편차  중앙값");
            for (int i = 0; i < group1.size(); i++) {
            	min = Collections.min(group1.get(i));
            	max = Collections.max(group1.get(i));
            	Double std = Math.sqrt(devList.get(i));
            	bufWriter.newLine();
            	bufWriter.write("p"+i+" : "+String.format("%.2f",avgList.get(i))+"  "+String.format("%2s",min)+"     "+String.format("%2s",max)+"     "+String.format("%.2f",std)+"    "+String.format("%.2f", medList.get(i)));
			}
            
            afterTime = System.currentTimeMillis();
            
            long totalTime = (afterTime - beforeTime)/1000;
            bufWriter.newLine();
            bufWriter.write("thread 처리시간 : " + 	totalTime +"초");
            
            
            
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(bufWriter != null){
                    bufWriter.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    
    public static ArrayList<ArrayList<Double>> readCSV(){
       
    	
    	
    	ArrayList<ArrayList<Double>> group1 = new ArrayList<ArrayList<Double>>(10);
    	
    	group1.add(p0);
    	group1.add(p1);
    	group1.add(p2);
    	group1.add(p3);
    	group1.add(p4);
    	group1.add(p5);
    	group1.add(p6);
    	group1.add(p7);
    	group1.add(p8);
    	group1.add(p9);
    
        BufferedReader br = null;
        
        
        
        try{
            br = Files.newBufferedReader(Paths.get("test.csv"));
            // https://drive.google.com/file/d/1CmUKlXOaWUHvJpMOrbcqXgNxDVFsV0N4/view
            Charset.forName("UTF-8");
            String line = "";
            
            while((line = br.readLine()) != null){
                //CSV 1행을 저장하는 리스트
            	//ArrayList<String> tmpList = new ArrayList<String>();
                String array[] = line.split(",");
                //배열에서 리스트 반환
               
                
                
                for (int i = 0; i < group1.size(); i++) {
					
					
                	if(     array[i].equals("p0")  || array[i].equals(" p1") || array[i].equals(" p2") || 
            				array[i].equals(" p3") || array[i].equals(" p4") || array[i].equals(" p5") ||
            				array[i].equals(" p6") || array[i].equals(" p7") || array[i].equals(" p8") ||
            				array[i].equals(" p9") )  {
                		 ++i;
                		}else if(array[i].equals("NA") || array[i].equals(" NA") || array[i].equals(" ") || array[i].equals("")){
                				group1.get(i).add(0.0);
                			}else {
                				group1.get(i).add(Double.parseDouble(array[i]));
                			}
				}
                	++n;

            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(br != null){
                    br.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return group1;
    }
}



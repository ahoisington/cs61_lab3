import java.io.*;

public class RIC{




	public static void main(String[] args) {

		try{
			BufferedReader br = new BufferedReader(new FileReader("./RICodes.txt"));
			try {
			    StringBuilder sb = new StringBuilder();
			    String line = br.readLine();

			    while (line != null) {
			        sb.append(line);
			        sb.append(System.lineSeparator());
			        line = br.readLine();
			    }
			    String everything = sb.toString();
			    System.out.println(everything);
			    String[] request = everything.split("\n");
			    for (int x=0; x<request.length; x++){
			    	System.out.println("next val is " + request[x]);
			    }
			    Integer id = 1;
			    try{
				    PrintWriter writer = new PrintWriter("./RICodes.json", "UTF-8");
				    writer.println("'RICodes': [");
				    for (int x=0; x<request.length; x++){
				    	writer.println("{ '_id': "+ id +", 'RIString': '"+ request[x]+"'},");
				    	id++;
				    }
				    
				    writer.close();
				} catch (IOException e) {
				   // do something
				}


			} catch(IOException e){
				br.close();
			} 
			finally {
			    br.close();
			}
		} catch (Exception e){

		}
		






	}








}
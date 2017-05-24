/*
  Acacia and Elisabeth

  connecting to a MongoDB database and executing a query
 */

import java.io.*;

import java.util.*;
import com.mongodb.*;



public class frontend {

	public static void main( String args[] ) {
		String mode = "initial";
		String person_id = "";
	    String person_job = "";
	    BasicDBObject doc = null;
	    DBCollection collection = null;

	    try{   
		
		  	MongoClientURI uri = new MongoClientURI("mongodb://Team18:bTdsYoXCKceLP0pS@cluster0-shard-00-00-ppp7l.mongodb.net:27017,cluster0-shard-00-01-ppp7l.mongodb.net:27017,cluster0-shard-00-02-ppp7l.mongodb.net:27017/Team18DB?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin");
		  	MongoClient mongoClient = new MongoClient(uri);
		  	DB db = mongoClient.getDB("Team18DB");

		  	// BEGIN PLAY/TEST
		  	collection = db.getCollection("person"); 
		  	doc = new BasicDBObject("person_id", 1)
	            .append("fname", "Spongebob")
	            .append("lname", "Squarepants")
	            .append("person_job", "author")
	            .append("email","sb@mac.com")
	            .append("mailing_address","Bikini Bottom")
	            .append("affiliation","The Ocean");

	        collection.insert(doc);

	        DBObject myDoc = collection.findOne();
	 		System.out.println(myDoc); 

	 		// END PLAY/TEST 


		 	//  	Set<String> tables = db.getCollectionNames();

			// for(String coll : tables){
			// 	System.out.println(coll);
			// }
		    
		    System.out.println("Collection created successfully");


// db.person.find({},{_id:0, person_id:1}).sort({person_id: -1}).limit(1)


		 //    //////////////////////////////////////////////////////////////////////////////////////////////////////////
			//TAKE IN USER INPUT
	        BufferedReader buffReader = null;
	        

	        try {
	            buffReader = new BufferedReader(new InputStreamReader(System.in));
	            while (true) {

	            	// mode-dependant greeting
	            	if (!mode.equals("initial")) {
	            		System.out.print("You are logged in as an " + mode + ".  ");
	            	}

	                System.out.print("Enter your request: "); 	// ASK USER FOR INPUT
	                String input = buffReader.readLine();

	                if ("q".equals(input)) {	// USER WANTS TO EXIT PROGRAM
	                    System.out.println("Exit!");
	                    buffReader.close();
	               		break; //leave buffReader loop. then prog will close connection to MySQL server
	                } 

	               	String[] request = input.split(", ");

	               	int num_args = request.length;
	               	
	               	/* ----------------------------------------------------- REGISTER ----------------------------------------------------- */ 

	               	if (request[0].equals("register") && mode.equals("initial")){

		               	person_job = request[1];	          
	               		
	               		// we expect: register author <fname> <lname> <email> <address> <affiliation>
	               		if (person_job.equals("author")) {

		               		if (num_args != 7) {
	       			    		System.err.println("Incorrect number of arguments.");
	       			    		System.exit(1);
	       			    	}

	       			    	String fname = request[2];
	       			    	String lname = request[3];
	       			    	String email = request[4];
	       			    	String address = request[5];
	       			    	String affiliation = request[6];

		               		// make sure email is valid
		               		if (!isEmailAddress(email)) {

		               			System.err.println("Invalid email address.");
								System.exit(1);
		               		
		               		}

	   			    		//////////FIG OUT NEXT person_ID///////////// TO DO
	   			    		//db.collection.find()
	   			    		//pseudocode >> find maxperson_id 
	               			// query = "SELECT MAX(person_id) FROM person;";
	               			// stmt = con.createStatement();
	               			// res = stmt.executeQuery(query);

							if (res.next()){ //TO DO CHANGE FOR PID
								person_id = String.valueOf(Integer.parseInt(res.getString(1)) + 1);
							} else {
								person_id = "1";
							}

		               		////////// INSERT AUTHOR INTO PERSON TABLE///////////
		               		collection = db.getCollection("person"); 
						  	doc = new BasicDBObject("person_id", person_id)
					            .append("fname", fname)
					            .append("lname", lname)
					            .append("person_job", person_job)
					            .append("email",email)
					            .append("mailing_address",address)
					            .append("affiliation",affiliation);

					        collection.insert(doc);

		               		

		               		System.out.println("Author #" + person_id + " has registered successfully. Please login with your new ID to continue.");

		               	// we expect: register editor <fname> <lname>
	               		} else if (person_job.equals("editor")) {

		               		if (num_args != 4) {
	       			    		System.err.println("Incorrect number of arguments.");
	       			    		System.exit(1);
	       			    	}

	       			    	String fname = request[2];
	       			    	String lname = request[3];

	   			    		// FIGURE OUT NEXT PERSON ID TO DO
	               			// query = "SELECT MAX(person_id) FROM person;";
	               			// stmt = con.createStatement();
	               			// res = stmt.executeQuery(query);

							if (res.next()){ //TO DO CHANGE FOR PID
								person_id = String.valueOf(Integer.parseInt(res.getString(1)) + 1);
							} else {
								person_id = "1";
							}

		               		// insert editor into person table
							collection = db.getCollection("person"); 
						  	doc = new BasicDBObject("person_id", person_id)
					            .append("fname", fname)
					            .append("lname", lname)
					            .append("person_job", person_job);

		               		System.out.println("Editor #" + person_id + " has registered successfully. Please login with your new ID to continue.");


		               	// we expect: register reviewer <fname> <lname> <email> <affiliationcode> <RICode1> <RICode2> <RICode3>
	               		} else if (person_job.equals("reviewer")) {

		               		if (num_args < 7 || num_args > 9) {
	       			    		System.err.println("Incorrect number of arguments.");
	       			    		System.exit(1);
	       			    	}

	       			    	String fname = request[2];
	       			    	String lname = request[3];
	       			    	String email = request[4];
	       			    	String affiliation = request[5];
	       			    	String ricode1 = request[6];
	       			    	String ricode2 = "";
	       			    	String ricode3 = "";
	       			    	int num_ricodes = 1;


	       			    	if (!isInteger(ricode1)) {
	       			    		System.err.println("Your first RICode must be an integer.");
	       			    		System.exit(1);
	       			    	}

	       			    	if (num_args > 7) {
		       			    	ricode2 = request[7];

		       			    	if (!isInteger(ricode2)) {
		       			    		System.err.println("Your second RICode must be an integer.");
		       			    		System.exit(1);
		       			    	}

		       			    	num_ricodes = 2;

		       			    	if (num_args > 8) {
		       			    		ricode3 = request[8];

		       			    		if (!isInteger(ricode3)) {
		       			    			System.err.println("Your third RICode must be an integer.");
		       			    			System.exit(1);
		       			    		}

		       			    		num_ricodes = 3;
		       			    	}
	       			    	}


	   			    		// FIGURE OUT NEXT PERSON ID TO DO
	               			// query = "SELECT MAX(person_id) FROM person;";
	               			// stmt = con.createStatement();
	               			// res = stmt.executeQuery(query);

							if (res.next()){
								person_id = String.valueOf(Integer.parseInt(res.getString(1)) + 1);
							} else {
								person_id = "1";
							}

		               		// insert reviewer into person table
							////////// INSERT REVIEWER INTO PERSON TABLE///////////
							collection = db.getCollection("person");
							if (num_ricodes == 1){
								doc = new BasicDBObject("person_id", person_id)
					            .append("fname", fname)
					            .append("lname", lname)
					            .append("person_job", person_job)
					            .append("email",email)
					            .append("affiliation",affiliation)
					            .append("RICode1", ricode1);
							} else if (num_ricodes ==2){
								doc = new BasicDBObject("person_id", person_id)
					            .append("fname", fname)
					            .append("lname", lname)
					            .append("person_job", person_job)
					            .append("email",email)
					            .append("affiliation",affiliation)
					            .append("RICode1", ricode1)
					            .append("RICode2", ricode2);
							} else if (num_ricodes == 3){
								doc = new BasicDBObject("person_id", person_id)
					            .append("fname", fname)
					            .append("lname", lname)
					            .append("person_job", person_job)
					            .append("email",email)
					            .append("affiliation",affiliation)
					            .append("RICode1", ricode1)
					            .append("RICode2", ricode2)
					            .append("RICode3", ricode3);
							}			
					        collection.insert(doc);
		               		System.out.println("Reviewer #" + person_id + " has registered successfully. Please login with your new ID to continue.");
							
							
							
	               		} else {
		         			System.err.println("Invalid request to register.");
		         			System.exit(1);
	               		}
	               	}
	            }

			} catch (IOException e) { 
		        e.printStackTrace();
		    } finally { //close buffReader
		        if (buffReader != null) {
		            try {
		                buffReader.close();
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
		        }
		    } //end of bufferedReader


	  }catch(Exception e){
	    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	  }
	}

	/*
   	 *
   	 * copied from internet. that okay? my inner good student feels wary.
   	 * http://stackoverflow.com/questions/624581/what-is-the-best-java-email-address-validation-method
   	 */
    public static boolean isEmailAddress(String email) {
           String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
           java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
           java.util.regex.Matcher m = p.matcher(email);
           return m.matches();
    }

    /*
    *
    * checks if val is integer. haha another one from internet aah i hope this is okay lolol
    * http://stackoverflow.com/questions/8336607/how-to-check-if-the-value-is-integer-in-java
    */
    public static boolean isInteger(String str) {
	    try {
	        Integer.parseInt(str);
	        return true;
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	}


	// checks to see if str is an integer between 1 and 10 inclusive
    public static boolean isIntegerBetween1And10(String str) {
	    try {
	        int i = Integer.parseInt(str);
	        if (i >= 1 && i <= 10) {
	        	return true;
	        } else {
	        	return false;
	        }
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	}    	

	// gets today's date and time in the form of a string
    public static String getDate() {
	    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	return sdf.format(new java.util.Date());
	}    	

	// gets today's year in the form of a string
    public static String getYear() {
	    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy");
    	return sdf.format(new java.util.Date());
	}    



}
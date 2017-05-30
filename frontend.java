/*
  Acacia and Elisabeth

  connecting to a MongoDB database and executing a query
 */

import java.io.*;
import java.util.concurrent.TimeUnit;
import java.util.*;
import com.mongodb.*;


public class frontend {

	public static void main( String args[] ) {
		String mode = "initial";
		int person_id = 0;

	    BasicDBObject doc = null;
	    BasicDBObject query = null;
	    BasicDBObject fields = null; // tells mongodb what fields in doc to send over
	    
	    DBCursor cursor = null;

	    try{   
		
		  	MongoClientURI uri = new MongoClientURI("mongodb://Team18:bTdsYoXCKceLP0pS@cluster0-shard-00-00-ppp7l.mongodb.net:27017,cluster0-shard-00-01-ppp7l.mongodb.net:27017,cluster0-shard-00-02-ppp7l.mongodb.net:27017/Team18DB?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin");
		  	MongoClient mongoClient = new MongoClient(uri);
		  	DB db = mongoClient.getDB("Team18DB");

		  	DBCollection person = db.getCollection("person");
	    	DBCollection feedback = db.getCollection("feedback");
	    	DBCollection manuscript = db.getCollection("manuscript");
	    	DBCollection issue = db.getCollection("issue");

			//TAKE IN USER INPUT
	        BufferedReader buffReader = null;
	        
	        // sleep for two seconds so that all the connection output does not interfere with buffered reader
	        TimeUnit.SECONDS.sleep(2);

	        try {
	            buffReader = new BufferedReader(new InputStreamReader(System.in));
	            
	            while (true) {

	            	// mode-dependant greeting
	            	if (mode.equals("initial")) {
	                	System.out.print("Please enter your request to begin: ");
	            	} else {
	            		System.out.print("You are logged in as an " + mode + " -- enter your request: ");
	            	}

	                String[] request = buffReader.readLine().split(", ");
	               	int num_args = request.length;

					// USER WANTS TO EXIT PROGRAM
	                if (request[0].equals("q")) {	
	                    System.out.println("Exit!");

	               		//leave buffReader loop. then prog will close connection to mongo server
	                    buffReader.close();
	               		break; 
	                }
	               	
	               	/* ----------------------------------------------------- REGISTER ----------------------------------------------------- */ 

	               	if (request[0].equals("register") && mode.equals("initial")){
	               		System.out.println("REGISTER");

		               	String person_job = request[1];	          
	               		
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

		               		BasicDBObject sort = new BasicDBObject("person_id", -1);
		               		cursor = person.find().sort(sort).limit(1);

		               		if (cursor.hasNext()) {
		               			person_id = Integer.parseInt(cursor.next().get("person_id").toString().replaceAll(".0", "")) + 1;
		               		} else {
		               			person_id = 1;
		               		}

		               		// insert reviewer into person table
		               		person = db.getCollection("person"); 
						  	doc = new BasicDBObject("person_id", person_id)
					            .append("fname", fname)
					            .append("lname", lname)
					            .append("person_job", person_job)
					            .append("email",email)
					            .append("mailing_address",address)
					            .append("affiliation",affiliation);

					        person.insert(doc);

		               		System.out.println("Author #" + person_id + " has registered successfully. Please login with your new ID to continue.");

		               	// we expect: register editor <fname> <lname>
	               		} else if (person_job.equals("editor")) {

		               		if (num_args != 4) {
	       			    		System.err.println("Incorrect number of arguments.");
	       			    		System.exit(1);
	       			    	}

	       			    	String fname = request[2];
	       			    	String lname = request[3];

	   			    		BasicDBObject sort = new BasicDBObject("person_id", -1);
	   			    		cursor = person.find().sort(sort).limit(1);

	   			    		if (cursor.hasNext()) {
	   			    			person_id = Integer.parseInt(cursor.next().get("person_id").toString().replaceAll(".0", "")) + 1;
	   			    		} else {
	   			    			person_id = 1;
	   			    		}

		               		// insert editor into person table
							person = db.getCollection("person"); 
						  	doc = new BasicDBObject("person_id", person_id)
					            .append("fname", fname)
					            .append("lname", lname)
					            .append("person_job", person_job);
					        person.insert(doc);

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

	   			    		BasicDBObject sort = new BasicDBObject("person_id", -1);
	   			    		
	   			    		cursor = person.find().sort(sort).limit(1);

	   			    		if (cursor.hasNext()) {
	   			    			person_id = Integer.parseInt(cursor.next().get("person_id").toString().replaceAll(".0", "")) + 1;
	   			    		} else {
	   			    			person_id = 1;
	   			    		}

		               		// insert reviewer into person table
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
					        person.insert(doc);
		               		System.out.println("Reviewer #" + person_id + " has registered successfully. Please login with your new ID to continue.");
							
	               		} else {
		         			System.err.println("Invalid request to register.");
		         			System.exit(1);
	               		}
	               	}

	               	/* ----------------------------------------------------- LOGIN ----------------------------------------------------- */
	               	// we expect: login <person_id>
	               	else if (request[0].equals("login") && mode.equals("initial")){
	               		
	               		System.out.println("LOGIN\n");

	               		if (num_args != 2) {
       			    		System.err.println("Incorrect number of arguments.");
       			    		System.exit(1);
       			    	}

       			    	// make sure person_id is given as an integer
	               		if (!isInteger(request[1])) {
	               			System.err.println("Invalid ID.");
							System.exit(1);
	               		}

	               		person_id = Integer.parseInt(request[1]);	  
	               		
	               		// check that person_id exists in person collection
	               		query = new BasicDBObject("person_id", new BasicDBObject("$eq", person_id));

	               		cursor = person.find(query);
					    
					    if(!cursor.hasNext()) {
					    	System.err.println("Invalid ID.");
	               			System.exit(1);
					    } 
					    
					    DBObject result = cursor.next();

					    String person_job = result.get("person_job").toString();
					    String fname = result.get("fname").toString();
					    String lname = result.get("lname").toString();

						// greeting
						System.out.println("\nWelcome to your " + person_job+ " page.\n");
						System.out.println(fname.toString() +  " " + lname.toString() + "\n");

	               		if (person_job.equals("author")){

	               			mode = "author";

							DBObject internalQuery = new BasicDBObject("person_id", person_id);
						    internalQuery.put("author_order_num", 1);
						   	query = new BasicDBObject("authors", new BasicDBObject("$elemMatch", internalQuery));
						    
						    cursor = manuscript.find(query);

							System.out.println("The manuscripts for which you are the primary author:");
							printStatus(cursor);

	               		} else if (person_job.equals("reviewer")){

	               			mode = "reviewer";

						   	query = new BasicDBObject("reviewers", new BasicDBObject("$eq", person_id));
						    
						    cursor = manuscript.find(query);

							System.out.println("The manuscripts for which you are a reviewer:");
							printStatus(cursor);

	               		} else if (person_job.equals("editor")){

	               			mode = "editor";

	               			query = new BasicDBObject("editor_id", person_id);

							cursor = manuscript.find(query);

							System.out.println("The manuscripts for which you are an editor:");
							printStatus(cursor);

	               		} else {
	               			// this should never happen!
	               			System.err.println("Input error: Make sure your request to login follows the correct format. Read documentation or input -h or --help for guidance.");
	               			System.exit(1);
	               		}
	            	

	               	/* ----------------------------------------------------- RESIGN ----------------------------------------------------- */
	               	// we expect: resign <person_id>
	               	} else if (request[0].equals("resign") && mode.equals("initial")) {
	    				System.out.println("RESIGN");

	               		if (num_args != 2) {
	               			System.err.println("Incorrect number of arguments.");
	               			System.exit(1);
	               		}

	               		// make sure person_id is an integer
	               		if (!isInteger(request[1])) {
	               			System.err.println("Invalid ID.");
							System.exit(1);	               		
	               		}            

	               		person_id = Integer.parseInt(request[1]);

	               		// delete reviewer!
						person.remove(new BasicDBObject().append("person_id", person_id));
						feedback.remove(new BasicDBObject().append("person_id", person_id));

						System.out.println("Thank you for your service.");

           		    /* --------------------------------------- AUTHOR OPTIONS --------------------------------------- */
           		    /* ------------- SUBMIT ------------- */
       			    // we expect: submit <title> <affiliation> <ricode> <filename> <editor_id> <author2_id> <author3_id> <author4_id>
       			    } else if (request[0].equals("submit") && mode.equals("author")) {
       			    	System.out.println("AUTHOR SUBMIT");
       			    	if (num_args < 6 || num_args > 9) {
       			    		System.err.println("Incorrect number of arguments.");
       			    		System.exit(1);
       			    	}

       			    	String title = request[1];
       			    	String affiliation = request[2];
       			    	String ricode = request[3];
       			    	String filename = request[4]; 
       			    	String editor_id = request[5];
       			    	String new_man_id = "";


	               		// make sure manu_id and reviewer_id are integers
	               		if ( !isInteger(ricode) || !isInteger(editor_id) ) {
	               			System.err.println("RICode and the editor ID must be integers.");
							System.exit(1);	               		
	               		}


				   		BasicDBObject sort = new BasicDBObject("person_id", -1);
				   		
				   		cursor = person.find().sort(sort).limit(1);

				   		int new_manuscript_id = 1;

				   		if (cursor.hasNext()) {
				   			new_manuscript_id = Integer.parseInt(cursor.next().get("person_id").toString().replaceAll(".0", "")) + 1;
				   		}

						// get today's date
						String currentTime = getDate();

						if (num_args == 6){ //only one author
							//insert manuscript into manuscript
							manuscript = db.getCollection("manuscript");
							doc = new BasicDBObject("manuscript_id", new_manuscript_id)
						            .append("title", title)
						            .append("date_submitted", currentTime)
						            .append("man_status", "submitted")
						            .append("RICode", ricode)
						            .append("person_id",editor_id);
						    
						    //insert authors
						    BasicDBList authors = new BasicDBList(); //create list of authors
						    BasicDBObject author1 = new BasicDBObject("person_id",person_id).append("author_order_number",1);
						    
						    authors.add(author1); //add to list of authors
							
							doc.put("authors", authors);
						    manuscript.insert(doc); //insert man

						} else if (num_args == 7){ //two authors
							manuscript = db.getCollection("manuscript");
							doc = new BasicDBObject("manuscript_id", new_manuscript_id)
						            .append("title", title)
						            .append("date_submitted", currentTime)
						            .append("man_status", "submitted")
						            .append("RICode", ricode)
						            .append("person_id",editor_id);

						    Integer author2_id = Integer.parseInt(request[6]);

						    BasicDBList authors = new BasicDBList(); //create list of authors
						    BasicDBObject author1 = new BasicDBObject("person_id",person_id).append("author_order_number",1);
						 	BasicDBObject author2 = new BasicDBObject("person_id",author2_id).append("author_order_number",2);
						 	
						 	authors.add(author1);
						 	authors.add(author2);

							doc.put("authors", authors);
						    manuscript.insert(doc); //insert man
						} else if (num_args == 8){ //three authors
							manuscript = db.getCollection("manuscript");
							doc = new BasicDBObject("manuscript_id", new_manuscript_id)
						            .append("title", title)
						            .append("date_submitted", currentTime)
						            .append("man_status", "submitted")
						            .append("RICode", ricode)
						            .append("person_id",editor_id);

 							Integer author2_id = Integer.parseInt(request[6]);
 							Integer author3_id = Integer.parseInt(request[7]);

						    BasicDBList authors = new BasicDBList(); //create list of authors
						    BasicDBObject author1 = new BasicDBObject("person_id",person_id).append("author_order_number",1);
						 	BasicDBObject author2 = new BasicDBObject("person_id",author2_id).append("author_order_number",2);
						 	BasicDBObject author3 = new BasicDBObject("person_id",author3_id).append("author_order_number",3);
						 	
						 	authors.add(author1);
						 	authors.add(author2);
						 	authors.add(author3);

							doc.put("authors", authors);
						    manuscript.insert(doc); //insert man

						} else if (num_args == 9){ //four authors
							manuscript = db.getCollection("manuscript");
							doc = new BasicDBObject("manuscript_id", new_manuscript_id)
						            .append("title", title)
						            .append("date_submitted", currentTime)
						            .append("man_status", "submitted")
						            .append("RICode", ricode)
						            .append("person_id",editor_id);

 							Integer author2_id = Integer.parseInt(request[6]);
 							Integer author3_id = Integer.parseInt(request[7]);
 							Integer author4_id = Integer.parseInt(request[8]);

						    BasicDBList authors = new BasicDBList(); //create list of authors
						    BasicDBObject author1 = new BasicDBObject("person_id",person_id).append("author_order_number",1);
						 	BasicDBObject author2 = new BasicDBObject("person_id",author2_id).append("author_order_number",2);
						 	BasicDBObject author3 = new BasicDBObject("person_id",author3_id).append("author_order_number",3);
						 	BasicDBObject author4 = new BasicDBObject("person_id",author4_id).append("author_order_number",4);

						 	authors.add(author1);
						 	authors.add(author2);
						 	authors.add(author3);
						 	authors.add(author4);

							doc.put("authors", authors);
						    manuscript.insert(doc); //insert man
						}

						System.out.println("Your manuscript, " + title + " has been submitted successfully.");
							
					/* ------------- STATUS ------------- */
       			    // we expect: status
       			    } else if (request[0].equals("status") && mode.equals("author")) {
       			    	System.out.println("AUTHOR STATUS");

						DBObject internalQuery = new BasicDBObject("person_id", person_id);
					    internalQuery.put("author_order_num", 1);
					   	query = new BasicDBObject("authors", new BasicDBObject("$elemMatch", internalQuery));
					    
					    cursor = manuscript.find(query);

						System.out.println("The manuscripts for which you are the primary author:");
						printStatus(cursor);	

       			    /* ------------- RETRACT ------------- */
       			    // we expect: retract <man_id>
       			    } else if (request[0].equals("retract") && mode.equals("author")) {
       			    	System.out.println("AUTHOR RETRACT");
       			    	if (num_args != 2) {
       			    		System.err.println("Incorrect number of arguments.");
       			    		System.exit(1);
       			    	}

       			    	// validate man_id
       			    	if (!isInteger(request[1])) {
       			    		System.err.println("Invalid ID.");
	               			System.exit(1);
       			    	}

       			    	int man_id = Integer.parseInt(request[1]);

       			    	// ask user for confirmation
       			    	System.out.println("Are you sure you would like to retract manuscript #" + man_id + " from the database?");

       			    	System.out.print("Y/N: ");
       			    	String input = buffReader.readLine();

   			    	    if (input.trim().toLowerCase().equals("y")) {

   			    	    	// delete manuscript
   			    	    	manuscript.remove(new BasicDBObject().append("manuscript_id", man_id));
               				System.out.println("Success. \n");

   			    	    } else {
   			    	    	System.out.println("Request aborted. \n");
   			    	    }

           		    /* --------------------------------------- EDITOR OPTIONS --------------------------------------- */
       			    /* ------------- STATUS ------------- */
       			    // we expect: status
       			    } else if (request[0].equals("status") && mode.equals("editor")) {
       			    	System.out.println("EDITOR STATUS");
       			    	if (num_args != 1) {
       			    		System.err.println("Incorrect number of arguments.");
       			    		System.exit(1);
       			    	}

       			    	//ask for all manuscripts for this editor
               			query = new BasicDBObject("editor_id", person_id);

						cursor = manuscript.find(query);

						System.out.println("The manuscripts for which you are an editor:");
						printStatus(cursor);


       			    /* ------------- ASSIGN ------------- */
       			    // we expect: assign <manu_id> <reviewer_id>
       			    } else if (request[0].equals("assign") && mode.equals("editor")) {
       			    	System.out.println("EDITOR ASSIGN");

       			    	if (num_args != 3) {
       			    		System.err.println("Incorrect number of arguments.");
       			    		System.exit(1);
       			    	}

	               		// make sure manu_id and reviewer_id are integers
	               		if ( !isInteger(request[1]) || !isInteger(request[2]) ) {

	               			System.err.println("Invalid ID.");
							System.exit(1);
	               		
	               		}

	               		int manu_id = Integer.parseInt(request[1]);
	               		int reviewer_id = Integer.parseInt(request[2]);

       			    	// get today's date
       			    	String currentTime = getDate();

   			    		// insert new feedback document
   			    		doc = new BasicDBObject("manuscript_id", manu_id)
   			    	            .append("person_id", reviewer_id)
   			    	            .append("date_submitted", currentTime);
   			    	    feedback.insert(doc); 

   			    	    // update man_status to "under review"
						manuscript.update(new BasicDBObject("manuscript_id", manu_id), new BasicDBObject("man_status", "under review"));

            			System.out.println("Manuscript #" + manu_id + " is being reviewed by reviewer " + reviewer_id + ".");

   			    	/* ------------- REJECT ------------- */
   			    	// we expect: reject <manu_id>
   			    	} else if (request[0].equals("reject") && mode.equals("editor")) {
   			    		System.out.println("EDITOR REJECT");

   			    		if (num_args != 2) {
   			    			System.err.println("Incorrect number of arguments.");
   			    			System.exit(1);
   			    		}

	               		// make sure manu_id is an integer
	               		if ( !isInteger(request[1]) ) {
	               			System.err.println("Invalid ID.");
							System.exit(1);
	               		}

	               		int manu_id = Integer.parseInt(request[1]);

						// update manuscript to "rejected"
 						manuscript.update(new BasicDBObject("manuscript_id", manu_id), new BasicDBObject("man_status", "rejected"));


            			System.out.println("Manuscript #" + manu_id + " has been rejected.");

		    		/* ------------- ACCEPT ------------- */
		    		// we expect: accept <manu_id>
		    		} else if (request[0].equals("accept") && mode.equals("editor")) {
		    			System.out.println("EDITOR ACCEPT");
		    			if (num_args != 2) {
		    				System.err.println("Incorrect number of arguments.");
		    				System.exit(1);
		    			}

	               		// make sure manu_id is an integer
	               		if ( !isInteger(request[1]) ) {
	               			System.err.println("Invalid ID.");
							System.exit(1);
	               		
	               		}

	               		int manu_id = Integer.parseInt(request[1].toString().replaceAll(".0", ""));

						// update manuscript to "accepted"
 						manuscript.update(new BasicDBObject("manuscript_id", manu_id), new BasicDBObject("man_status", "accepted"));

            			// get today's date
            			String currentTime = getDate();

						// add manuscript to the accepted_man page (num_pages is null because manuscript has not been typeset yet)
            			query = new BasicDBObject("acceptance_info", new BasicDBObject("date_of_acceptance", currentTime));        			    
        			    manuscript.update(new BasicDBObject("manuscript_id", manu_id), query);

            			System.out.println("Manuscript #" + manu_id + " has been accepted.");

       			    /* ------------- TYPESET ------------- */
       			    // we expect: typeset <manu_id> <pp>
       			    } else if (request[0].equals("typeset") && mode.equals("editor")) {
       			    	System.out.println("EDITOR TYPESET");

       			    	if (num_args != 3) {
       			    		System.err.println("Incorrect number of arguments.");
       			    		System.exit(1);
       			    	}

	               		// make sure manu_id and pp are integers
	               		if ( !isInteger(request[1]) || !isInteger(request[2]) ) {

	               			System.err.println("Arguments must be integers.");
							System.exit(1);
	               		}
	               		
	               		int manu_id = Integer.parseInt(request[1].toString().replaceAll(".0", ""));
	               		int pp = Integer.parseInt(request[2].toString().replaceAll(".0", ""));

						// update manuscript to "in typesetting"
 						manuscript.update(new BasicDBObject("manuscript_id", manu_id), new BasicDBObject("man_status", "in typesetting"));

            			System.out.println("Manuscript #" + manu_id + " is " + pp + " pages long.");

   			    	/* ------------- SCHEDULE ------------- */
   			    	// we expect: schedule <manu_id> <issue_id>
   			    	} else if (request[0].equals("schedule") && mode.equals("editor")) {
   			    		System.out.println("EDITOR SCHEDULE");
   			    		if (num_args != 3) {
   			    			System.err.println("Incorrect number of arguments.");
   			    			System.exit(1);
   			    		}

	               		// make sure manu_id and issue are integers
	               		if ( !isInteger(request[1]) || !isInteger(request[2]) ) {

	               			System.err.println("Arguments must be integers.");
							System.exit(1);
	               		}

	               		int manu_id = Integer.parseInt(request[1].toString().replaceAll(".0", ""));
	               		int issue_id = Integer.parseInt(request[2].toString().replaceAll(".0", ""));

						// insert manuscript information into issue
   		    			query = new BasicDBObject("issue_info", new BasicDBObject("issue_id", issue_id));        			    
   					    manuscript.update(new BasicDBObject("manuscript_id", manu_id), query);

            			// set manuscript status to be "scheduled for publication"
 						manuscript.update(new BasicDBObject("manuscript_id", manu_id), new BasicDBObject("man_status", "scheduled for publication"));

            			System.out.println("Manuscript #" + manu_id + " is scheduled in issue #" + issue_id + ".");	

		    		/* ------------- PUBLISH ------------- */
		    		// we expect: status <issue_id> <pub_period_num>
		    		} else if (request[0].equals("publish") && mode.equals("editor")) {
		    			System.out.println("EDITOR PUBLISH");
		    			if (num_args != 3) {
		    				System.err.println("Incorrect number of arguments.");
		    				System.exit(1);
		    			}

	               		// make sure issue is an integer
	               		if ( !isInteger(request[1]) || !isInteger(request[2]) ) {

	               			System.err.println("Arguments must be integers.");
							System.exit(1);
	               		}

	               		int issue_id = Integer.parseInt(request[1].toString().replaceAll(".0", ""));
	               		int pub_period_num = Integer.parseInt(request[2].toString().replaceAll(".0", ""));

	               		// get today's date
            			String currentTime = getDate();

            			// get this year
            			String currentYear = getYear();

						// update issue information
 						query = new BasicDBObject("print_date", currentTime);
 						query.put("pub_period_num", pub_period_num);  
 						query.put("pub_year", currentYear);        			    
   					    issue.update(new BasicDBObject("issue_id", issue_id), query);

            			System.out.println("Issue #" + issue_id + " has been published.");
           			         			    
           		    /* --------------------------------------- REVIEWER OPTIONS --------------------------------------- */
           		    /* ------------- REVIEW ------------- */
       			    // we expect: review <manuscript_id> <appropriateness> <clarity> <methodology> <contribution to field> <recommendation>
       			    } else if (request[0].equals("review") && mode.equals("reviewer")) {
       			    	
       			    	System.out.println("REVIEWER REVIEW");
        			   	
       			    	if (num_args != 7) {
       			    		System.err.println("Incorrect number of arguments.");
       			    		System.exit(1);
       			    	}

       			    	if (!isInteger(request[1]) ) {

       			    		System.err.println("Incorrect arguments. Manuscript ID must be an integer.");
       			    		System.exit(1);

       			    	}

       			    	if (!isIntegerBetween1And10(request[2]) || !isIntegerBetween1And10(request[3]) ||
       			    		!isIntegerBetween1And10(request[4]) || !isIntegerBetween1And10(request[5]) ) {

       			    		System.err.println("Incorrect arguments. Ratings must be integers between 1 and 10.");
       			    		System.exit(1);

       			    	}

       			    	String recommendation = request[6];

       			    	if ( !recommendation.equals("accept") || !recommendation.equals("reject") ) {

       			    		System.err.println("Incorrect arguments. Recommendation must be `accept` or `reject`.");
       			    		System.exit(1);

       			    	}

       			    	int manuscript_id = Integer.parseInt(request[1].toString().replaceAll(".0", ""));
       			    	int appropriateness = Integer.parseInt(request[2].toString().replaceAll(".0", ""));
       			    	int clarity = Integer.parseInt(request[3].toString().replaceAll(".0", ""));
       			    	int methodology = Integer.parseInt(request[4].toString().replaceAll(".0", ""));
       			    	int contribution = Integer.parseInt(request[5].toString().replaceAll(".0", ""));

       			    	// get today's date
       			    	String currentTime = getDate();

						// update feedback information
 						query = new BasicDBObject("appropriateness", appropriateness);
 						query.put("clarity", clarity);  
 						query.put("methodology", methodology);        			    
 						query.put("contribution", contribution);        			    
 						query.put("recommendation", recommendation);   

   					    manuscript.update(new BasicDBObject("manuscript_id", manuscript_id), query);
    
             			System.out.println("Feedback submitted.");    

       			    /* ------------- STATUS ------------- */
       			    // we expect: status
       			    } else if (request[0].equals("status") && mode.equals("reviewer")) {
       			    	System.out.println("REVIEWER STATUS");

					   	query = new BasicDBObject("reviewers", new BasicDBObject("$eq", person_id));
					    
					    cursor = manuscript.find(query);

						System.out.println("The manuscripts for which you are a reviewer:");
						printStatus(cursor);

       			    /* ------------- catch everything else ------------- */
	               	} else {
	               		System.err.println("Invalid request. Try again.");
	               		// don't exit - just prompt user for another command
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
    * printStatus() takes a ResultSet from a status query and prints the manuscripts and their information appropriately.
    *
    */
    public static void printStatus (DBCursor cursor){
    	DBObject r = null;

		while (cursor.hasNext()) {
			r = cursor.next();
	    	System.out.format("%-3s", r.get("manuscript_id").toString().replaceAll(".0", ""));
	    	System.out.format("%-15s", r.get("title"));
	    	System.out.format("%-30s", r.get("man_status"));
	    	System.out.format("%-15s\n", r.get("date_submitted"));
		}	

		System.out.println("");
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

	public static DBObject findDocumentById(DBCollection collection, String id) {
	    BasicDBObject query = new BasicDBObject();
	    query.put("person_id", id);
	    DBObject dbObj = collection.findOne(query);
	    return dbObj;
	}
}
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





		 ////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

		               		////////// INSERT REVEIWER INTO PERSON TABLE///////////
		               		DBCollection person = db.getCollection("person"); 
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
							DBCollection person = db.getCollection("person"); 
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
							DBCollection person = db.getCollection("person");
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

	               		if (num_args != 2) {
       			    		System.err.println("Incorrect number of arguments.");
       			    		System.exit(1);
       			    	}

	               		person_id = request[1];	          

	               		// make sure person_id is an integer
	               		if (!isInteger(person_id)) {
	               			System.err.println("Invalid ID.");
							System.exit(1);
	               		}

               			// retrieve information about this person //TO DO
               			// query = ("SELECT * FROM person WHERE person_id=" + person_id + ";");
               			// stmt = con.createStatement();
               			// res = stmt.executeQuery(query);

						if (res.next()){

							person_job = res.getString(4);

							// greeting
							System.out.println("\nWelcome to your " + person_job + " page.\n");
							System.out.println(res.getString(2) +  " " + res.getString(3));

						} else {

							System.err.println("Invalid ID.");
							System.exit(1);

						}


	               		if (person_job.equals("author")){

	               			mode = "author";

	               			// retrieve author specific information //TO DO
							// query = ("SELECT * FROM author WHERE person_id=" + person_id + ";");		           			
							// stmt = con.createStatement();
		     //       			res = stmt.executeQuery(query);

		           			if (res.next()){
		           				System.out.println(res.getString(3) + "\n"); // mailing address
		           			}

	               			// retrieve author's manuscript information //TO DO
							// query = ("SELECT manuscript_id, title, man_status, date_submitted FROM LeadAuthorManuscripts WHERE person_id=" + person_id + " ORDER BY manuscript_id ASC;");	
							// stmt = con.createStatement();
		     //       			res = stmt.executeQuery(query);

		           			printStatus(res);	
		           			//need to access author info and their manuscript info and print dat shit out //TO DO

	               		} else if (person_job.equals("reviewer")){

	               			mode = "reviewer";

	               			//TO DO
	               			// retrieve editor's manuscript information (should these be ALL manuscripts or only the ones overseen by this editor???)
							// query = ("SELECT manuscript_id, title, man_status, date_submitted FROM ReviewerManuscripts WHERE reviewer_id=" + person_id + " ORDER BY man_status DESC, manuscript_id ASC;");		           			
							// stmt = con.createStatement();
		     //       			res = stmt.executeQuery(query);

		           			System.out.println("");

		           			printStatus(res);

	               		} else if (person_job.equals("editor")){

	               			mode = "editor";

	               			//TO DO
	               			// retrieve editor's manuscript information (should these be ALL manuscripts or only the ones overseen by this editor???)
							// query = ("SELECT manuscript_id, title, man_status, date_submitted FROM LeadAuthorManuscripts WHERE editor_id=" + person_id + " ORDER BY man_status DESC, manuscript_id ASC;");		           			
							// stmt = con.createStatement();
		     //       			res = stmt.executeQuery(query);

		           			System.out.println("");

							printStatus(res);

	               		} else {
	               			// this should never happen!
	               			System.err.println("Input error: Make sure your request to login follows the correct format. Read documentation or input -h or --help for guidance.");
	               			System.exit(1);
	               		}
	            	}

	               	/* ----------------------------------------------------- RESIGN ----------------------------------------------------- */
	               	// we expect: resign <person_id>
	               	} else if (request[0].equals("resign") && mode.equals("initial")) {
	               		// only reviewers can do this...

	               		if (num_args != 2) {
	               			System.err.println("Incorrect number of arguments.");
	               			System.exit(1);
	               		}

	               		person_id = request[1];

	               		// make sure person_id is an integer
	               		if (!isInteger(person_id)) {
	               			System.err.println("Invalid ID.");
							System.exit(1);	               		
	               		}

	               		// // make sure person is a reviewer TO DO
	               		// query = ("SELECT * FROM reviewer WHERE person_id=" + person_id + ";");
	               		// stmt = con.createStatement();
	               		// res = stmt.executeQuery(query);

	               		if (!res.next()) {
	               			System.err.println("You cannot resign because you are not a reviewer.");
							System.exit(1);
	               		}

	               		// // delete reviewer from database
	               		// query = ("DELETE FROM person WHERE `person_id`=" + person_id + ";");
	               		// stmt = con.createStatement();
	               		// stmt.executeUpdate(query);

	               		// trigger has reverted all "under_review" manuscripts to which they were assigned back to "submitted"
	               		System.out.println("Thank you for your service.");

           		    /* --------------------------------------- AUTHOR OPTIONS --------------------------------------- */
           		    /* ------------- SUBMIT ------------- */
       			    // we expect: submit <title> <affiliationcode> <ricode> <filename> <editor_id> <author2_id> <author3_id> <author4_id>
       			    } else if (request[0].equals("submit") && mode.equals("author")) {

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


   			    		// // figure out next manuscript_id //TO DO
            //    			query = "SELECT MAX(manuscript_id) FROM manuscript;";
            //    			stmt = con.createStatement();
            //    			res = stmt.executeQuery(query);

						if (res.next()){
							new_manuscript_id = String.valueOf(Integer.parseInt(res.getString(1)) + 1);
						} else {
							new_manuscript_id = "1";
						}

						// get today's date
						String currentTime = getDate();

						////////////////////FOR ACACIA TO DO
						// insert manuscript into manuscript
						DBCollection manuscript = db.getCollection("manuscript");
						doc = new BasicDBObject("manuscript_id", new_manuscript_id)
					            .append("title", title)
					            .append("date_submitted", currentTime)
					            .append("man_status", "submitted")
					            .append("RICode", ricode)
					            .append("editor_id",editor_id);
					    manuscript.insert(doc);
					    BasicDBList authors = new BasicDBList();
					    BasicDBObject author1 = new BasicDBObject("person_id",person_id).append("author_order_number",1);
					    authors.add(author1);
					    //need to add to manuscript next


	           			// insert primary author information
						query = ("INSERT INTO `man_to_author` (`manuscript_id`,`person_id`,`author_order_num`) VALUES (" + new_man_id + "," + person_id + ",1);");
						stmt = con.createStatement();
	           			stmt.executeUpdate(query);

	           			// insert secondary author information
						for (int i=6; i<num_args; i++) {
							query = "INSERT INTO `man_to_author` (`manuscript_id`,`person_id`,`author_order_num`) VALUES (" + new_man_id + "," + request[i] + "," + String.valueOf(i-4) + ");";
							stmt = con.createStatement();
	           				stmt.executeUpdate(query);
						}   
						///////////////////// 			    

					/* ------------- STATUS ------------- */
       			    // we expect: status
       			    } else if (request[0].equals("status") && mode.equals("author")) {

       			    	if (num_args != 1) {
       			    		System.err.println("Incorrect number of arguments.");
       			    		System.exit(1);
       			    	}
   			    		
               			// retrieve author's manuscript information //TO DO
						// query = ("SELECT manuscript_id, title, man_status, date_submitted FROM LeadAuthorManuscripts WHERE person_id=" + person_id + " ORDER BY manuscript_id ASC;");	
						// stmt = con.createStatement();
	     //       			res = stmt.executeQuery(query);

	           			System.out.println("");

	           			printStatus(res);	

       			    /* ------------- RETRACT ------------- */
       			    // we expect: retract <man_id>
       			    } else if (request[0].equals("retract") && mode.equals("author")) {

       			    	if (num_args != 2) {
       			    		System.err.println("Incorrect number of arguments.");
       			    		System.exit(1);
       			    	}

       			    	String man_id = request[1];

       			    	// validate man_id
       			    	if (!isInteger(man_id)) {
       			    		System.err.println("Invalid ID.");
	               			System.exit(1);
       			    	}

       			    	// ask user for confirmation
       			    	System.out.println("Are you sure you would like to retract manuscript #" + man_id + " from the database?");

       			    	System.out.print("Y/N: ");
       			    	input = buffReader.readLine();

   			    	    if (input.trim().toLowerCase().equals("y")) {

   			    	        // delete manuscript from database //TO DO
    						// query = ("DELETE FROM manuscript WHERE manuscript_id = " + man_id + ";");
    						// stmt = con.createStatement();
          //      				stmt.executeUpdate(query);

               				System.out.println("Success. \n");

   			    	    } else {
   			    	    	System.out.println("Request aborted. \n");
   			    	    }

           		    /* --------------------------------------- EDITOR OPTIONS --------------------------------------- */
       			    /* ------------- STATUS ------------- */
       			    // we expect: status
       			    } else if (request[0].equals("status") && mode.equals("editor")) {

       			    	if (num_args != 1) {
       			    		System.err.println("Incorrect number of arguments.");
       			    		System.exit(1);
       			    	}

      //          			// retrieve editor's manuscript information  //TO DO
						// query = ("SELECT manuscript_id, title, man_status, date_submitted FROM LeadAuthorManuscripts WHERE editor_id=" + person_id + " ORDER BY man_status DESC, manuscript_id ASC;");		           			
						// stmt = con.createStatement();
	     //       			res = stmt.executeQuery(query);

	           			System.out.println("");

						printStatus(res);

       			    /* ------------- ASSIGN ------------- */
       			    // we expect: assign <manu_id> <reviewer_id>
       			    } else if (request[0].equals("assign") && mode.equals("editor")) {

       			    	if (num_args != 3) {
       			    		System.err.println("Incorrect number of arguments.");
       			    		System.exit(1);
       			    	}

	               		String manu_id = request[1];
	               		String reviewer_id = request[2];

	               		// make sure manu_id and reviewer_id are integers
	               		if ( !isInteger(manu_id) || !isInteger(reviewer_id) ) {

	               			System.err.println("Invalid ID.");
							System.exit(1);
	               		
	               		}

	               		// check to see if this reviewer has the same RICode as the manuscript //TO DO
	              //  		query = ("SELECT RICode FROM person_to_RICode WHERE person_id=" + reviewer_id +
	              //  			" AND RICode=(SELECT RICode FROM manuscript WHERE manuscript_id=" + manu_id + " AND person_id=" + person_id + ");");
	              //  		stmt = con.createStatement();
            			// res = stmt.executeQuery(query);

            			if (!res.next()) {
            				System.err.println("Reviewer " + reviewer_id + " cannot be assigned to Manuscript #" + manu_id + " because they do not match in RICode.");
            				System.exit(1);
            			}		

       			    	// get today's date
       			    	String currentTime = getDate();

			    	    // assign reviewer to manuscript //ACACIA TO DO
 						// query = ("INSERT INTO `feedback` (`manuscript_id`,`person_id`,`date_assigned`) VALUES (" + manu_id + "," + reviewer_id + ",'" + currentTime + "');");
 						// stmt = con.createStatement();
       //      			stmt.executeUpdate(query);	

			    	    // update manuscript to "under review" //TO DO
 						// query = ("UPDATE `manuscript` SET `man_status` = 'under review' WHERE manuscript_id = " + manu_id + ";");
 						// stmt = con.createStatement();
       //      			stmt.executeUpdate(query);	

            			System.out.println("Manuscript #" + manu_id + " is being reviewed by reviewer " + reviewer_id + ".");

   			    	/* ------------- REJECT ------------- */
   			    	// we expect: reject <manu_id>
   			    	} else if (request[0].equals("reject") && mode.equals("editor")) {

   			    		if (num_args != 2) {
   			    			System.err.println("Incorrect number of arguments.");
   			    			System.exit(1);
   			    		}

	               		String manu_id = request[1];

	               		// make sure manu_id is an integer
	               		if ( !isInteger(manu_id) ) {
	               			System.err.println("Invalid ID.");
							System.exit(1);
	               		
	               		}

						// update manuscript to "rejected" //TO DO
 						// query = ("UPDATE `manuscript` SET `man_status` = 'rejected' WHERE manuscript_id = " + manu_id + " AND person_id=" + person_id + ";");
 						// stmt = con.createStatement();
       //      			stmt.executeUpdate(query);	

            			System.out.println("Manuscript #" + manu_id + " has been rejected.");

		    		/* ------------- ACCEPT ------------- */
		    		// we expect: accept <manu_id>
		    		} else if (request[0].equals("accept") && mode.equals("editor")) {

		    			if (num_args != 2) {
		    				System.err.println("Incorrect number of arguments.");
		    				System.exit(1);
		    			}

	               		String manu_id = request[1];

	               		// make sure manu_id is an integer
	               		if ( !isInteger(manu_id) ) {
	               			System.err.println("Invalid ID.");
							System.exit(1);
	               		
	               		}

						// update manuscript to "accepted" //TO DO
 						// query = ("UPDATE `manuscript` SET `man_status` = 'accepted' WHERE manuscript_id = " + manu_id + " AND person_id=" + person_id + ";");
 						// stmt = con.createStatement();
       //      			stmt.executeUpdate(query);	

            			// get today's date
            			String currentTime = getDate();

						// add manuscript to the accepted_man page (num_pages is null because manuscript has not been typeset yet) //TO DO NEED TO DECIDE WHAT TO DO
 						// query = ("INSERT INTO `accepted_man` (`manuscript_id`,`date_of_acceptance`,`num_pages`) VALUES (" + manu_id + ",'" + currentTime + "', NULL);");
 						// stmt = con.createStatement();
       //      			stmt.executeUpdate(query);

            			System.out.println("Manuscript #" + manu_id + " has been accepted.");

       			    /* ------------- TYPESET ------------- */
       			    // we expect: typeset <manu_id> <pp>
       			    } else if (request[0].equals("typeset") && mode.equals("editor")) {

       			    	if (num_args != 3) {
       			    		System.err.println("Incorrect number of arguments.");
       			    		System.exit(1);
       			    	}

	               		String manu_id = request[1];
	               		String pp = request[2];

	               		// make sure manu_id and pp are integers
	               		if ( !isInteger(manu_id) || !isInteger(pp) ) {

	               			System.err.println("Arguments must be integers.");
							System.exit(1);
	               		
	               		}

						// // update manuscript to "in typesetting" //TO DO
 					// 	query = ("UPDATE `manuscript` SET `man_status` = 'in typesetting' WHERE manuscript_id = " + manu_id + " AND person_id=" + person_id + ";");
 					// 	stmt = con.createStatement();
      //       			stmt.executeUpdate(query);	

						// // set number of pages for manuscript //TO DO
 					// 	query = ("UPDATE `accepted_man` SET `num_pages`=" + pp + ";");
 					// 	stmt = con.createStatement();
      //       			stmt.executeUpdate(query);

            			System.out.println("Manuscript #" + manu_id + " is " + pp + " pages long.");

   			    	/* ------------- SCHEDULE ------------- */
   			    	// we expect: schedule <manu_id> <issue_id>
   			    	} else if (request[0].equals("schedule") && mode.equals("editor")) {

   			    		if (num_args != 3) {
   			    			System.err.println("Incorrect number of arguments.");
   			    			System.exit(1);
   			    		}

	               		String manu_id = request[1];
	               		String issue_id = request[2];
	               		String num_pages = "";
	               		String num_mans_in_issue = "";

	               		// make sure manu_id and issue are integers
	               		if ( !isInteger(manu_id) || !isInteger(issue_id) ) {

	               			System.err.println("Arguments must be integers.");
							System.exit(1);
	               		
	               		}

	               		// // check to see if manuscript has been typeset, if so, get number of pages //TO DO
	               		// query = ("SELECT num_pages FROM accepted_man WHERE manuscript_id=" + manu_id + ";");
	              //  		stmt = con.createStatement();
            			// res = stmt.executeQuery(query);

            			if (res.next()) {
            				if (res.getString(1) == null) {
	            				System.err.println("Manuscript #" + manu_id + " has not been typeset yet and cannot be scheduled.");
	            				System.exit(1);
            				}
            				num_pages = res.getString(1);
            				System.out.println(num_pages);

            			} else {
		        			System.err.println("Manuscript #" + manu_id + " has not been typeset yet and cannot be scheduled.");
		        			System.exit(1);	
            			}

            // 			// check to see how full the issue is? //TO DO
            // 			query = ("SELECT SUM(page_num), COUNT(manuscript_id) FROM issue_to_man WHERE issue_id=" + issue_id + ";");
	           //     		stmt = con.createStatement();
	        			// res = stmt.executeQuery(query);

	        			if (res.next()) {

	        				// would the issue have more than 100 pages if this manuscript were added?  TO DO
	        				if ( (Integer.parseInt(num_pages) + Integer.parseInt(res.getString(1))) > 100 ) {
	        					System.err.println("Issue #" + issue_id + " is too full. Cannot fit manuscript #" + manu_id + " in this issue.");
	        					System.exit(1);	
	        				}
	        				num_mans_in_issue = res.getString(2);
            				System.out.println(num_mans_in_issue);

	        			// select didn't return anything - issue doesn't exist
	        			} else { 
	        				System.err.println("Issue #" + issue_id + " does not exist.");
	        				System.exit(1);	
	        			}

	        			String pos_in_issue = String.valueOf(Integer.parseInt(num_mans_in_issue) + 1);

						// // insert manuscript information into issue_to_man TO DO
 					// 	query = ("INSERT INTO `issue_to_man` (`manuscript_id`,`issue_id`,`page_num`,`position_in_issue`) VALUES (" + manu_id + "," + issue_id + "," + num_pages + "," + pos_in_issue + ");");
 					// 	stmt = con.createStatement();
      //       			stmt.executeUpdate(query);

       //      			// set manuscript status to be "scheduled for publication" TO DO
 						// query = ("UPDATE manuscript SET `man_status`=`scheduled for publication` WHERE manuscript_id=" + manu_id + ");");
 						// stmt = con.createStatement();
       //      			stmt.executeUpdate(query);

            			System.out.println(query);
            			System.out.println("Manuscript #" + manu_id + " is at position " + pos_in_issue + " in issue #" + issue_id + ".");	

		    		/* ------------- PUBLISH ------------- */
		    		// we expect: status <issue_id> <pub_period_num>
		    		} else if (request[0].equals("publish") && mode.equals("editor")) {

		    			if (num_args != 3) {
		    				System.err.println("Incorrect number of arguments.");
		    				System.exit(1);
		    			}

	               		String issue_id = request[1];
	               		String pub_period_num = request[2];

	               		// make sure issue is an integer
	               		if ( !isInteger(issue_id) || !isInteger(pub_period_num) ) {

	               			System.err.println("Arguments must be integers.");
							System.exit(1);
	               		
	               		}

	               		// get today's date
            			String currentTime = getDate();

            			// get this year
            			String currentYear = getYear();

						// // update manuscript information into issue_to_man TO DO
 					// 	query = ("UPDATE issue SET `print_date`=" + currentTime + ", `pub_period_num`=" + pub_period_num + ", `pub_year`=" + currentYear + " WHERE issue_id=" + issue_id + ");");
 					// 	stmt = con.createStatement();
      //       			stmt.executeUpdate(query);

            			System.out.println("Issue #" + issue_id + " has been published.");

	               		// trigger 3 will set all associated manuscripts to published!	
           			         			    
           		    /* --------------------------------------- REVIEWER OPTIONS --------------------------------------- */
           		    /* ------------- REVIEW ------------- */
       			    // we expect: review <manuscript_id> <appropriateness> <clarity> <methodology> <contribution to field> <recommendation>
       			    } else if (request[0].equals("review") && mode.equals("reviewer")) {
       			    	// note: cannot give feedback for a manuscript that doesn't belong to this reviewer

       			    	if (num_args != 7) {
       			    		System.err.println("Incorrect number of arguments.");
       			    		System.exit(1);
       			    	}

       			    	String manuscript_id = request[1];
       			    	String appropriateness = request[2];
       			    	String clarity = request[3];
       			    	String methodology = request[4];
       			    	String contribution = request[5];
       			    	String recommendation = request[6];


       			    	if (!isInteger(manuscript_id) ) {

       			    		System.err.println("Incorrect arguments. Manuscript ID must be an integer.");
       			    		System.exit(1);

       			    	}

       			    	if (!isIntegerBetween1And10(appropriateness) || !isIntegerBetween1And10(clarity) ||
       			    		!isIntegerBetween1And10(methodology) || !isIntegerBetween1And10(contribution) ) {

       			    		System.err.println("Incorrect arguments. Ratings must be integers between 1 and 10.");
       			    		System.exit(1);

       			    	}

       			    	if ( !recommendation.equals("accept") || !recommendation.equals("reject") ) {

       			    		System.err.println("Incorrect arguments. Recommendation must be `accept` or `reject`.");
       			    		System.exit(1);

       			    	}

       			    	// get today's date
       			    	String currentTime = getDate();

			    // 	    // update feedback from this reviewer for manuscript TO DO
 						// query = ("UPDATE feedback SET appropriateness = " + appropriateness + ", clarity = " + 
 						// 	clarity + ", methodology = " + methodology + ", contribution_to_field = " + 
 						// 	contribution + ", recommendation = " + recommendation + ", date_completed = '" + 
 						// 	currentTime + "' WHERE manuscript_id = " + manuscript_id + " AND person_id=" + person_id + ";");
 						// stmt = con.createStatement();
       //      			stmt.executeUpdate(query);		
            			System.out.println("Feedback submitted.");    

       			    /* ------------- STATUS ------------- */
       			    // we expect: status
       			    } else if (request[0].equals("status") && mode.equals("reviewer")) {

      //          			// retrieve reviewer's manuscript information TO DO
						// query = ("SELECT manuscript_id, title, man_status, date_submitted FROM ReviewerManuscripts WHERE reviewer_id=" + person_id + " ORDER BY manuscript_id ASC;");	
						// stmt = con.createStatement();
	     //       			res = stmt.executeQuery(query);

	           			System.out.println("");

	           			printStatus(res);	

       			    /* ------------- catch everything else ------------- */
	               	} else {
	               		System.err.println("Invalid request.");
	               		System.exit(1);
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

// AUTHORS: Elisabeth Pillsbury & Acacia Hoisington
// DATE: 5/20/17
// ASSIGNMENT: LAB3A | COSC61

// DESCRIPTION: a .js script to create and populate five collections and 
// run a few sample queries
// USAGE: mongo < <path to setup.js>


// choose db
use local

// drop tables if they exist
db.person.drop()
db.manuscript.drop()
db.feedback.drop()
db.RICodes.drop()
db.issue.drop()

// insert person collection
db.person.insert(
	[
		{
			"person_id":1,
			"fname":"Spongebob",
		 	"lname": "Squarepants",
		 	"person_job" : "author",
		 	"email":"sb@mac.com",
		 	"mailing_address":"Bikini Bottom",
		 	"affiliation":"The Ocean"
		},
		{
			"person_id":2,
			"fname":"Scooby",
		 	"lname": "Doo",
		 	"person_job" : "reviewer",
		 	"email": "sd@mac.com",
		 	"affiliation":"Mystery Inc.",
		 	"RICode1": 1,
		 	"RICode2": 2
		},
		{
			"person_id":3,
			"fname":"Shrek",
		 	"lname": "TheOgre",
		 	"person_job" : "editor"
		},
		{
			"person_id":4,
			"fname":"Sco",
		 	"lname": "Doo",
		 	"person_job" : "reviewer",
		 	"email": "sd@mac.com",
		 	"affiliation":"mystery",
		 	"RICode1": 1,
		 	"RICode2": 2,
		 	"RICode3": 3
		},
		{
			"person_id":5,
			"fname":"blah",
		 	"lname": "blee",
		 	"person_job" : "reviewer",
		 	"email": "sd@mac.com",
		 	"affiliation":"Darty",
		 	"RICode1": 1,
		 	"RICode2": 2
		},
		{
			"person_id":6,
			"fname":"gooo",
		 	"lname": "gobble",
		 	"person_job" : "author",
		 	"email":"turkey@mac.com",
		 	"mailing_address":"turkey place",
		 	"affiliation":"Darty",
		 	"RICode1": 1
		}
	]
)

// insert manuscript collection
db.manuscript.insert( 
	[
		{
			"manuscript_id":1,
			"title":"Spongebob",
			"date_submitted": "2016-07-14 08:14:49",
			"man_status" : "submitted",
			"RICode":1,
			"editor_id":3,
			"authors": [
				{
					"person_id": 1,
					"author_order_num": 1
				},
				{
					"person_id": 6,
					"author_order_num": 2
				}
			]
		},
		{
			"manuscript_id": 2,
			"title":"Spongebob",
			"date_submitted": "2016-07-14 08:14:49",
			"man_status" : "under review",
			"RICode":1,
			"editor_id":3,
			"authors": [
				{
					"person_id": 1,
					"author_order_num": 1
				},
				{
					"person_id": 6,
					"author_order_num": 2
				}
			],
			"reviewers": [2, 4, 5]
		},
		{
			"manuscript_id": 3,
			"title":"Spongebob",
			"date_submitted": "2016-07-14 08:14:49",
			"man_status" : "rejected",
			"RICode":1,
			"editor_id":3,
			"authors": [
				{
					"person_id": 1,
					"author_order_num": 1
				},
				{
					"person_id": 6,
					"author_order_num": 2
				}
			]
		},
		{
			"manuscript_id": 4,
			"title":"Spongebob",
			"date_submitted": "2016-07-14 08:14:49",
			"man_status" : "accepted",
			"RICode":1,
			"editor_id":3,
			"authors": [
				{
					"person_id": 6,
					"author_order_num": 1
				},
				{
					"person_id": 1,
					"author_order_num": 2
				}
			],
			"reviewers": [2, 4, 5],
			"acceptance_info" : {
				"date_of_acceptance": "2016-07-14 08:14:49",
				"num_of_pages": 22
			}
		},
		{
			"manuscript_id": 5,
			"title":"Spongebob",
			"date_submitted": "2016-07-14 08:14:49",
			"man_status" : "in typesetting",
			"RICode":1,
			"editor_id":3,
			"authors": [
				{
					"person_id": 6,
					"author_order_num": 1
				},
				{
					"person_id": 1,
					"author_order_num": 2
				}
			],
			"reviewers": [2, 4, 5],
			"acceptance_info" : {
				"date_of_acceptance": "2016-08-14 08:14:49",
				"num_of_pages": 10
			}
		},
		{
			"manuscript_id": 6,
			"title":"Spongebob",
			"date_submitted": "2016-07-14 08:14:49",
			"man_status" : "scheduled for publication",
			"RICode":1,
			"editor_id":3,
			"authors": [
				{
					"person_id": 6,
					"author_order_num": 1
				},
				{
					"person_id": 1,
					"author_order_num": 2
				}
			],
			"reviewers": [2, 4, 5],
			"acceptance_info" : {
				"date_of_acceptance": "2016-09-14 08:14:49",
				"num_of_pages": 20
			},
			"issue_info": {
				"issue_id": 1,
				"page_num": 70,
				"position_in_issue": 2
			}
		},
		{
			"manuscript_id": 7,
			"title":"Spongebob",
			"date_submitted": "2016-07-14 08:14:49",
			"man_status" : "published",
			"RICode":1,
			"editor_id":3,
			"authors": [
				{
					"person_id": 6,
					"author_order_num": 1
				},
				{
					"person_id": 1,
					"author_order_num": 2
				}
			],
			"reviewers": [2, 4, 5],
			"acceptance_info" : {
				"date_of_acceptance": "2016-010-14 08:14:49",
				"num_of_pages": 50
			},
			"issue_info": {
				"issue_id": 1,
				"page_num": 1,
				"position_in_issue": 1
			}
		}
	]
)

// insert issue collection
db.issue.insert( 
	{	
		"issue_id": 1,
		"print_date": "2016-012-14",
		"pub_period_num": 2,
		"pub_year": 2018,
		"editor_id": 3
	}
)

// insert feedback collection
db.feedback.insert( 
	[
		{
			"manuscript_id": 2,
			"person_id": 2, 
			"date_assigned": "2016-07-14 08:14:49",
			"appropriateness": null,
			"clarity": null,
			"methodology":null,
			"contribution_to_field":null,
			"recommendation":null,
			"date_completed":null
		},
		{
			"manuscript_id": 2,
			"person_id": 4, 
			"date_assigned": "2016-07-14 08:14:49",
			"appropriateness": null,
			"clarity": null,
			"methodology":null,
			"contribution_to_field":null,
			"recommendation":null,
			"date_completed":null
		},
		{
			"manuscript_id": 2,
			"person_id": 5,
			"date_assigned": "2016-07-14 08:14:49",
			"appropriateness": null,
			"clarity": null,
			"methodology":null,
			"contribution_to_field":null,
			"recommendation":null,
			"date_completed":null
		},
		{
			"manuscript_id": 4,
			"person_id": 2, 
			"date_assigned": "2016-07-14 08:14:49",
			"appropriateness": 7,
			"clarity": 7,
			"methodology":7,
			"contribution_to_field":7,
			"recommendation":"accept",
			"date_completed":"2016-09-14 08:14:49"
		},
		{
			"manuscript_id": 4,
			"person_id": 4, 
			"date_assigned": "2016-07-14 08:14:49",
			"appropriateness": 7,
			"clarity": 7,
			"methodology":7,
			"contribution_to_field":7,
			"recommendation":"accept",
			"date_completed":"2016-09-14 08:14:49"
		},
		{
			"manuscript_id": 4,
			"person_id": 5, 
			"date_assigned": "2016-07-14 08:14:49",
			"appropriateness": 7,
			"clarity": 7,
			"methodology":7,
			"contribution_to_field":7,
			"recommendation":"accept",
			"date_completed":"2016-09-14 08:14:49"
		},
		{
			"manuscript_id": 5,
			"person_id": 2, 
			"date_assigned": "2016-07-14 08:14:49",
			"appropriateness": 7,
			"clarity": 7,
			"methodology":7,
			"contribution_to_field":7,
			"recommendation":"accept",
			"date_completed":"2016-09-14 08:14:49"
		},
		{
			"manuscript_id": 5,
			"person_id": 4, 
			"date_assigned": "2016-07-14 08:14:49",
			"appropriateness": 7,
			"clarity": 7,
			"methodology":7,
			"contribution_to_field":7,
			"recommendation":"accept",
			"date_completed":"2016-09-14 08:14:49"
		},
		{
			"manuscript_id": 5,
			"person_id": 5, 
			"date_assigned": "2016-07-14 08:14:49",
			"appropriateness": 7,
			"clarity": 7,
			"methodology":7,
			"contribution_to_field":7,
			"recommendation":"accept",
			"date_completed":"2016-09-14 08:14:49"
		},
		{
			"manuscript_id": 6,
			"person_id": 2, 
			"date_assigned": "2016-07-14 08:14:49",
			"appropriateness": 7,
			"clarity": 7,
			"methodology":7,
			"contribution_to_field":7,
			"recommendation":"accept",
			"date_completed":"2016-09-14 08:14:49"
		},
		{
			"manuscript_id": 6,
			"person_id": 4,
			"date_assigned": "2016-07-14 08:14:49",
			"appropriateness": 7,
			"clarity": 7,
			"methodology":7,
			"contribution_to_field":7,
			"recommendation":"accept",
			"date_completed":"2016-09-14 08:14:49"
		},
		{
			"manuscript_id": 6,
			"person_id": 5, 
			"date_assigned": "2016-07-14 08:14:49",
			"appropriateness": 7,
			"clarity": 7,
			"methodology":7,
			"contribution_to_field":7,
			"recommendation":"accept",
			"date_completed":"2016-09-14 08:14:49"
		},
		{
			"manuscript_id": 7,
			"person_id": 2, 
			"date_assigned": "2016-07-14 08:14:49",
			"appropriateness": 7,
			"clarity": 7,
			"methodology":7,
			"contribution_to_field":7,
			"recommendation":"accept",
			"date_completed":"2016-09-14 08:14:49"
		},
		{
			"manuscript_id": 7,
			"person_id": 4, 
			"date_assigned": "2016-07-14 08:14:49",
			"appropriateness": 7,
			"clarity": 7,
			"methodology":7,
			"contribution_to_field":7,
			"recommendation":"accept",
			"date_completed":"2016-09-14 08:14:49"
		},
		{
			"manuscript_id": 7,
			"person_id": 5, 
			"date_assigned": "2016-07-14 08:14:49",
			"appropriateness": 7,
			"clarity": 7,
			"methodology":7,
			"contribution_to_field":7,
			"recommendation":"accept",
			"date_completed":"2016-09-14 08:14:49"
		}
	]
)

// insert RICodes collection
db.RICodes.insert( 
	[
		{ "_id": 1, "RIString": "Agricultural engineering"},
		{ "_id": 2, "RIString": "Biochemical engineering"},
		{ "_id": 3, "RIString": "Biomechanical engineering"},
		{ "_id": 4, "RIString": "Ergonomics"},
		{ "_id": 5, "RIString": "Food engineering"},
		{ "_id": 6, "RIString": "Bioprocess engineering"},
		{ "_id": 7, "RIString": "Genetic engineering"},
		{ "_id": 8, "RIString": "Human genetic engineering"},
		{ "_id": 9, "RIString": "Metabolic engineering"},
		{ "_id": 10, "RIString": "Molecular engineering"},
		{ "_id": 11, "RIString": "Neural engineering"},
		{ "_id": 12, "RIString": "Protein engineering"},
		{ "_id": 13, "RIString": "Rehabilitation engineering"},
		{ "_id": 14, "RIString": "Tissue engineering"},
		{ "_id": 15, "RIString": "Aquatic and environmental engineering"},
		{ "_id": 16, "RIString": "Architectural engineering"},
		{ "_id": 17, "RIString": "Civionic engineering"},
		{ "_id": 18, "RIString": "Construction engineering"},
		{ "_id": 19, "RIString": "Earthquake engineering"},
		{ "_id": 20, "RIString": "Earth systems engineering and management"},
		{ "_id": 21, "RIString": "Ecological engineering"},
		{ "_id": 22, "RIString": "Environmental engineering"},
		{ "_id": 23, "RIString": "Geomatics engineering"},
		{ "_id": 24, "RIString": "Geotechnical engineering"},
		{ "_id": 25, "RIString": "Highway engineering"},
		{ "_id": 26, "RIString": "Hydraulic engineering"},
		{ "_id": 27, "RIString": "Landscape engineering"},
		{ "_id": 28, "RIString": "Land development engineering"},
		{ "_id": 29, "RIString": "Pavement engineering"},
		{ "_id": 30, "RIString": "Railway systems engineering"},
		{ "_id": 31, "RIString": "River engineering"},
		{ "_id": 32, "RIString": "Sanitary engineering"},
		{ "_id": 33, "RIString": "Sewage engineering"},
		{ "_id": 34, "RIString": "Structural engineering"},
		{ "_id": 35, "RIString": "Surveying"},
		{ "_id": 36, "RIString": "Traffic engineering"},
		{ "_id": 37, "RIString": "Transportation engineering"},
		{ "_id": 38, "RIString": "Urban engineering"},
		{ "_id": 39, "RIString": "Irrigation and agriculture engineering"},
		{ "_id": 40, "RIString": "Explosives engineering"},
		{ "_id": 41, "RIString": "Biomolecular engineering"},
		{ "_id": 42, "RIString": "Ceramics engineering"},
		{ "_id": 43, "RIString": "Broadcast engineering"},
		{ "_id": 44, "RIString": "Building engineering"},
		{ "_id": 45, "RIString": "Signal Processing"},
		{ "_id": 46, "RIString": "Computer engineering"},
		{ "_id": 47, "RIString": "Power systems engineering"},
		{ "_id": 48, "RIString": "Control engineering"},
		{ "_id": 49, "RIString": "Telecommunications engineering"},
		{ "_id": 50, "RIString": "Electronic engineering"},
		{ "_id": 51, "RIString": "Instrumentation engineering"},
		{ "_id": 52, "RIString": "Network engineering"},
		{ "_id": 53, "RIString": "Neuromorphic engineering"},
		{ "_id": 54, "RIString": "Engineering Technology"},
		{ "_id": 55, "RIString": "Integrated engineering"},
		{ "_id": 56, "RIString": "Value engineering"},
		{ "_id": 57, "RIString": "Cost engineering"},
		{ "_id": 58, "RIString": "Fire protection engineering"},
		{ "_id": 59, "RIString": "Domain engineering"},
		{ "_id": 60, "RIString": "Engineering economics"},
		{ "_id": 61, "RIString": "Engineering management"},
		{ "_id": 62, "RIString": "Engineering psychology"},
		{ "_id": 63, "RIString": "Ergonomics"},
		{ "_id": 64, "RIString": "Facilities Engineering"},
		{ "_id": 65, "RIString": "Logistic engineering"},
		{ "_id": 66, "RIString": "Model-driven engineering"},
		{ "_id": 67, "RIString": "Performance engineering"},
		{ "_id": 68, "RIString": "Process engineering"},
		{ "_id": 69, "RIString": "Product Family Engineering"},
		{ "_id": 70, "RIString": "Quality engineering"},
		{ "_id": 71, "RIString": "Reliability engineering"},
		{ "_id": 72, "RIString": "Safety engineering"},
		{ "_id": 73, "RIString": "Security engineering"},
		{ "_id": 74, "RIString": "Support engineering"},
		{ "_id": 75, "RIString": "Systems engineering"},
		{ "_id": 76, "RIString": "Metallurgical Engineering"},
		{ "_id": 77, "RIString": "Surface Engineering"},
		{ "_id": 78, "RIString": "Biomaterials Engineering"},
		{ "_id": 79, "RIString": "Crystal Engineering"},
		{ "_id": 80, "RIString": "Amorphous Metals"},
		{ "_id": 81, "RIString": "Metal Forming"},
		{ "_id": 82, "RIString": "Ceramic Engineering"},
		{ "_id": 83, "RIString": "Plastics Engineering"},
		{ "_id": 84, "RIString": "Forensic Materials Engineering"},
		{ "_id": 85, "RIString": "Composite Materials"},
		{ "_id": 86, "RIString": "Casting"},
		{ "_id": 87, "RIString": "Electronic Materials"},
		{ "_id": 88, "RIString": "Nano materials"},
		{ "_id": 89, "RIString": "Corrosion Engineering"},
		{ "_id": 90, "RIString": "Vitreous Materials"},
		{ "_id": 91, "RIString": "Welding"},
		{ "_id": 92, "RIString": "Acoustical engineering"},
		{ "_id": 93, "RIString": "Aerospace engineering"},
		{ "_id": 94, "RIString": "Audio engineering"},
		{ "_id": 95, "RIString": "Automotive engineering"},
		{ "_id": 96, "RIString": "Building services engineering"},
		{ "_id": 97, "RIString": "Earthquake engineering"},
		{ "_id": 98, "RIString": "Forensic engineering"},
		{ "_id": 99, "RIString": "Marine engineering"},
		{ "_id": 100, "RIString": "Mechatronics"},
		{ "_id": 101, "RIString": "Nanoengineering"},
		{ "_id": 102, "RIString": "Naval architecture"},
		{ "_id": 103, "RIString": "Sports engineering"},
		{ "_id": 104, "RIString": "Structural engineering"},
		{ "_id": 105, "RIString": "Vacuum engineering"},
		{ "_id": 106, "RIString": "Military engineering"},
		{ "_id": 107, "RIString": "Combat engineering"},
		{ "_id": 108, "RIString": "Offshore engineering"},
		{ "_id": 109, "RIString": "Optical engineering"},
		{ "_id": 110, "RIString": "Geophysical engineering"},
		{ "_id": 111, "RIString": "Mineral engineering"},
		{ "_id": 112, "RIString": "Mining engineering"},
		{ "_id": 113, "RIString": "Reservoir engineering"},
		{ "_id": 114, "RIString": "Climate engineering"},
		{ "_id": 115, "RIString": "Computer-aided engineering"},
		{ "_id": 116, "RIString": "Cryptographic engineering"},
		{ "_id": 117, "RIString": "Information engineering"},
		{ "_id": 118, "RIString": "Knowledge engineering"},
		{ "_id": 119, "RIString": "Language engineering"},
		{ "_id": 120, "RIString": "Release engineering"},
		{ "_id": 121, "RIString": "Teletraffic engineering"},
		{ "_id": 122, "RIString": "Usability engineering"},
		{ "_id": 123, "RIString": "Web engineering"},
		{ "_id": 124, "RIString": "Systems engineering"}
	]
)
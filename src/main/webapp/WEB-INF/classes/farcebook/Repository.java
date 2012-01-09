package farcebook;
import java.util.*;
import java.io.*;

/** The Repository class is responsible for creating and storing {@link farcebook.Entity}, {@link farcebook.User}, {@link farcebook.Group}, and {@link farcebook.Event} objects ordered by their unique IDs. The stored Entities can be retrieved by using the {@link farcebook.Repository#getEntity(String)} method, which gets the Entity using its unique ID. The repository class is also responsible for loading the saved entities from file.
  @author Kevin Cherniawski
 */
public class Repository{

	private static Repository theInstance;
	private Hashtable<String, User> users;
	private Hashtable<String, Event> events;
	private Hashtable<String, Group> groups;


	private Repository() {
		//System.out.println("new Repository");
		users = new Hashtable<String, User>();
		events = new Hashtable<String, Event>();
		groups = new Hashtable<String, Group>();
	}

	/**
	  Returns the Repository object that contains all of the Users, Events, and Group objects that have been made and stored on Farcebook.
	 */
	public static synchronized Repository instance(){
		if(theInstance == null) {
			theInstance = new Repository();
			//theInstance.bootstrapBill();
		}
		return theInstance;
	}


	/** Creates a new {@link farcebook.User} object and returns it. If there already exists a User that contains this email, this method will return null.
	  @param email The email to be associated with the new {@link farcebook.User}.
	  @param password A {@link java.lang.String} containing the password to be associated with the new {@link farcebook.User}.
	  @return The created User object, or null if the email is already contained by another User object.
	 */
	public User createNewUser(String email, String password) {
		if(getUserByEmail(email) == null){
			User temp = new User(email, password);
			//System.out.println("Making a new User withe the email:" + email);
			users.put(temp.getID(), temp);
			//System.out.println("putting a user in the hashtable");
			return temp;
		}
		else{
			return null;
		}
	}


	/** Creates a new {@link farcebook.Group} object and returns it. If there already exists a Group that contains the same displayName, this method will return null.
	  @param displayName A {@link java.lang.String} containing the name to be associated with the new {@link farcebook.Group}.
	  @param description A {@link java.lang.String} containing the description to be associated with the new {@link farcebook.Group}.
	  @return The created Group object, or null if the displayName is already contained by another Group object.
	 */
     public Group createNewGroup(String displayName, String description) {
          Group temp = new Group(displayName, description);
//          System.out.println("Making a new group withe the name:" + displayName);
          groups.put(temp.getID(), temp);
//          System.out.println("putting a group in the hashtable");
          return temp;
     }


	/** Creates a new {@link farcebook.Event} object and returns it. If there already exists an Event that contains the same displayName, this method will return null. 
	  @param displayName A String containing the name to be associated with the new {@link farcebook.Event}.
	  @param startDate The Date object that represents the start of the event.
	  @param endDate The date object that represents the end of the event.
	  @param location A String containing the location to be associated with the event
	  @param description A String containing the description to be associated with the new {@link farcebook.Event}.
	  @return The created Event object, or null if the displayName is already contained in another Event object.
	 */
	public Event createNewEvent(String displayName, Date startDate, 
	     Date endDate, String location, String description){
		Event temp = new Event(displayName, startDate, endDate, location, description);
		events.put(temp.getID(), temp);
		return temp;
	}


	/** Returns the {@link farcebook.User} associated with the given email. If the no User in the Repository contains that email, this method returns null.
	  @param email The email of the user being requested
	  @return The User object that contains the given email, or null if no User object in the Repository contains the given email.
	 */
	public User getUserByEmail(String email){
		Enumeration<User> tmp = users.elements();
		while(tmp.hasMoreElements()) {
			User test = tmp.nextElement();
			if(test.getEmail().equals(email)) {
				return test;
			}
		}
		return null;
	}

	/**
	  Returns an ArrayList of User objects that contain the given String in their names. If there are no User objects containing the passed String within their name, it returns an empty ArrayList.
	  @param name The name of the User that is being searched for.
	  @return An ArrayList of Users that contain the given name.
	 */

	public ArrayList<User> getUsersByName(String name){
		ArrayList<User> matches = new ArrayList<User>();
		Enumeration<User> temp = users.elements();
		while(temp.hasMoreElements()){
			User test = temp.nextElement();
//			System.out.println("in getUsersByName " + test.getDisplayName());	
			if(test.getDisplayName().toLowerCase().contains(name.toLowerCase())){
				matches.add(test);
			}
		}
//		System.out.println("The repository has these usernames" );

		Enumeration<User> temp2 = users.elements();
		while(temp2.hasMoreElements()){
			User test = temp2.nextElement();
			System.out.println(test.getDisplayName());
		}		


//		System.out.println("The name of the search is " + name);
//		System.out.println("The matchs are " + matches);
		return matches;	 
	}	

	/**
	  Returns an ArrayList of Group objects that contain the given String in their names. If there are no Group objects containing the passed String within their name, it returns an empty ArrayList.
	  @param name The name of the Group that is being searched for.
	  @return An ArrayList of Groups that contain the given name.
	 */

	public ArrayList<Group> getGroupsByName(String name){
                ArrayList<Group> matches = new ArrayList<Group>();
                Enumeration<Group> temp = groups.elements();
                while(temp.hasMoreElements()){
                        Group test = temp.nextElement();
  //                      System.out.println("in getGroupsByName " + test.getDisplayName());
                        if(test.getDisplayName().toLowerCase().contains(name.toLowerCase())){
                                matches.add(test);
                        }
                }
    //            System.out.println("The repository has these groupnamess" );

                Enumeration<Group> temp2 = groups.elements();
                while(temp2.hasMoreElements()){
                        Group test = temp2.nextElement();
                        System.out.println(test.getDisplayName());
                }


      //          System.out.println("The name of the search is " + name);
      //          System.out.println("The matchs are " + matches);
                return matches;

	}

	/**
	  Returns an ArrayList of Event objects that contain the given String in their names. If there are no Event objects containing the passed String within their name, it returns an empty ArrayList.
	  @param name The name of the Events that is being searched for.
	  @return An ArrayList of Events that contain the given name.
	 */

	public ArrayList<Event> getEventsByName(String name){
                ArrayList<Event> matches = new ArrayList<Event>();
                Enumeration<Event> temp = events.elements();
                while(temp.hasMoreElements()){
                        Event test = temp.nextElement();
                        //System.out.println("in getEventsByName " + test.getDisplayName());
                        if(test.getDisplayName().toLowerCase().contains(name.toLowerCase())){
                                matches.add(test);
                        }
                }
                System.out.println("The repository has these groupnamess" );
                
                Enumeration<Event> temp2 = events.elements();
                while(temp2.hasMoreElements()){
                        Event test = temp2.nextElement();
                        //System.out.println(test.getDisplayName());
                }       
                                

                //System.out.println("The name of the search is " + name);
                //System.out.println("The matchs are " + matches);
                return matches;

	}




	/** Returns the {@link farcebook.Entity} associated with the given ID. If no Entity within the Repository conatins the ID, this method will return null. 
	  @param ID A unique ten character String.
	  @return The Entity object containing the unique ID, or null if there is no Entity object with the given ID.
	 */
	public Entity getEntity(String ID){
		Hashtable<String, Entity> masterHash = new Hashtable<String, Entity>();
		masterHash.putAll(users);
		masterHash.putAll(events);
		masterHash.putAll(groups);

		Enumeration<Entity> tmp = masterHash.elements();
		while(tmp.hasMoreElements()){
			Entity test = tmp.nextElement();
			if(test.getID().equals(ID)){
				return test;
			}
		}
		return null;
	}

     private void bootstrapBill() {
          File f = new File ("/home/dlederle/workspace/farcebook/src/main/webapp/data/users");
          String[] filenames = f.list(
               new FilenameFilter() {
                    public boolean accept(File dir, String name) {
                         if (name.endsWith(".user")) {
                              return true;
                         }
                         else {
                              return false;
                         }
                    }
               });

          File g = new File ("/home/dlederle/workspace/farcebook/src/main/webapp/data/groups");
          String[] groupnames = g.list(
                    new FilenameFilter() {
                    public boolean accept(File dir, String name) {
                    if (name.endsWith(".group")) {
                         return true;
                    }
                    else {
                         return false;
                    }
                    }
                    });


          File e = new File ("/home/rkinsman/tomcat/webapps/farceRepo/data/events");
          String[] eventnames = e.list(
                    new FilenameFilter() {
                    public boolean accept(File dir, String name) {
                    if (name.endsWith(".event")) {
                    return true;
                    }
                    else {
                    return false;
                    }
                    }
                    });


		for (int i = 0; i<filenames.length; i++){
			User user = new User();
			int index = filenames[i].indexOf('.');
			String userID = filenames[i].substring(0,index);
			user.setID(userID);
			users.put(userID, user);
		}

                for (int i = 0; i<groupnames.length; i++){
                        Group group= new Group();
                        int index = groupnames[i].indexOf('.');
                        String groupID = groupnames[i].substring(0,index);
                        group.setID(groupID);
                        groups.put(groupID, group);
			System.out.println("Reloaded & added group " + groupID);
                }


		for (int i = 0; i<eventnames.length; i++){
                        Event event= new Event();
                        int index = eventnames[i].indexOf('.');
                        String eventID = eventnames[i].substring(0,index);
                        event.setID(eventID);
                        events.put(eventID, event);
                        System.out.println("Reloaded & added event " + eventID);
                }



		for (int i = 0; i<filenames.length; i++){
			int index= filenames[i].indexOf('.');
			String userID = filenames[i].substring(0,index);
			users.get(userID).load(filenames[i]);
		}


                for (int i = 0; i<groupnames.length; i++){
                        int index= groupnames[i].indexOf('.');
                        String groupID = groupnames[i].substring(0,index);
                        groups.get(groupID).load(groupnames[i]);
			System.out.println("LOADED DERKA DER " + groupID);
                }

		for (int i = 0; i<eventnames.length; i++){
                        int index= eventnames[i].indexOf('.');
                        String eventID = eventnames[i].substring(0,index);
                        events.get(eventID).load(eventnames[i]);
                        System.out.println("LOADED DERKA DER " + eventID);
                }


		
	}



}


package farcebook;
import java.util.*;

/** Entity is an abstract class that encapsulates all {@link farcebook.User}, {@link farcebook.Group}, and {@link farcebook.Event} objects. Entities contain information needed to display a profile and keep track of its own subscribees.

  Entity objects hold all of the instance variables and methods needed to display a profile on the Farcebook website and hold all of the Entity's subscribers, {@link farcebook.Feed}, {@link farcebook.Album}, and {@link farcebook.Request} objects. Also, upon declaration of each Entity object, it contains a unique identifier. This is a String with length 10 of random uppercase, lowercase, and numeric characters.
  Each Entity has one Feed which is a collection of {@link farcebook.Post} objects retrieved from each user's {@link farcebook.Wall}.

  Entity objects also hold a private ArrayList of User objects called "admins". User objects within this list have the permission to edit the information of the Entity object. For User objects, there is only one admin; the user himself. For Event and Group objects, admins are the users who have been given permission by the original admin (the creator) to edit the page. 

  Entity objects are saved and created through the Singleton {@link farcebook.Repository} object.
  @author Ross Kinsman
 */
public abstract class Entity {

     protected String filepath; //Set to your tomcat (i.e "home/dlederle/tomcat/farceBook/)
     protected String ID;
     protected String displayName;
     protected String status;
     protected ArrayList<User> admins = new ArrayList<User>();
     protected ArrayList<User> subscribedTo = new ArrayList<User>();
     protected ArrayList<Request> requests = new ArrayList<Request>();
     protected ArrayList<Album> albums = new ArrayList<Album>();
     protected Wall wall = new Wall(this);
     protected Picture profilePicture = new Picture("http://cas.umw.edu/computerscience/files/2011/09/sdavies.jpg", this);
     protected ArrayList<Group> groups = new ArrayList<Group>();
     protected ArrayList<Event> events = new ArrayList<Event>();
     protected int dislikes;	


     protected Entity(){

          //Generates a new ID
          String AB = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
          Random rnd = new Random();
          String sb;
          boolean original = false;
          while ( !original )
          {
               sb = "";
               for( int i = 0; i < 10; i++ )
               {
                    sb += AB.charAt( rnd.nextInt(AB.length()) );
               }
               ID = sb.toString();
               Repository rp = Repository.instance();
               if (rp.getEntity( ID ) != null)
               {
                    original = false;
               }
               else 
               {
                    original = true;
               }
          }
          dislikes = 0;
     }

     /**
       To load with.
      */
     Entity(String ID)
     {
          this.ID = ID;
     }

     /** 
       Returns an ArrayList of {@link farcebook.Post} containing all of the Post objects of this Entity. 
       The Entity contains a collection of Post objects of which it is the reciever. If this Entity contains no Post objects, this method will return an empty ArrayList.
       @return An ArrayList of Post objects, or empty ArrayList if it contains none.
      */
     public ArrayList<Post> getPosts()
     {
          return wall.getPosts();
     }

     public Post getPost(int i)
     {
          return wall.getPost(i);
     }

     public ArrayList<Post> getFeed(){
          Feed me = new Feed(this);
          return me.getPosts();
     }

     /**
       Returns an ArrayList of {@link farcebook.User} objects containing all of the Users that the Entity has subscribed to. If the Entity contains no subcribees, then it will return an empty ArrayList.
       @return An ArrayList of Users, or an empty ArrayList if this Entity contains none.  
      */
     public ArrayList<User> getSubscribedTo()
     {
          return subscribedTo;
     }

     public void makeProfilePicture( Picture picture )
     {
          profilePicture = picture;
          if (!albums.get(1).getPictures().contains(picture))
          {
               albums.get(1).getPictures().add(picture);
          }
     }


     public Picture getPicture(String ID){
          return albums.get(0).getPicture(ID);	
     }
	

	public boolean isAdmin(User user){return false;}

     /** 
       Adds the specified {@link farcebook.User} to the Entity's friends or members list. If the User that the Entity is adding to its subscriber list is already present, an Exception will be thrown, printing "That user is already subscribed to you."
       @param user The User object that this Entity adds to its list of subscribers.
      */
     public synchronized void subscribeTo(User user) throws Exception
     {    	
          if (subscribedTo.contains(user))
          {
               throw new Exception("That user is already subscribed to you.");
          }
          else
          {
               subscribedTo.add(user);
               save();
          }
     }

     public synchronized void post(String text, User author, Date date) {
          wall.post(text, author, date);
          save();
     }

     public Post makePost(String text, User author, Date date)
     {
          return new Post(text, author, date);

     }

     public synchronized void comment(String text, User author, Date date, int postNum){
          this.wall.getPosts().get(postNum).comment(text, author, date);
          save();
     }

     void save(){}

     void load(String filename){
     }

     public synchronized void setStatus(String text){
          status = text;
          save();
     }

     public String getStatus(){
          return status;
     }

	public void sendEmail(String address, String subject, String content) throws Exception{
		
	}

	public String getEmail(){
		return "stephen@mail.umw.edu";
	}

     public void dislike(){
          dislikes++;
          save();
     }

     public int getDislikes() {
          return dislikes;
     }



     /**
       Adds the specified {@link farcebook.Album} to the list of Albums owned by this Entity. The same album cannot be owned by more than one Entity. If an album with that title already exists in that User's list of albums, an Exception is thrown that says, "There is already an album with that name."
       @param title The title of the Album object to be added.
      */
     public synchronized void addAlbum(Album newAlbum)
     {
          albums.add(newAlbum);
          save();
     }	

     public Album makeAlbum(String title)
     {
          return new Album(title, this);
     }

     public ArrayList<Album> getAlbums() {
          return albums;
     }

     public Album getAlbum(String ID)
     {
          for (int i = 0; i < albums.size(); i++)
          {
               if (albums.get(i).getID().equals(ID))
               {
                    return albums.get(i);
               }
          }
          return null;
     }

     public Wall getWall(){
          return wall;
     }

     public String getInfo(){
          return "Error";
     }

     /** 
       Adds the specified {@link farcebook.Picture} to the allPictures Album. allPictures is the default Album object and also holds every {@link farcebook.Picture} object that this Entity has uploaded. The allPictures Album gives the user the option to not designate a specific album during picture upload. Neither the title nor the source need to be unique, however if the source being passed is not an image file, an exception will be thrown. If the title being passed is null, an empty string will hold the value of the Picture object's title.
       @param title The title of the Picture object to be added.
       @param source The path of where the image file is located. 
      */
     public synchronized void addPicture(Picture newPicture)
     {
          albums.get(0).addPicture(newPicture);
          save();
     }

     public Picture makePicture(String filename)
     {
          return new Picture(filename, this);
     }

     /** 
       Adds the specified {@link farcebook.Request} object to all the admins of this Entity object.
       @param request The request to be added.
      */
     public synchronized void addRequest(Request request)
     {
          requests.add(request);
          save();
     }

     /**
       Removes the specified {@link farcebook.Request} object from all of the admins of this Entity object. If the request object does not exist in any one of the admins' requests lists, an exception will be thrown.
      */
     public synchronized void removeRequest(Request request) 
     {
          // Concurrency issues here.
          requests.remove(request);
          save();
     }

     /**
       Returns the ID of this Entity object.
       @return The ID of this Entity object.
      */
     public String getID()
     {
          return ID;
     }

     public synchronized void setID(String ID){
          this.ID = ID;
     }

     /**
       Returns the displayName of this Entity object.
       @return The displayName of this Entity object. 
      */
     public String getDisplayName()
     {
          //System.out.println("The Display name is... " + displayName);
          return displayName;
     }

     /**
       Sets the displayName to the value of the passed String.
       @param name The name desired as the displayName of this Entity object.
      */
     public synchronized void setDisplayName(String name)
     {
          displayName = name;
          save();
     }

     /**
       Returns the Picture object that is designated as this Entity's profile picture. If there is no profilePicture, an exception will be thrown.
       @return The Picture object designated as this Entity's profile picture.
      */
     public Picture getProfilePicture()
     {
          return profilePicture;
     }

     public synchronized void addGroup(Group group){
          groups.add(group);
          save();
     }

     public synchronized void addEvent(Event event){
          events.add(event);
          save();
     }

	public void requestSubscription(User derp){

	}

     public String getSubscriberType() {
          return "Subscriber";
     }

	public String getSubscriberAction(){
		return "HERPY DERP";
	}

     public ArrayList<Group> getGroups(){
          return groups;
     }

     public ArrayList<Event> getEvents(){
          return events;
     }

}


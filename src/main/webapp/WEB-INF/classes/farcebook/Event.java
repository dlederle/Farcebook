package farcebook;
import java.util.*;
import java.io.*;

/**
  Event represents an event at a particular time and place, with one or more attendees. Events can be created by the {@link farcebook.Repository}.
  Event is the way in which Farcebook users can plan social gatherings and meetings involving one or more attendees.
  Event objects differ from {@link farcebook.Group} objects in that they are time-limited and revolve around a single activity.
  Event inherits from {@link farcebook.Entity} and represents an event {@link farcebook.User} can join and interact with. Events, from Entity, inherit {@link farcebook.Album}, {@link farcebook.Request}, {@link farcebook.Feed} objects. Events can hold multiple admins. Events have several instance variables; the date at which they were created, their location, start date, end date, and description.
  @author Dylan Lederle
 */
public class Event extends Entity {

	String description;
	String location;
	Date startDate;
	Date endDate;

	/**
	  Creates a new Event.
	  @param displayName The name displayed in the Event profile.
	  @author dlederle
	 */
	Event(String displayName, Date startDate, Date endDate, String location, String description) 
	{     
		this.description = description;
		this.location = location;
		this.startDate = startDate;
		this.endDate = endDate;
		this.displayName = displayName;
         	 profilePicture.setTitle("...Awwww Yeah");
         	 Album allPictures = new Album("All Pictures", this);
         	 allPictures.addPicture(profilePicture);
         	 albums.add(allPictures);
		save();
	}

	Event()
	{
		description = "";
		location = "";
		displayName = "";
		startDate = new Date();
		endDate = new Date();
	}

	public String getInfo(){
		String info = "";
		info += "Description: "+ description +"<br/>";
		info += "Starts: "+ startDate.toString() +"<br/>";
		info += "Ends: "+ endDate.toString()+"<br/>";
		info += "Location: "+ location +"<br/>";
		
		return info;
	}

	public void sendEmail(String address, String subject, String content) throws Exception{
		admins.get(0).sendEmail(address, subject, content);
	}

	public String getEmail(){
		return admins.get(0).getEmail();
	}

	/**
	  Makes the specified user an administrator of the event. Admins have several special privileges; changing the event instance variables, adding members, deleting wall posts, sending {@link farcebook.Request} objects, and appointing other admins. If the user is already an admin of the Event, or is not a member of the Event, then an exception will be thrown.  
	  @param user The user to make into an admin.
	 */
	public synchronized void makeAdmin(User user) throws Exception
	{
		if (isAdmin(user))
		{
			throw new Exception("This user is already an Administrator");
		}
		else if (!subscribedTo.contains(user))
		{
			throw new Exception("This user is not in the Event.");
		}
		else
		{
			admins.add(user);
			save();
		}
	}

	/**
	  Checks if the given {@link farcebook.User} is authorized as an administrator of the Event.
	  @param user The user to verify.
	  @return Returns true if the member is an admin, false otherwise.
	 */
	public boolean isAdmin(User user)
	{
		return admins.contains(user);     
	}

	/**
	  A {@link farcebook.User} object is added to this Event's attendees. This is used when a User accepts an {@link EventInvite} object. If the user is already attending the Event, then an exception will be thrown.
	  @param respondingUser The invited user who has confirmed their attendecne to this Event.
	 */
	public synchronized void RSVP(User respondingUser) 
	{
		try {
			subscribeTo(respondingUser);	
			save();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void requestSubscription(User asker){
		
	}

	public String getSubscriberType() {
		return "Attendee";
	}
	public String getSubscriberAction() {
		return "Join";       
	}

	void load(String eventFileName) 
	{

		try {
			System.out.println("\n\n\n\n\n LOADING THE Event!!! with fileName " + eventFileName);
			Repository repo = Repository.instance();
			BufferedReader br = new BufferedReader(new FileReader("/home/rkinsman/tomcat/webapps/farceRepo/data/events/" + eventFileName));
			String fileLine = br.readLine();
			System.out.println("The location for the group is:" + fileLine.substring(fileLine.indexOf(":") + 1));
			location = fileLine.substring(fileLine.indexOf(":") + 1);
			fileLine = br.readLine();
			System.out.println("The start date for the event is: " + fileLine.substring(fileLine.indexOf(":") + 1));
			long startEpoch = Long.parseLong(fileLine.substring(fileLine.indexOf(":") + 1));
			Date startDate = new Date(startEpoch);
			this.startDate = startDate;
			fileLine = br.readLine();
			System.out.println("The end date is: " + fileLine.substring(fileLine.indexOf(":") + 1));
			long endEpoch = Long.parseLong(fileLine.substring(fileLine.indexOf(":") + 1));
			Date endtDate = new Date(endEpoch);
			this.endDate = endDate;
			fileLine = br.readLine();
			System.out.println("The  description is: " + fileLine.substring(fileLine.indexOf(":") + 1));
			description = fileLine.substring(fileLine.indexOf(":") + 1);
			fileLine = br.readLine();
			while (!fileLine.equals("010001110110000101101110011001110100111101100110010001100110111101110101011100100000110100001010")){
				description += fileLine;
				fileLine = br.readLine();
			}
			System.out.println("This line should be binary: " + fileLine);
			fileLine = br.readLine();
			System.out.println("This line should be wall: " + fileLine);
			fileLine = br.readLine();

			int counter = 0; //To know which post we need to ass comments to
			while(!fileLine.equals("010001110110000101101110011001110100111101100110010001100110111101110101011100100000110100001010")){ // get posts
				if(fileLine.equals("?!(post)!?")){
					fileLine = br.readLine();
					System.out.println("the post's text is " + fileLine.substring(fileLine.indexOf(":") + 1));
					String postText = fileLine.substring(fileLine.indexOf(":") + 1);
					fileLine= br.readLine();
					while (!fileLine.contains("author:")){
						postText += fileLine;
						fileLine = br.readLine();
					}
					System.out.println("the author is " + fileLine.substring(fileLine.indexOf(":") + 1));
					String postAuthor = fileLine.substring(fileLine.indexOf(":") + 1);
					fileLine = br.readLine();
					System.out.println("the epoch is " + fileLine.substring(fileLine.indexOf(":") + 1));
					long epoch = Long.parseLong(fileLine.substring(fileLine.indexOf(":") + 1));
					Date postDate = new Date(epoch);
					wall.post(postText, (User) repo.getEntity(postAuthor), postDate);
					fileLine= br.readLine();
					System.out.println("The next line is: " + fileLine);

					while(fileLine.equals("?!(comment)!?")) {
						fileLine= br.readLine();
						System.out.println("comment text is " + fileLine.substring(fileLine.indexOf(":") + 1));
						String commentText = fileLine.substring(fileLine.indexOf(":") + 1);
						fileLine= br.readLine();
						while (!fileLine.contains("author:")){
							commentText += fileLine;
							fileLine = br.readLine();
						}
						System.out.println("comment author is " + fileLine.substring(fileLine.indexOf(":") + 1));
						String commentAuthor = fileLine.substring(fileLine.indexOf(":") + 1);
						fileLine = br.readLine();
						System.out.println("commentEpoch is " + fileLine.substring(fileLine.indexOf(":") + 1));
						long commentEpoch = Long.parseLong(fileLine.substring(fileLine.indexOf(":") + 1));
						Date commentDate = new Date(commentEpoch);
						wall.getPost(counter).comment(commentText, (User) repo.getEntity(commentAuthor), commentDate);
						fileLine = br.readLine();
						System.out.println("The next line is " + fileLine);
					}
					counter++;
				}

				else {
					System.out.println("RUH RO RAGGY!!!! SHOULD NOT BE GETTING HERE! Check Your load method with wallposts!");
					br.readLine();
				}
			}


			fileLine= br.readLine();
			System.out.println("The next line is " + fileLine);
			while(!fileLine.equals("010001110110000101101110011001110100111101100110010001100110111101110101011100100000110100001010")){
				if(fileLine.equals("?!(album)!?")){
					fileLine=br.readLine();
					System.out.println("albumTitle is " + fileLine.substring(fileLine.indexOf(":") + 1));
					String albumTitle = fileLine.substring(fileLine.indexOf(":") + 1);
					fileLine = br.readLine();
					System.out.println("albumEpoch is " + fileLine.substring(fileLine.indexOf(":") + 1));
					long albumEpoch = Long.parseLong(fileLine.substring(fileLine.indexOf(":") + 1));
					fileLine = br.readLine();
					System.out.println("albumnAuthor is " + fileLine.substring(fileLine.indexOf(":") + 1));
					String albumAuthor = fileLine.substring(fileLine.indexOf(":") + 1);
					fileLine = br.readLine();
					System.out.println("The albumID is " + fileLine.substring(fileLine.indexOf(":") + 1));
					String albumID = fileLine.substring(fileLine.indexOf(":") + 1);
					fileLine = br.readLine();
					System.out.println("The next line is " + fileLine);
					Album album = new Album(albumTitle, repo.getEntity(albumAuthor));
					album.setDate(albumEpoch);
					album.setID(albumID);
					albums.add(album);
					while (fileLine.equals("?!(picture)!?")){
						fileLine = br.readLine();
						System.out.println("The pictureID is " + fileLine.substring(fileLine.indexOf(":") + 1));
						String pictureID = fileLine.substring(fileLine.indexOf(":") + 1);
						fileLine = br.readLine();
						System.out.println("The pictureTitle is " + fileLine.substring(fileLine.indexOf(":") + 1));
						String pictureTitle = fileLine.substring(fileLine.indexOf(":") + 1);
						fileLine = br.readLine();
						System.out.println("thePictureImage is " + fileLine.substring(fileLine.indexOf(":") + 1));
						String pictureImage = fileLine.substring(fileLine.indexOf(":") + 1);
						fileLine= br.readLine();
						System.out.println("pictureEpoch is " + fileLine.substring(fileLine.indexOf(":") + 1));
						long pictureEpoch = Long.parseLong(fileLine.substring(fileLine.indexOf(":") + 1));
						fileLine = br.readLine();
						System.out.println("pictureAuthor is " + fileLine.substring(fileLine.indexOf(":") + 1));
						String pictureAuthor = fileLine.substring(fileLine.indexOf(":") + 1);
						Picture picture = new Picture (pictureImage, (Event) repo.getEntity(pictureAuthor));
						picture.setID(pictureID);
						picture.setTitle(pictureTitle);
						picture.setDate(pictureEpoch);
						album.addPicture(picture);
						fileLine= br.readLine();
						System.out.println("the nextLine is " + fileLine);
						while (fileLine.equals("?!(comment)!?")){
							fileLine= br.readLine();
							System.out.println("pictureCommentText is " + fileLine.substring(fileLine.indexOf(":") + 1));
							String commentText = fileLine.substring(fileLine.indexOf(":") + 1);
							fileLine= br.readLine();
							while (!fileLine.contains("author:")){
								commentText += fileLine;
								fileLine = br.readLine();
							}
							System.out.println("pictureCommentAuthor is " + fileLine.substring(fileLine.indexOf(":") + 1));
							String commentAuthor = fileLine.substring(fileLine.indexOf(":") + 1);
							fileLine = br.readLine();
							System.out.println("pictureCommentEpoch is " + fileLine.substring(fileLine.indexOf(":") + 1));
							long commentEpoch = Long.parseLong(fileLine.substring(fileLine.indexOf(":") + 1));
							Date epochDate = new Date(commentEpoch);
							Post comment = new Post(commentText, (User) repo.getEntity(commentAuthor), epochDate);
							picture.addPost(comment);
							fileLine = br.readLine();
							System.out.println("The next line is: " + fileLine);
						}
					}


				}
				else{
					System.out.println("DERKA DER NO ALBUMS");
					br.readLine();
				}


			}
			fileLine = br.readLine();
			fileLine = br.readLine();
			ID = fileLine.substring(fileLine.indexOf(":") + 1);
			System.out.println("The ID is " + fileLine.substring(fileLine.indexOf(".") + 1));
			fileLine = br.readLine();
			System.out.println("The next line is: " + fileLine);
			fileLine = br.readLine();
			while (!fileLine.contains("profilePicture:")){
				System.out.println("The next admin is: " + fileLine);
				admins.add((User) repo.getEntity(fileLine.substring(fileLine.indexOf(":") +1)));
				fileLine = br.readLine();
				System.out.println("The next admin is: " + fileLine);
			}
			System.out.println("The profilePicture is: " + fileLine.substring(fileLine.indexOf(":") + 1));
			profilePicture = albums.get(0).getPictures().get(0);
			fileLine = br.readLine();
			System.out.println("The displayName is: " + fileLine);
			displayName = fileLine.substring(fileLine.indexOf(":") + 1);
			fileLine = br.readLine();
			System.out.println("The next line is: " + fileLine);
			fileLine= br.readLine();
			System.out.println("The next line is: " + fileLine);
			while(!fileLine.equals("?!(dislikes)!?")){
				subscribedTo.add((User) repo.getEntity(fileLine));
				System.out.println("The subscribed user is: " + fileLine);
				fileLine = br.readLine();
			}
			fileLine = br.readLine();
			dislikes = Integer.parseInt(fileLine.substring(fileLine.indexOf(":") + 1));


		}

		catch (Exception e){
			e.printStackTrace();
		}

	}

	void save() 
	{
		try {
			FileWriter saveFile = new FileWriter("/home/rkinsman/tomcat/webapps/farceRepo/data//events/" + ID + ".event");


			saveFile.write("location:" + location + "\n");
			saveFile.write("startDate:" + startDate.getTime() + "\n");
			saveFile.write("endDate:" + endDate.getTime() + "\n");
			saveFile.write("description:" + description + "\n");
			saveFile.write("010001110110000101101110011001110100111101100110010001100110111101110101011100100000110100001010" + "\n");

			saveFile.write("?!(wall)!?" + "\n");

			for(int i=0; i<wall.getPosts().size(); i++){
				saveFile.write("?!(post)!?" + "\n");                                                                        //User's posts
				saveFile.write("text:" + wall.getPosts().get(i).getText() +"\n");
				saveFile.write("author:" + wall.getPosts().get(i).getAuthor().getID() + "\n");
				saveFile.write("date:" + wall.getPosts().get(i).getDate().getTime() + "\n");

				for(int j=0; j<wall.getPosts().get(i).getComments().size(); j++){                   //Comments per post
					saveFile.write("?!(comment)!?" + "\n");
					saveFile.write("text:" + wall.getPosts().get(i).getComments().get(j).getText() +"\n");
					saveFile.write("author:" + wall.getPosts().get(i).getComments().get(j).getAuthor().getID() +"\n");
					saveFile.write("date:" + wall.getPosts().get(i).getComments().get(j).getDate().getTime() +"\n");
				}
			}

			saveFile.write("010001110110000101101110011001110100111101100110010001100110111101110101011100100000110100001010" + "\n");

			for(int i=0; i<albums.size(); i++){
				saveFile.write("?!(album)!?" + "\n");
				saveFile.write("title:" + albums.get(i).getTitle() + "\n");
				saveFile.write("date:" + albums.get(i).getDate().getTime() + "\n");
				saveFile.write("author:" + albums.get(i).getAuthor().getID() + "\n");
				saveFile.write("ID:" + albums.get(i).getID() + "\n");

				for(int j=0; j<albums.get(i).getPictures().size(); j++){
					saveFile.write("?!(picture)!?" + "\n");
					saveFile.write("ID:" + albums.get(i).getPictures().get(j).getID() +"\n");
					saveFile.write("title:" + albums.get(i).getPictures().get(j).getTitle() + "\n");
					saveFile.write("image:" + albums.get(i).getPictures().get(j).getSource() +"\n");
					saveFile.write("date:" + albums.get(i).getPictures().get(j).getDate().getTime() + "\n");
					saveFile.write("author:" + albums.get(i).getPictures().get(j).getOwner().getID() + "\n");

					for(int k=0; k< albums.get(i).getPictures().get(j).getPicturePosts().size(); k++){
						saveFile.write("?!(comment)!?" + "\n");
						saveFile.write("text:" + albums.get(i).getPictures().get(j).getPicturePosts().get(k).getText() + "\n");
						saveFile.write("author:" + albums.get(i).getPictures().get(j).getPicturePosts().get(k).getAuthor() + "\n");
						saveFile.write("date:" + albums.get(i).getPictures().get(j).getPicturePosts().get(k).getDate().getTime() +"\n");
					}
				}
			}


			saveFile.write("010001110110000101101110011001110100111101100110010001100110111101110101011100100000110100001010" + "\n");
			saveFile.write("?!(entity)!?" + "\n");
			saveFile.write("ID:" + getID() + "\n");
			saveFile.write("admins:" + "\n");
			for (int i = 0; i<admins.size(); i++){
				saveFile.write(admins.get(i).getID() + "\n");
			}
			saveFile.write("profilePicture:" + profilePicture.getID() +"\n");
			saveFile.write("displayName:" + getDisplayName() + "\n");
			saveFile.write("?!(subscibees)!?\n");
			for (int i = 0; i<subscribedTo.size(); i++){
				saveFile.write(subscribedTo.get(i).getID() +"\n");
			}
			saveFile.write("?!(dislikes)!?\n");
			saveFile.write("dislikes:" + dislikes);
			saveFile.close();


		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

}

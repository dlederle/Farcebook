package farcebook;
import java.util.*;
import java.io.*;

/**
  Group inherits from {@link farcebook.Entity} and represents a group that {@link farcebook.User} objects can join and interact with. 
  Groups, from Entity, inherit {@link farcebook.Album}, {@link farcebook.Request}, and {@link farcebook.Feed} objects.
  Groups can hold multiple admins. They also hold as instance variables the date they were created and their description, set by the admin during instantiation. Groups differ from {@link farcebook.Event} objects in the respect that they have no finite time-limt. 
  @author Michael Crawford
 */
public class Group extends Entity
{

	//	private Date dateCreated;
	private String description;
	private ArrayList<Request> requests;

	/**
	  Creates a new Group object that has a mutable ArrayList of admins, with explanatory parameters. Inherits {@link farcebook.Entity} functionality.
	  @param displayName The group's name as displayed on the webpage.
	  @param description The group's description. 
	 */
	Group(String displayName, String description)
	{
		this.description = description;
		this.displayName = displayName;
		requests = new ArrayList<Request>();
         	 profilePicture.setTitle("...Awwww Yeah");
         	 Album allPictures = new Album("All Pictures", this);
         	 allPictures.addPicture(profilePicture);
         	 albums.add(allPictures);
		save();
	}

	Group()
	{
		requests = new ArrayList<Request>();
		description = "";
		displayName = "";
	}

	/**
	  Makes the specified {@link farcebook.User} an administrator of the group. Admins have several special priviledges: changing the groups instance variables, adding members, deleting wall posts, responding to {@link farcebook.Request} objects, and appointing other admins. If the user is already an admin of the group or is not a member of the group, an exception will be thrown indicating this.  
	  @param user The user to make into an admin.
	 */
	public synchronized void makeAdmin(User user) throws Exception
	{
		if (isAdmin(user))
		{
			throw new Exception("This user is already an administrator of the group.");
		}
		else if (!subscribedTo.contains(user))
		{
			throw new Exception("This user is not a member of the group.");
		}
		else
		{	
			admins.add(user);
			save();	
		}
	}

	/**
	  Checks to see if the given user is an administrator of the group.
	  @param user The user to verify.
	  @return Returns true if the member is an admin, false otherwise.	
	 */
	public boolean isAdmin(User user)
	{
		return admins.contains(user);    
	}

	/**
	  This is called by a {@link farcebook.User} object on the Group to request membership. This creates a Request object from the User and puts it within the Group object's ArrayList of Requests.
	  @param requestingUser The user passes themself to the given group for consideration.
	 */
	public synchronized void requestSubscription(User requestingUser)
	{
		GroupRequest request = new GroupRequest(requestingUser, this, requestingUser.getDisplayName() + "wants to join your group!");
		for (int i = 0; i<admins.size(); i++){
			admins.get(i).addRequest(request);
			System.out.print("sending " + admins.get(i).getDisplayName() + " a request");
		}
		this.addRequest(request);
		save();
	}

	public String getSubscriberType() {
		return "Member";
	}

	public String getSubscriberAction() {
		return "Join";
	}

	public void sendEmail(String address, String subject, String content) throws Exception{
		admins.get(0).sendEmail(address, subject, content);     
	}

	public String getEmail(){
		return admins.get(0).getEmail();
	}

	public String getInfo(){
		return description;
	}


	/**
	  This method removes a Request from the list of requests possessed by all of the admins of the Group. This is called when any admin of the group accepts a request for membership from some User object. If no such request exists, and Exception will be thrown indicating this. 
	  @param groupRequest The request that has been accepted and needs to be removed from the list of requests.
	 */
	public synchronized void resolveRequest(Request groupRequest)
	{
		for (int i = 0; i<admins.size(); i++){
			admins.get(i).removeRequest(groupRequest);
		}
		this.removeRequest(groupRequest);		
		save();
	}
	/*
	   void setDate(long date)
	   {
	// Did this in Twitter. Copy/Paste.
	}
	 */
	void load(String groupFileName)
	{
		try {
			System.out.println("\n\n\n\n\n LOADING THE GROUP!!! with fileName " + groupFileName);
			Repository repo = Repository.instance();
			BufferedReader br = new BufferedReader(new FileReader("/home/rkinsman/tomcat/webapps/farceRepo/data/groups/" + groupFileName));
			String fileLine = br.readLine();
			System.out.println("The description for the group is:" + fileLine.substring(fileLine.indexOf(":") + 1));
			description = fileLine.substring(fileLine.indexOf(":") + 1);
			fileLine = br.readLine();
			while (!fileLine.equals("010001110110000101101110011001110100111101100110010001100110111101110101011100100000110100001010")){
				description += fileLine;
				fileLine = br.readLine();	
			}
			System.out.println("The next line is: " + fileLine);
			fileLine = br.readLine();
			System.out.println("The next line is: " + fileLine);
			fileLine = br.readLine();
			System.out.println("The next line is: " + fileLine);

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
					System.out.println("The album ID is " + fileLine.substring(fileLine.indexOf(":") + 1));
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
						Picture picture = new Picture (pictureImage, (Group) repo.getEntity(pictureAuthor));
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
			while(!fileLine.equals("?!(requests)!?")){
				subscribedTo.add((User) repo.getEntity(fileLine));
				System.out.println("The subscribed user is: " + fileLine);
				fileLine = br.readLine();
			}
			fileLine = br.readLine();

			while(!fileLine.equals("?!(dislikes)!?")){
				//requests
				fileLine= br.readLine();
				String sender = fileLine.substring(fileLine.indexOf(":") + 1);
				fileLine = br.readLine();
				String recieverGroup = fileLine.substring(fileLine.indexOf(":") + 1);
				fileLine = br.readLine();
				String text = fileLine.substring(fileLine.indexOf(":") + 1);
				fileLine= br.readLine();
				while(!fileLine.contains("Type:") || !fileLine.equals("?!(dislikes)!?")){
					text += fileLine;
					fileLine = br.readLine();
				}
				GroupRequest request = new GroupRequest((User) repo.getEntity(sender), (Group) repo.getEntity(recieverGroup), text);
				for (int i = 0; i < admins.size(); i++){
					User admin = (User) repo.getEntity(admins.get(i).getID());
					admin.addRequest(request);
				}
			}
			fileLine= br.readLine();
			dislikes = Integer.parseInt(fileLine.substring(fileLine.indexOf(":") + 1));


		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	void save()
	{
		try{
			FileWriter saveFile = new FileWriter("/home/rkinsman/tomcat/webapps/farceRepo/data/groups/" + ID + ".group");


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
				saveFile.write("ID:" + albums.get(i).getID() +"\n");
				
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
			saveFile.write("?!(requests)!?" + "\n");                                                         //Any requests the user has stored

			for(int i=0; i<requests.size(); i++){
				saveFile.write(requests.get(i).toString());
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

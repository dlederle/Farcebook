package farcebook;
import java.util.*;
import java.io.*;
//import javax.mail.*;
//import javax.mail.internet.*;
import java.util.Properties;
/** 
  An {@link farcebook.Entity} that represents a single person on Farcebook. User, from {@link farcebook.Entity}, inherits {@link farcebook.Album}, {@link farcebook.Request}, and {@link farcebook.Feed} objects. Users only hold one admin, who is the creator of the object.
  User objects hold both the email and password for the user, and all other information is contained in a hashtable called attributes. Both notifications and Request objects are held in ArrayLists. 
  @author Kevin Cherniawski
 */
public class User extends Entity {

	private String email;
	private String password;
	private ArrayList<String> notifications = new ArrayList<String>();
//	private ArrayList<Request> requests = new ArrayList<Request>();
	private Hashtable<String, String> attributes = new Hashtable<String, String>();
     private boolean verified = true;
	//"/home/kchernia/LOLcat/webapps/farce/data/users/" + ID + ".user");
	/**
	  Constructs a new, empty hashtable of attributes, new ArrayLists of notifications and Request objects,  and sets the email and password from the parameters.
	  @param email The email of the User object.
	  @param password The password of the User object.
	 */
	User(String email, String password) {
		this.email = email;
		this.password = password;
		//Date date2 = new Date();
		//post("This is a post!", this, date2);
		//Date date = new Date();
		//this.getPost(0).comment("This is a comment!", this, date);
/*		try{
			sendEmail(email, "Farcebook Account Verification", "Welcome to Farcebook. " 
				+ "To complete your registration, click on this link: http://rosemary.umw.edu:52058/farceRepo/controllers/validationController.jsp?ID="
 				+ getID() );
		}
		catch(Exception e){
			e.printStackTrace();
		}
          */
          profilePicture.setTitle("...Awwww Yeah");
         	Album allPictures = new Album("All Pictures", this);
         	allPictures.addPicture(profilePicture);
         	albums.add(allPictures);
	
		Album profilePics = new Album("Profile Pictures", this);
		profilePics.addPicture(profilePicture);
		albums.add(profilePics);
		save();
	}

	/**
	  Constructs a new, empty User object with the default email and password set as empty strings, and the collections within the object empty. 
	 */
	User () {
		email = "";
		password = "";
          verified = true;
	}
/*
	public synchronized void sendEmail(String address, String subject, String content) throws Exception{
		//System.out.println("YSGNVYRSGRBGNGSBGIJB!!!!!!");
		Properties p = new Properties();
		p.setProperty("mail.smtp.auth", "true");
		p.setProperty("mail.smtp.starttls.enable", "true");

		p.setProperty("mail.transport.protocol","smtp");
		p.setProperty("mail.host","smtp.gmail.com");   // for example
		//        p.setProperty("mail.smtp.host","paprika.umw.edu");   // for example
		p.setProperty("mail.user","farcebookgof");
		p.setProperty("mail.password","507Fauquier");
		//        p.setProperty("mail.from","sdavies@umw.edu");

		Session s = Session.getInstance(p, new GMailAuthenticator("farcebookgof", "507Fauquier"));
		MimeMessage m = new MimeMessage(s);
		m.setSubject(subject);
		m.setText(content);
		// NOTE: some servers demand that this is from their domain ("no
		// forwarding")
		m.setFrom(new InternetAddress("rkinsman@umw.edu"));
		InternetAddress a = new InternetAddress(address);
		Transport t = s.getTransport("smtp");   // or no arguments
		//System.out.println("Sending to " + address + "...");
		t.connect("smtp.gmail.com",587,"farcebookgof","507Fauquier");
		t.send(m, new Address[] { a });
	}
*/
	public void addRequest(Request req){
		super.addRequest(req);
	}


	public boolean isAdmin(User user){
		if (user == this){
			return true;
		}
		return false;
	}

	/**
	  Returns the value of the password of this User object.
	  @return The String password contained within the User Object. This may be empty String. 
	 */
	public String getPassword() {
		return password;
	}

	public Picture getProfilePicture(){
		return super.getProfilePicture();
	} 	

	/**
	  Returns the value of the ID of this User object.
	  @return The String ID contained within the User Object.  This may be empty String.
	 */
	public String getID() {
		return super.getID();
	}

	public synchronized void subscribeTo(User user) throws Exception {
		super.subscribeTo(user);
	}

	/**
	  Returns the value of the email of this User object.
	  @return The String email contained within the User object.  This may be empty String.
	 */	
	public String getEmail() {
		return email;
	}

     public void verify() {
          verified = true;
     }

     public boolean isVerified() {
          return verified;
     }

	/**
	  Creates an attribute called displayName and holds its value in the User object. The displayName attribute is created from the firstName, alias, and lastName attributes already held by the User object.
	 */	
	public synchronized void assembleDisplayName() {
		displayName = attributes.get("firstName") + " \"" + attributes.get("alias") + "\" " + attributes.get("lastName");
		save();
	}

	/**
	  Creates an attribute and holds its value in the User object. If the name of the attribute or it's value are null, this method will throw an exception.
	  @param name The name of the type of attribute being added to the attributes hashtable.
	  @param content The value of the attribute.
	 */
	public synchronized void setAttribute(String name, String content) {
		attributes.put(name, content);
		save();
	}

	public synchronized void addGroup(Group group){
		groups.add(group);
		save();
	}

	public ArrayList<Group> getGroups(){
		return groups;

	}

	public ArrayList<Event> getEvents(){
		return events;
	}

	/**
	  Searches the User object's attributes for the attribute with the given name. If the name of the attribute being passed is null, this method will thrown an exception. If no such attribute exists, this method will return null. 
	  @param name The name of the type of attribute being searched for.
	  @return The value of the attribute, or null of the seached attribute type doesn't exist. 
	 */
	public String getAttribute (String name) {
		return attributes.get(name);
	}

	public Wall getWall(){
		return super.getWall();
	}

	public String getInfo(){
		String info= "";
		info += ("Social Security Number : " + getAttribute("SSN") + "<br/>");
		info += ("Mother's Maiden Name : " + getAttribute("MMN") + "<br/>");
		info += ("Address : " + getAttribute("address") + "<br/>");
		return info;
	}

	/**
	  Creates a new {@link farcebook.Post} object and adds it to this User's {@link farcebook.Wall}. If either of the parameters being passed are null, an exception will be thrown.
	  @param text The text of the post.
	  @param author The User object that is posting on this User's wall.
	 */ 

	public ArrayList<Request> getRequests(){
		return requests;
	}

	public synchronized void requestSubscription(User user){
		FriendRequest fr = new FriendRequest(user, this);
		requests.add(fr);
		save();
	}

	/**
	  Creates a new {@link farcebook.Request} object and adds it to this User object's requests. This request is either be a {@link farcebook.EventInvite} or {@link farcebook.GroupInvite} object.
	  @param requestingUser The User object from the {@link farcebook.Group} or {@link farcebook.Event} that is sending the request.
	  @param requestingEntity The Group or Event object that this User object is being requested to join.
	  @param text The text of the Request Object.
	 */
	public synchronized void requestAttendance(User requestingUser, Group requestingEntity){

		GroupInvite gi = new GroupInvite(requestingUser, this, requestingEntity);
		requests.add(gi);
		save();	
	}

	public synchronized void requestAttendance(User requestingUser, Event requestingEntity){

		EventInvite ei = new EventInvite(requestingUser, this, requestingEntity);
		requests.add(ei);
		save();	
	}

	public synchronized void requestAttendance(User requestingUser, Entity requestingEntity){
		if(requestingEntity.getSubscriberType().equals("Attendee")){
			requestAttendance(requestingUser, (Event) requestingEntity);
		}
		else{
			requestAttendance(requestingUser, (Group) requestingEntity);
		}
	}


	public String getSubscriberType() {
		return "Friend";
	}

	public String getSubscriberAction(){
		return "Add Friend";
	}

	void load(String userFilename){
		try{
			Repository repo = Repository.instance();

			BufferedReader br = new BufferedReader(new FileReader("/home/rkinsman/tomcat/webapps/farceRepo/data/users/" + userFilename));

			String fileLine = br.readLine();
			System.out.println("email is " + fileLine.substring(fileLine.indexOf(":") + 1));
			email = fileLine.substring(fileLine.indexOf(":") + 1);
			fileLine=br.readLine();
			System.out.println("password is " + fileLine.substring(fileLine.indexOf(":") + 1));
			password = fileLine.substring(fileLine.indexOf(":") + 1);
			fileLine=br.readLine();
			System.out.println("fistName is " + fileLine.substring(fileLine.indexOf(":") + 1));
			attributes.put("firstName",fileLine.substring(fileLine.indexOf(":") + 1));
			displayName = fileLine.substring(fileLine.indexOf(":") + 1) + " \"";
			fileLine=br.readLine();
			System.out.println("alias is " + fileLine.substring(fileLine.indexOf(":") + 1));
			attributes.put("alias", fileLine.substring(fileLine.indexOf(":") + 1));
			displayName += fileLine.substring(fileLine.indexOf(":") + 1) + "\" ";
			fileLine=br.readLine();
			System.out.println("lastName is " + fileLine.substring(fileLine.indexOf(":") + 1));
			attributes.put("lastName", fileLine.substring(fileLine.indexOf(":") + 1));
			displayName += fileLine.substring(fileLine.indexOf(":") + 1);
			fileLine=br.readLine();
			System.out.println("secuirtyQuestion is " + fileLine.substring(fileLine.indexOf(":") + 1));
			attributes.put("securityQuestion",fileLine.substring(fileLine.indexOf(":") + 1));
			fileLine=br.readLine();
			System.out.println("securityAnswer is " + fileLine.substring(fileLine.indexOf(":") + 1));
			attributes.put("securityAnswer",fileLine.substring(fileLine.indexOf(":") + 1));
			fileLine=br.readLine();
			System.out.println("SSN is " + fileLine.substring(fileLine.indexOf(":") + 1));
			attributes.put("SSN", fileLine.substring(fileLine.indexOf(":") + 1));
			fileLine=br.readLine();
			System.out.println("MMN is " + fileLine.substring(fileLine.indexOf(":") + 1));
			attributes.put("MMN", fileLine.substring(fileLine.indexOf(":") + 1));
			fileLine=br.readLine();
			System.out.println("annualIncome is " + fileLine.substring(fileLine.indexOf(":") + 1));
			attributes.put("income", fileLine.substring(fileLine.indexOf(":") + 1));
			fileLine=br.readLine();
			System.out.println("address is " + fileLine.substring(fileLine.indexOf(":") + 1));
			attributes.put("address", fileLine.substring(fileLine.indexOf(":") +1));
			fileLine = br.readLine();
			System.out.println("ID is " + fileLine.substring(fileLine.indexOf(":") + 1));
			attributes.put("ID", fileLine.substring(fileLine.indexOf(":") + 1));
			fileLine=br.readLine();	
			System.out.println("The next line is: " + fileLine);		
			fileLine = br.readLine();
			System.out.println("The next line is: " + fileLine);		
			fileLine = br.readLine();
			System.out.println("The next line is: " + fileLine);		

			while(!fileLine.equals("010001110110000101101110011001110100111101100110010001100110111101110101011100100000110100001010")){  //get notifications
				//		notifications.add(fileLine);
				fileLine = br.readLine();
				System.out.println("The next line is: " + fileLine);		
			}

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
			//albums
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
						Picture picture = new Picture (pictureImage, (User) repo.getEntity(pictureAuthor));
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
			System.out.println("The next line is " + fileLine);
			fileLine= br.readLine();
			System.out.println("profilePicture is " + fileLine.substring(fileLine.indexOf(":")+1));
			profilePicture = albums.get(1).getPictures().get(0);
			System.out.println("The Source of the profile picture is... " + profilePicture.getSource());
			fileLine = br.readLine();
			System.out.println("the next line is " + fileLine);
			fileLine= br.readLine();
			System.out.println("the next line is " + fileLine);
			while(!fileLine.equals("?!(groups)!?")){ // get subscribees
				subscribedTo.add((User) repo.getEntity(fileLine));
				System.out.println("The user being added is : " + fileLine);
				fileLine = br.readLine();
			}
			fileLine = br.readLine();
			while (!fileLine.equals("?!(events)!?")){
				groups.add((Group) repo.getEntity(fileLine));
				System.out.println("The group being added is : " + fileLine);
				fileLine = br.readLine();
			}
			fileLine = br.readLine();
			while(!fileLine.equals("010001110110000101101110011001110100111101100110010001100110111101110101011100100000110100001010")){
				events.add((Event) repo.getEntity(fileLine));
				System.out.println("The event being added is : " + fileLine);
				fileLine = br.readLine();
			}

			fileLine= br.readLine();
			System.out.println("the next line is " + fileLine);
			fileLine = br.readLine();
			System.out.println("the next line is " + fileLine);
			while(!fileLine.equals("?!(dislikes)!?")){ // get requests
				if(fileLine.contains("FriendRequest")){
					fileLine = br.readLine();
					String sender = fileLine.substring(fileLine.indexOf(":") + 1);
					fileLine = br.readLine();
					String reciever = fileLine.substring(fileLine.indexOf(":") + 1);
					FriendRequest request = new FriendRequest((User) repo.getEntity(sender), (User) repo.getEntity(reciever));
					request.setText(repo.getEntity(sender).getDisplayName() + " wants to be your friend!");
					requests.add(request);	
					fileLine = br.readLine();
				}
				else if (fileLine.contains("GroupInvite")){
					fileLine= br.readLine();	
					String sender = fileLine.substring(fileLine.indexOf(":") + 1);
					fileLine = br.readLine();
					String reciever = fileLine.substring(fileLine.indexOf(":") + 1);
					fileLine = br.readLine();
					String group = fileLine.substring(fileLine.indexOf(":") + 1);
					fileLine = br.readLine();
					String text = fileLine.substring(fileLine.indexOf(":") + 1);
					fileLine = br.readLine();
					while (!fileLine.contains("Type:") || !fileLine.equals("?!(dislikes)!?")){
						text += fileLine;
						fileLine = br.readLine();
					}
					GroupInvite request = new GroupInvite((User) repo.getEntity(sender), (User) repo.getEntity(reciever), (Group) repo.getEntity(group));
					requests.add(request);
				}
				else if (fileLine.contains("EventInvite")){
					fileLine= br.readLine();
					String sender = fileLine.substring(fileLine.indexOf(":") +1);
					fileLine = br.readLine();
					String reciever = fileLine.substring(fileLine.indexOf(":") + 1);
					fileLine = br.readLine();
					String event = fileLine.substring(fileLine.indexOf(":") + 1);
					fileLine = br.readLine();
					String text = fileLine.substring(fileLine.indexOf(":") + 1);
					fileLine = br.readLine();
					while (!fileLine.contains("Type:") || !fileLine.equals("?!(dislikes)!?")){
						text += fileLine;
						fileLine = br.readLine();
					}
					EventInvite request = new EventInvite((User) repo.getEntity(sender), (User) repo.getEntity(reciever), (Event) repo.getEntity(event));
					requests.add(request);
				}
				else if (fileLine.contains("GroupRequest")){
					fileLine = br.readLine();
					System.out.println("In group request! and the next line is: " + fileLine);
					fileLine = br.readLine();
					System.out.println("In group request! and the next line is: " + fileLine);
					fileLine = br.readLine();
					System.out.println("In group request! and the next line is: " + fileLine);
					fileLine = br.readLine();
					System.out.println("In group request! and the next line is: " + fileLine);
				}
			}
			fileLine = br.readLine();
			dislikes = Integer.parseInt(fileLine.substring(fileLine.indexOf(":") + 1));

			System.out.println("END OF FILE!!!");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	void save()
	{
		System.out.println("SAVING THE FILE " + ID + ".user");
		try{
			FileWriter saveFile = new FileWriter("/home/rkinsman/tomcat/webapps/farceRepo/data/users/" + ID + ".user");
			/* These are the Attributes for each user. */
			saveFile.write("email:" + email + "\n");								
			saveFile.write("password:" + password + "\n");
			saveFile.write("firstName:" + getAttribute("firstName") + "\n");
			saveFile.write("alias:" + getAttribute("alias") + "\n");
			saveFile.write("lastName:" + getAttribute("lastName") + "\n");
			saveFile.write("securityQuestion:" + getAttribute("securityQuestion") + "\n"); //Not Required in register
			saveFile.write("securityAnswer:" + getAttribute("securityAnswer") + "\n"); //Not Required in register
			saveFile.write("SSN:" + getAttribute("SSN") + "\n");
			saveFile.write("MMN:" + getAttribute("MMN") + "\n");
			saveFile.write("annualIncome:" + getAttribute("income") + "\n");
			saveFile.write("address:" + getAttribute("address") + "\n");
			saveFile.write("ID:" + ID + "\n"); //Not in register
			saveFile.write("010001110110000101101110011001110100111101100110010001100110111101110101011100100000110100001010" + "\n");
			saveFile.write("?!(notificatons)!?" + "\n");								//User Notifications


			for(int i=0; i<notifications.size(); i++)
			{	//This doesnt seem to be thought out. we dont have methods for this suff...
				saveFile.write(notifications.get(i) + "\n");
			}


			saveFile.write("010001110110000101101110011001110100111101100110010001100110111101110101011100100000110100001010" + "\n");

			saveFile.write("?!(wall)!?" + "\n");									//User's Wall


			for(int i=0; i<wall.getPosts().size(); i++){
				saveFile.write("?!(post)!?" + "\n");									//User's posts
				saveFile.write("text:" + wall.getPosts().get(i).getText() +"\n");
				saveFile.write("author:" + wall.getPosts().get(i).getAuthor().getID() + "\n");
				saveFile.write("date:" + wall.getPosts().get(i).getDate().getTime() + "\n");

				for(int j=0; j<wall.getPosts().get(i).getComments().size(); j++){			//Comments per post
					saveFile.write("?!(comment)!?" + "\n");								
					saveFile.write("text:" + wall.getPosts().get(i).getComments().get(j).getText() +"\n");
					saveFile.write("author:" + wall.getPosts().get(i).getComments().get(j).getAuthor().getID() +"\n");
					saveFile.write("date:" + wall.getPosts().get(i).getComments().get(j).getDate().getTime() +"\n");
				}
			}


			saveFile.write("010001110110000101101110011001110100111101100110010001100110111101110101011100100000110100001010" +"\n");

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

			saveFile.write("?!(entity)!?" + "\n");								//Entity level Information

			saveFile.write("ProfilePic:" + profilePicture.getID() +"\n");
			//I left out display name and admin because we get that info above and we know the user is its own admin

			// Users that are subscribed to that user.
			saveFile.write("?!(subscribees)!?" +"\n");

			for(int i=0; i<subscribedTo.size(); i++){
				saveFile.write(subscribedTo.get(i).getID() +"\n");
			}
			saveFile.write("?!(groups)!?" + "\n");
			for (int i = 0; i<groups.size(); i++){
				saveFile.write(groups.get(i).getID() + "\n");

			}
			saveFile.write("?!(events)!?" + "\n");
			for (int i = 0; i<events.size(); i++){
				saveFile.write(events.get(i).getID() +"\n");

			}

			saveFile.write("010001110110000101101110011001110100111101100110010001100110111101110101011100100000110100001010" +"\n");

			saveFile.write("?!(requests)!?" + "\n");								//Any requests the user has stored

			for(int i=0; i<requests.size(); i++){
				saveFile.write( requests.get(i).toString());
				System.out.println("SAVING A REQUEST");
			}
			saveFile.write("?!(dislikes)!?\n");
			saveFile.write("dislikes:"+getDislikes());

			saveFile.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}

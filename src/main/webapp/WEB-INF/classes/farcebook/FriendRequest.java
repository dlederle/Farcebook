package farcebook;
import java.util.*;
import java.io.*;

/** 
  A FriendRequest listens for the receiving {@link farcebook.User} object response to another User object's request for friendship. After {@link farcebook.User#requestFriendship} is called, a {@link farcebook.Request} instance is instantiated added the User's ArrayList of Requests. 
  @author Kevin Cherniawski
 */
public class FriendRequest implements Request{

	private User sender;
	private User receiver;
	private String text;
	private String type;

	public FriendRequest(User requestingUser, User targetUser){
		this.sender = requestingUser;
		this.receiver = targetUser;
		text = ("<a href = profile.jsp?ID=" + sender.getID() + ">" + sender.getDisplayName() + "</a>" + " wants to be your friend!");
		type = "FriendRequest";
	}

	public String getText(){
		return text;
	}
	
	public void setText(String text){
		this.text = text;
	}

	public String getType(){
		return type;
	}
	/**
	  Invoked when the {@link farcebook.User} responds to a specific request. Given Farcebook design, the User has no choice but to accept the request, therefore the sender and receiver both add each other as subscribees and the Request is deleted in any ArrayLists holding requests it previously existed in.

	 */
	public void accept(){
		update();
	}

	private void update() {

		try {
			receiver.subscribeTo(sender);
			Date date = new Date();
			receiver.post("<a href = profile.jsp?ID=" + sender.getID() + ">" + sender.getDisplayName() + "</a>"  + 
					" is now friends with " + "<a href = profile.jsp?ID=" + receiver.getID() + ">" + receiver.getDisplayName() + "</a>", receiver, date);

			sender.subscribeTo(receiver);
			sender.post("<a href = profile.jsp?ID=" + sender.getID() + ">" + receiver.getDisplayName() + "</a>" + 
					" is now friends with " + "<a href = profile.jsp?ID=" + sender.getID() + ">" + sender.getDisplayName() +"</a>", sender, date);
		}
		catch(Exception e) {     //Fix to later return some sort of bs
			e.printStackTrace();
		}

	}
	/**
	  Converts the contents of the FriendRequest to a {@link java.lang.String} in order to save it to file. The toString method will save the FriendRequest as follows:
		Sender: senderID
		Receiver: receiverID
		Text: text
	  @return The string representation of the FriendRequest.
	 */
	public String toString(){
		return ("Type:" + type + "\nSender:" + sender.getID() + "\nReceiver:" + receiver.getID() + "\n");     //Just for compile
	}

}


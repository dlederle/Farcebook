package farcebook;
import java.util.*;
import java.io.*;


/** 
  A GroupInvite listens for the receiving {@link farcebook.User} object's response to an admin's invitation to a {@link farcebook.Group}.  After {@link farcebook.Group#requestMembership} is called, a {@link farcebook.Request} instance is instantiated and added the User's ArrayList of requests. 
  @author Dylan Lederle
 */
public class GroupInvite implements Request{

	private User sender;
	private User receiver;
	private Group group;
	private String text;
	private String type;

	GroupInvite(User sender, User receiver, Group group)
	{
		this.sender = sender;
		this.receiver = receiver;
		this.group = group;
		this.text = "<a href = profile.jsp?ID=" + sender.getID() + ">" +  sender.getDisplayName() + "</a>" + " has invited you to join "+ "<a href profile.jsp?ID=" + group.getID() + ">" + group.getDisplayName() + "</a>";
		type = "GroupInvite";
	}
	/**
	  Invoked when the requested User responds to a specific request. Given Farcebook design, the User has no choice but to accept the request, therefore the group and User both add each other as subscribees and the Request is deleted in any ArrayLists holding requests it previously existed in.
	 */
	public void accept(){
		update();
	}

	private void update(){
		try {
                        group.subscribeTo(receiver);
			receiver.addGroup(group);	
                        Date date = new Date();
                        receiver.post("<a href = profile.jsp?ID=" + receiver.getID() + ">" + receiver.getDisplayName() + "</a>" +
                                        " joined " + "<a href = profile.jsp?ID=" + group.getID() + ">" + group.getDisplayName() + "</a>", receiver, date);
                }
                catch(Exception e) {     //Fix to later return some sort of bs
                        e.printStackTrace();
                }
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
	  Converts the contents of the GroupInvite to a {@link java.lang.String} in order to save it to file. The toString method will save the invite as follows:
Sender: senderID
Receiver: receiverID
Text: text
ID : groupID
	 */ 
	public String toString(){
		return ("Type:" + type + "\nSender:" + sender.getID() + "\nReceiver:" + receiver.getID() + "\nGroup:" + group.getID() + "\nText:" + text + "\n");          //Just for compile
	}

}


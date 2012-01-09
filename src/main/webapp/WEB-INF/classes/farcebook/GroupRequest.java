package farcebook;
import java.util.*;

/**
  A GroupRequest listens for the receiving {@link farcebook.Group} admin's response to a {@link farcebook.User} object's membership request.  After {@link farcebook.User#subscribeTo} is called, a {@link farcebook.Request} instance is instantiated and added the all Group admins' ArrayList of Requests. 
  @author Kevin Cherniawski 
 */
public class GroupRequest implements Request{

	private User sender;
	private Group receiverGroup;
	private String text;
	private String type;

	GroupRequest(User sender, Group receiverGroup, String text)
	{
		this.sender = sender;
		this.receiverGroup = receiverGroup;
		this.text = text;
		type = "GroupRequest";
	}

	/**
	  Invoked when one admin of a Group responds to a specific request. Given Farcebook design, the admin has no choice but to accept the request, therefore the sender and group both add each other as subscribees and the Request is deleted in any ArrayLists holding requests it previously existed in.
	 */
	public void accept()
	{
		update();
	}

	private void update() 
	{

		try 
		{
			Date date = new Date();
			receiverGroup.subscribeTo(sender);
			sender.addGroup(receiverGroup);
			receiverGroup.post("<a href= profile.jsp?ID= " + sender.getID() + ">" + sender.getDisplayName() + "</a>" + " joined " + "<a href = profile.jsp?ID=" + receiverGroup.getID() + ">" + receiverGroup.getDisplayName() + "</a>", sender, date);
			sender.post("<a href= profile.jsp?ID= " + sender.getID() + ">" + sender.getDisplayName() + "</a>" + " joined " + "<a href = profile.jsp?ID=" + receiverGroup.getID() + ">" + receiverGroup.getDisplayName() + "</a>", sender, date);
		}

		catch(Exception e) {     //Fix to later return something
			e.printStackTrace();
		}

	}

	/**
	  Converts the contents of the GroupRequest to a {@link java.lang.String} in order to save it to file. The toString method will save the Request as follows:
Sender: senderID
Receiver: receiverID
Text: text
@return A string representation of the GroupRequest.
	 */
	public String toString()
	{
		return ("Type:" + type + "\nSender:" + sender.getID() + "\nReceiver:" + receiverGroup.getID() + "\nText:" + text + "\n");
	}

	public String getText(){
		return text;
	}

	public String getType(){
		return type;
	}

}


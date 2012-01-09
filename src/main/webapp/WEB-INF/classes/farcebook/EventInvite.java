package farcebook;
import java.util.*;

/** 
  An EventInvite listens for the receiving {@link farcebook.User} object's response to an admin's invitation to an {@link farcebook.Event}. After {@link farcebook.Event#RSVP} is called, a {@link farcebook.Request} instance is instantiated and added the User's ArrayList of Requests. 
  @author Michael Crawford
 */
public class EventInvite implements Request{

	private User sender;
	private User receiver;
	private Event event;
	private String text;
	private String type;

	EventInvite(User sender, User receiver, Event event)
	{
		this.sender = sender;
		this.receiver = receiver;
		this.event = event;
		this.text = "<a href = profile?ID=" + sender.getID() + ">" + sender.getDisplayName() + "</a>" +" has invited you to attend "+ "<a href = profile.jsp?ID=" + event.getID() + ">" + event.getDisplayName() + "</a>";
		type = "EventInvite";
	}

	public String getText(){
		return text;
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
	private void update(){
		try {
			event.subscribeTo(receiver);
			receiver.addEvent(event);
			Date date = new Date();
			receiver.post("<a href = profile.jsp?ID=" + sender.getID() + ">" +  sender.getDisplayName() + "</a>" +
					" is attending " + "<a href = profile.jsp?ID=" + receiver.getID() + ">" + receiver.getDisplayName() + "</a>", receiver, date);
		}
		catch(Exception e) {     //Fix to later return some sort of bs
			e.printStackTrace();
		}
	}
	/**
	  Converts the contents of the EventInvite to a {@link java.lang.String} in order to save it to file. The toString method will save the EventInvite as follows:
Sender: senderID
Receiver: receiverID
Text: text
ID: eventID
@return A String representation of the EventInvite object.
	 */	
	public String toString(){
		return ("Type:" + type + "\nSender:" + sender.getID() + "\nReceiver:" + receiver.getID() + "\nEvent" + event.getID() + "\nText:" + text + "\n");          //nust to compile
	}

}

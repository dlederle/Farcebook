package farcebook;
import java.util.*;
import java.io.*;

/** 
Defines a Request object that listens for a {@link farcebook.User} Object's response. The specific type of response will be determined in the subclass. When the Request is accepted, the sender is notified and the request is deleted from the User's ArrayList of Requests.

@author Ross Kinsman
*/
public interface Request{

	/** 
	Invoked when the {@link farcebook.User} responds to a specific request. Given Farcebook design, the User has no choice but to accept the request, therefore the sender and reciever both add each other as subscribees and the Request is deleted in any ArrayLists holding requests it previously existed in.  
	*/
	public void accept();

    	/** 
	Converts the contents of the Request to a {@link java.lang.String} in order to save it to file when the Entity calls its private save() method. Further description of the toString() method is contained within the subclasses.
	@return A String representation of the request.
	*/
	public String toString();

//	private void update();

	public String getText();

	public String getType();
}


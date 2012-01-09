package farcebook;
import java.util.*;
import java.io.*;

/**
  Represents a collection of {@link farcebook.Post} objects by subscribees of an entity.  Feed could be used to show a list of updates from {@link farcebook.User}'s friends, groups.  {@link farcebook.Wall} inherits from Feed, and represents what other Users see when they view that User's profile. It will have all post that pertain to that User, such as friending, wall posts, and status updates.
  @author Michael Crawford
 */
public class Feed{

	private ArrayList<Post> newsFeed;	
	private Entity owner;

	Feed (Entity owner) 
	{
		this.owner = owner;
		newsFeed = new ArrayList<Post>();
		for (int i = 0; i<owner.getSubscribedTo().size(); i++){
			newsFeed.add(getLatestPost(owner.getSubscribedTo().get(i)));
		}
		newsFeed.add(getLatestPost(owner));
		Collections.sort(newsFeed);				
	}	

	Feed() 
	{  //Ask stephen whats up

	}

	/**
	  Creates a new {@link farcebook.Post} object, this is called when a {@link farcebook.User} object posts to the Feed. The User will be both the sender and recipient of the post, however it will be displayed on the Feed when {@link farcebook.Feed#getPosts()} is c
	  alled.
	  @param text The text of the post object.
	 */
	// I am deleting the second parameter, "Entity author," because it doesn't make any sense.  Dammit. KEVIN.
	/*	public void post(String text, Entity owner){
		Post newPost = new Post(text, owner);

		}
	 */
	/**
	  Returns all of the {@link farcebook.Post}s that have been made by the {@link farcebook.User} object's subcribers in an ArrayList. It is in reverse chronological order, meaning that the newest Post object by date will be the first object in the ArrayList.
	  @return An ArrayList of Posts gathered from the User Object's subscribers. Returns an empty ArrayList if there are no posts. 
	 */
	public ArrayList<Post> getPosts(){
		return newsFeed;
	}

	/**
	  Returns all of the {@link farcebook.Post}s that have been made by the {@link farcebook.User} object's subcribers in an ArrayList. 
	  @return An ArrayList of Posts gathered from the User Object's subscribers. 
	 */
	public Post getLatestPost(Entity user){        //This method is currently borked.
		if(user.getWall().getPosts().size() != 0){
			return user.getWall().getPosts().get(0);
		}
		else{
			return null;
		}
	}
}

package farcebook;

import java.util.*;

/**
     Represents a {@link farcebook.User}'s Wall, where other users may leave them messages. The messages will be held as {@link farcebook.Post}s. Wall inherits from Feed, and can be used to show other Users interactions with that User.
     @author Dylan Lederle
*/
public class Wall extends Feed {
	
	private ArrayList<Post> posts;
	private Entity owner;
	
    	Wall(Entity owner) {
		this.owner = owner;
		posts = new ArrayList<Post>();
    	}
     
     /**
          Returns all {@link farcebook.Post}s that have been posted to this wall. Returns an empty ArrayList<Post> if the user has no Post's.
          @return ArrayList<Post> that belong to this Wall.
     */
    	public ArrayList<Post> getPosts() {
	
    	     return posts;
    	}

	public void post(String text, User author, Date date){
		Post post = new Post(text, author, date);
		posts.add(post);
	}
	/**
	Returns the Post object at the given index. If the given index is larger than the amount of Posts contained in the Wall, an exception will be thrown.
	@param index The index of the Post.
	@return The Post object at the given index. 
	*/	
	public Post getPost(int index){
		return posts.get(index);
	}
}

package farcebook;
import java.util.*;
import java.io.*;

/** A Post is a public message from a {@link farcebook.User} that is added to an {@link farcebook.Entity}'s wall. It consists of a {@link java.lang.String} which stores the message, the User that authored the post, and the {@link java.util.Date} which it was created on. Posts are created by Users, when they call {@link farcebook.User#post}.
  @author Michael Crawford
 */
public class Post implements Comparable<Post>{

     private String text;
     private Entity author;
     private Date date;
     private ArrayList<Post> comments = new ArrayList<Post>();


	Post(String text, Entity author, Date date){
		this.text = text;
		this.author = author;
		this.date = date;
	}

     /** 
       Returns the text associated with the Post object in a {@link java.lang.String}.
       @return The text of the Post object.
      */
     public String getText(){
          return text;
     }

     /** 
       Returns the {@link java.util.Date} associated with the Post object.
       @return The Date object of the Post.
      */
     public Date getDate(){
          return date;
     }

	public void setDate(Date date){
		this.date = date;
	}

	public void comment(String text, User author, Date date){
		Post comment = new Post(text, author, date);
		comments.add(comment);
	}

     /**
       Returns the {@link farcebook.User} who authored with the Post object.
       @return The User that created this post object.
      */
     public Entity getAuthor(){
          return author;
     }

     /**
       Compares this Post to the given Post object by {@link java.util.Date} created.
       @param other The Post that is being compared to.
       @return -1 if this Post was created before the Post being passed to it. 1 if this Post was created after the Post being passed to it.
      */
     public int compareTo(Post other){
          if (getDate().compareTo(other.getDate()) == 0)
               return 0;
          else if (getDate().compareTo(other.getDate()) < 0)
               return 1;
          else
               return -1;
     }
     /**
       Returns an ArrayList of Post objects on this Post. Post objects at are contained in other Post objects are referred to as comments. If there are no comments on this Post, this method will return an empty ArrayList.
       @return An ArrayList of Posts, or an empty ArrayList if no comments exist.
      */
     public ArrayList<Post> getComments() {
          return comments;
     }
}

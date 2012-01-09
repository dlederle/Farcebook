package farcebook;
import java.util.*;
import java.io.*;

/**
  A representation of a particular image, with the image file source, {@link java.util.Date} created, a title and the {@link farcebook.User} who uploaded the picture. Pictures also have a randomly generated 10 sequence of upper case and lower case letters and numbers {@link java.lang.String} that is their unique identifier. The Entity class creates picture objects with its {@link farcebook.Entity#addPicture} method.
  test comment.  @author Dylan Lederle
 */
public class Picture {

     private String imagesrc;
     private String title;
     private Date date;
     private Entity owner;
     private String ID;
     private ArrayList<Post> picturePosts;

     /**
       Constructs a new Picture object, taking the path to the image source and the {@link farcebook.Entity} who created it.
       @param source {@link java.lang.String} containing the file path to the image's location on the server.
       @param owner The {@link farcebook.Entity} who created the picture.
      */
     Picture (String source, Entity owner) 
     {
          this.imagesrc = source;
          this.owner = owner;
          picturePosts = new ArrayList<Post>();
          date = new Date();

          String AB = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
          Random rnd = new Random();
          String sb;
          boolean original = false;
          while ( !original )
          {
               sb = "";
               for( int i = 0; i < 10; i++ )
               {
                    sb += AB.charAt( rnd.nextInt(AB.length()) );
               }
               ID = sb.toString();
               Repository rp = Repository.instance();
               if (rp.getEntity( ID ) != null)
               {
                    original = false;
               }
               else
               {
                    original = true;
               }
          }

     }

     Picture ()
     {
          imagesrc = "";
          picturePosts = new ArrayList<Post>();
     }

     /**
       Sets the title to be displayed for the Picture. By default, the title for picture will be empty String.  Also, titles do not need to be unique.
      */
     public void setTitle(String title) 
     {
          this.title = title;
     }

     public void setImagesrc(String imagesrc)
     {
          this.imagesrc = imagesrc;
     }

     public void setID(String ID)
     {
          this.ID = ID;
     }

     public void setOwner(Entity owner)
     {
          this.owner = owner;
     }

     public void addPost(Post post)
     {
          picturePosts.add(post);
     }

     /**
       Returns the file path to the image's location on the server as a {@link java.lang.String}.
       @return A String filepath representing the image's location.  
      */
     public String getSource() 
     {
          return imagesrc;
     }

     /**
       Returns the title of the Picture as a {@link java.lang.String}.
       @return The image's String title.
      */
     public String getTitle() 
     {
          return title;
     }

     /**
       Returns the {@link java.util.Date} the picture was created.
       @return The Date object that represents when the picture object was created.
      */
     public Date getDate() 
     {
          return date;
     }

     public void setDate(long epoch)
     {
          date = new Date(epoch);
     }

     /**
       Returns the {@link farcebook.User} who created the Picture.
       @return The User object that the picture belongs to.
      */
     public Entity getOwner() 
     {
          return owner;
     }

     /**
       Compares this Picture to the given Picture by {@link java.util.Date} created.
       @param other The Picture that is being compared to.
       @return -1 if this Picture was created before the Picture being passed to it.
       1 if this Picture was created after the Picture being passed to it.
      */
     public int compareTo(Picture other) 
     {
          if (getDate().compareTo(other.getDate()) == 0)
               return 0;
          else if (getDate().compareTo(other.getDate()) < 0)
               return 1;
          else
               return -1;
     }

     /**
       Returns the ID of the Picture object.
       @return The ID of the Picture, which is a String object.
      */
     public String getID()
     {
          return ID;
     }          

     /**
       Returns an ArrayList of {@link farcebook.Post} objects made to this Picture.  If there are no Posts on this Picture object, an empty ArrayList will be returned.
       @return An ArrayList of Posts, or an empty ArrayList if there are none.
      */
     public ArrayList<Post> getPicturePosts()
     {
          return picturePosts;
     }

}

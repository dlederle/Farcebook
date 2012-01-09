package farcebook;
import java.util.*;
import java.io.*;

/**
  The Album class represents a collection of {@link farcebook.Picture} objects. An {@link farcebook.Entity} can hold on to multiple Album objects, however they are ordered by their date. Each Album holds on to a title and Date object. 

  @author Ross Kinsman
 */
public class Album implements Comparable<Album>
{
     private String title;
     private Date date;
     private ArrayList<Picture> pictures;
     private Entity author;
     private String ID;

     Album(String title, Entity author)
     {
          this.title = title;
          date = new Date();
          pictures = new ArrayList<Picture>();
          this.author = author;

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

     Album()
     {
          title = "";
          pictures = new ArrayList<Picture>();
     }

     /**
       Adds a {@link farcebook.Picture} object to this Album object and refreshes the Date of the Album to the current time. 
       The Picture object will be sorted in the pictures ArrayList by date when it is added. 
       @param picture The Picture object being added to the Album Object's ArrayList of Pictures.
      */
     public void addPicture(Picture picture)
     {
          pictures.add(picture);
          Collections.reverse(pictures);
     }

     public String getID()
     {
          return ID;
     }

     public void setID(String ID)
     {
          this.ID = ID;
     }

     public void setAuthor(User author)
     {
          this.author = author;
     }

     public void setTitle(String title)
     {
          this.title = title;
     }

     /**
       Returns the title of the Album object.
       @return The title of the Album, which is a String.
      */
     public String getTitle()
     {
          return title;
     }

     /**
       Returns the date of the Album object.
       @return The date of the Album, which is a Date.
      */
     public Date getDate()
     {
          return date;
     }

     public void setDate(long epoch) {
          date = new Date(epoch);

     }

     /**
       Returns the author of the Album object.
       @return The author of the Album, which is a User object.
      */
     public Entity getAuthor()
     {
          return author;
     }

     /**
       Returns an ArrayList of Picture objects that are contained within this Album.  If the Album contains no Picture objects, an empty ArrayList is returned.
       @return An ArrayList of Picture objects, or an empty ArrayList if the album is empty.
      */
     public ArrayList<Picture> getPictures()
     {
          return pictures;
     }

     public Picture getPicture(String ID)
     {
          for (int i = 0; i<pictures.size(); i++)
          {
               if (pictures.get(i).getID().equals(ID))
               {
                    return pictures.get(i);
               }
          }
          return null;
     }

     public int getNumPics() {
          return pictures.size();
     }

     /**
       Compares this Album's {@link java.util.Date} object to the Date object of the another Album object. 
       @param otherAlbum The Album object that is being compared to this Album object.
       @return A negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
      */
     public int compareTo(Album otherAlbum)
     {
          if (getDate().compareTo(otherAlbum.getDate()) == 0)
               return 0;
          else if (getDate().compareTo(otherAlbum.getDate()) < 0)
               return 1;
          else
               return -1;
     }
}


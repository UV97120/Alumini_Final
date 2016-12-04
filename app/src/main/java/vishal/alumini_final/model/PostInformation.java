package vishal.alumini_final.model;

import android.graphics.Bitmap;

/**
 * Created by razintailor on 03/08/16.
 */

public class PostInformation {
    String Title;
    String Content;
    String TimeStamp;
    String UserName;
    Bitmap image;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {

        return Title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setTitle(String title) {

        Title = title;
    }

}

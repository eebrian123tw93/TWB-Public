package twb.conwaybrian.com.twbandroid.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import lombok.Data;
import twb.conwaybrian.com.twbandroid.reactbutton.Reaction;

@Data
public class Like {

    private String articleId;
    
    private String userId;

    private Type type;

    private Date createTimes;

    public enum Type{
        @SerializedName("1")
        LIKE(1),
        @SerializedName("-1")
        DISLIKE(-1);
        private int value;
         Type(int value) {
            this.value=value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    public void setLikeType(Reaction.Type type){
        switch (type){
            case LIKE_COLOR:
                setType(Like.Type.LIKE);
                break;
            case DISLIKE_COLOR:
                setType(Like.Type.DISLIKE);
                break;
        }
    }
}

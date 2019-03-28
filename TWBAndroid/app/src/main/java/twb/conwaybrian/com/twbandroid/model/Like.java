package twb.conwaybrian.com.twbandroid.model;

import java.util.Date;

public class Like {

    private String articleId;

    private  Long vKey;

    private String userId;

    private Type type;

    private Date createTimes;

    public enum Type{
        LIKE(1),DISLIKE(-1);
        private int value;
         Type(int value) {
            this.value=value;
        }

        public int getValue() {
            return value;
        }
    }
}

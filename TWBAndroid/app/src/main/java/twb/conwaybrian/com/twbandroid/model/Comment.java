package twb.conwaybrian.com.twbandroid.model;

import java.util.Date;

import lombok.Data;

@Data
public class Comment {
    private String articleId;

    private  Long vKey;

    private String userId;

    private String comment;

    private Date  commentTime;
}

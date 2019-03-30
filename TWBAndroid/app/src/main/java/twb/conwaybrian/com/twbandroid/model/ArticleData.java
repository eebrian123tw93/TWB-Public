package twb.conwaybrian.com.twbandroid.model;

import java.util.List;

import lombok.Data;

@Data
public class ArticleData {
    private int commentCount;
    private  int points;
    private   int views;
    private   int likeStatus;
    private    List<Comment>comments;
}

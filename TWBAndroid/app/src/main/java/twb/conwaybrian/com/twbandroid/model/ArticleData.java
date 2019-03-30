package twb.conwaybrian.com.twbandroid.model;

import java.util.List;

import lombok.Data;

@Data
public class ArticleData {
    int commentCount;
    int points;
    int views;
    List<Comment>comments;
}

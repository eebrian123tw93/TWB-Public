package twb.conwaybrian.com.twbandroid.model;



import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class Article {
    private String articleId;
    private String content;
    private List<String>images;
    private String userId;
    private String title;
    private int points;
    private int views;
    private Date createTime;
    private int vkey;
}

package twb.conwaybrian.com.twbandroid.presenter;

import twb.conwaybrian.com.twbandroid.view.ArticleView;

public class ArticlePresenter extends TWBPresenter {
    private ArticleView articleView;
    public ArticlePresenter(ArticleView articleView){
        this.articleView=articleView;
    }
}

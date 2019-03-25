package twb.conwaybrian.com.twbandroid.reactbutton;

import twb.conwaybrian.com.twbandroid.R;

public  class TWBReactions {

    public static Reaction defaultReact = new Reaction(
            ReactConstants.LIKE,
            ReactConstants.DEFAULT,
            ReactConstants.GRAY,
            R.drawable.like_color);

    public static Reaction[] reactions = {
            new Reaction(ReactConstants.LIKE,ReactConstants.BLUE,R.drawable.like_color),
            new Reaction(ReactConstants.NO_LIKE,ReactConstants.RED_LOVE,R.drawable.no_like_color),
            new Reaction(ReactConstants.DISLIKE,ReactConstants.YELLOW_WOW,R.drawable.dislike_color),
    };


    static Reaction getDefaultReact() {
        return defaultReact;
    }

    static Reaction[] getReactions() {
        return reactions;
    }
}

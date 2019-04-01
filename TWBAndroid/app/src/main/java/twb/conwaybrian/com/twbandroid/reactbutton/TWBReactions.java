package twb.conwaybrian.com.twbandroid.reactbutton;

import twb.conwaybrian.com.twbandroid.R;

public class TWBReactions {

    public static Reaction defaultReact = new Reaction(
            Reaction.Type.LIKE,
            R.drawable.like);

    public static Reaction[] reactions = {
            new Reaction(Reaction.Type.LIKE_COLOR, R.drawable.like_color),
            new Reaction(Reaction.Type.DISLIKE_COLOR, R.drawable.dislike_color)
    };


    static Reaction getDefaultReact() {
        return defaultReact;
    }

    static Reaction[] getReactions() {
        return reactions;
    }
}

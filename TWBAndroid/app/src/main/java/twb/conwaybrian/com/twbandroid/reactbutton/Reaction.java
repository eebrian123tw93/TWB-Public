package twb.conwaybrian.com.twbandroid.reactbutton;


import lombok.Data;

/**
 * Model Class to save one React Attribute
 */
@Data
public class Reaction {
    private Type type;
    /**
     * ReactButton Icon id value for this Reaction
     */
    private int reactIconId;

    public Reaction(Type type, int reactIconId) {
        this.type = type;
        this.reactIconId = reactIconId;
    }

    /**
     * @param object : Reaction object
     * @return : true if new Reaction type equal current Reaction
     */
    @Override
    public boolean equals(Object object) {
        //Assert that obj type is Reaction
        if (object instanceof Reaction) {
            //Cast Object to Reaction
            Reaction react = (Reaction) object;
            //if react type equal current Reaction type
            return react.getType().equals(type);
        }
        return false;
    }

    public enum Type {
        DISLIKE, LIKE, NO_LIKE, DISLIKE_COLOR, LIKE_COLOR
    }
}


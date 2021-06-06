import jade.core.AID;
import java.io.Serializable;

public class Type implements Serializable {
    AID name;
    String type;
    boolean vote;
    public Type(AID name,String type,boolean vote)
    {
        this.name = name;
        this.type = type;
        this.vote = vote;
    }
    public Type(AID name,String type)
    {
        this.name = name;
        this.type = type;
        this.vote = false;
    }
    public Type(Type obj,boolean vote)
    {
        this.name = obj.name;
        this.type = obj.type;
        this.vote = vote;
    }
}

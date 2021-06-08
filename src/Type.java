import jade.core.AID;
import java.io.Serializable;

public class Type implements Serializable {
    AID name;
    String type;
    int vote;
    public Type(AID name,String type,int vote)
    {
        this.name = name;
        this.type = type;
        this.vote = vote;
    }
    public Type(AID name,String type)
    {
        this.name = name;
        this.type = type;
        this.vote = 0;
    }
    public Type(Type obj,int vote)
    {
        this.name = obj.name;
        this.type = obj.type;
        this.vote = vote;
    }
}

import jade.core.AID;
import java.io.Serializable;
import java.util.ArrayList;

public class Nice implements Serializable {
    AID name;
    ArrayList<Type> record;
    public Nice(AID name,ArrayList<Type> record)
    {
        this.name = name;
        this.record = record;
    }
}
import java.io.Serializable;
import jade.core.*;

public class Vote implements Serializable {
    AID id;
    boolean value;
    String type;
    public Vote(AID id, String type, boolean value) {
        super();
        this.id = id;
        this.type = type;
        this.value = value;
    }
}
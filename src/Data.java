import java.io.Serializable;
import jade.core.*;

import java.util.*;

public class Data implements Serializable {
    ArrayList<Type> data;
    AID id;
    public Data(AID id) {
        super();
        this.id = id;
        this.data = new ArrayList<Type>();
    }
    public void append(Type v)
    {
        data.add(v);
    }
}
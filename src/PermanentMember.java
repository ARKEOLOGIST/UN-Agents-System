import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.*;
import jade.domain.JADEAgentManagement.*;
import jade.domain.introspection.AMSSubscriber;
import jade.domain.introspection.BornAgent;
import jade.domain.mobility.*;
import jade.domain.introspection.*;

import jade.lang.acl.ACLMessage;

import jade.domain.*;

import jade.core.behaviours.*;
import jade.core.*;

import jade.content.onto.basic.*;

import jade.content.*;
import jade.content.abs.*;
import jade.content.onto.*;
import jade.content.lang.*;
import jade.content.lang.leap.LEAPCodec;
import jade.content.lang.sl.*;

import jade.util.leap.Iterator;
import jade.util.leap.List;
import jade.util.leap.Map;

import java.beans.EventHandler;
import java.util.*;

public class PermanentMember extends Agent {

    private ContentManager manager  = (ContentManager) getContentManager();
    private Codec codec = new SLCodec();
    private Ontology ontology = JADEManagementOntology.getInstance();

    @Override
    protected void setup() {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName( getAID() ); 
        ServiceDescription sd  = new ServiceDescription();
        sd.setType("Permanent");
        sd.setName( getLocalName() );
        dfd.addServices(sd);
        
        try {  
            DFService.register(this, dfd);  
        }
        catch (Exception fe) { fe.printStackTrace(); }
        Agent id = this;
        getContentManager().registerLanguage(codec);
        getContentManager().registerOntology(ontology);
        getContentManager().registerOntology(MobilityOntology.getInstance());
        Behaviour receiveMessage = new CyclicBehaviour(this) {
            public void action() 
            {
                MessageTemplate mt = MessageTemplate.MatchConversationId("C");
                ACLMessage ip = receive(mt);
                if (ip != null)
                {
                    try {
                        ArrayList<Type> data = (ArrayList<Type>) ip.getContentObject();
                        ArrayList<Type> votes = new ArrayList<Type>();
                        for (Type i : data)
                    {
                        System.out.println(i.name.toString() + " - " + i.type);
                        double chance = Math.random();
                        if (chance <= 0.5)
                        {
                            votes.add(new Type(i,true));
                        }
                        else
                        {
                            votes.add(new Type(i,false));
                        } 
                    }
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    msg.setContentObject(votes);
                    msg.addReceiver(ip.getSender());
                    msg.setSender(getAID());
                    msg.setConversationId("A");
                    send(msg);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }             
                }
                else
                {
                    block();
                }
               
            }
        };
        addBehaviour(receiveMessage);
    }
}
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.tools.sniffer.Message;

import javax.swing.JOptionPane;

import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.*;
import jade.domain.JADEAgentManagement.*;
import jade.domain.introspection.AMSSubscriber;
import jade.domain.introspection.BornAgent;
import jade.domain.mobility.*;
import jade.domain.introspection.*;

import jade.core.behaviours.*;
import jade.core.*;

import jade.content.*;
import jade.content.abs.*;
import jade.content.onto.*;
import jade.content.lang.*;
import jade.content.lang.leap.LEAPCodec;
import jade.content.lang.sl.*;

import java.util.*;

public class MasterMember extends Agent {

    private ContentManager manager  = (ContentManager) getContentManager();
    private Codec codec = new SLCodec();
    private Ontology ontology = JADEManagementOntology.getInstance();

    @Override
    protected void setup() {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName( getAID() ); 
        ServiceDescription sd  = new ServiceDescription();
        sd.setType("Master");
        sd.setName(getLocalName());
        dfd.addServices(sd);
        Agent id = this;
        try {  
            DFService.register(this, dfd);  
        }
        catch (Exception fe) { fe.printStackTrace(); }
        getContentManager().registerLanguage(codec);
        getContentManager().registerOntology(ontology);
        getContentManager().registerOntology(JADEManagementOntology.getInstance());
        getContentManager().registerOntology(MobilityOntology.getInstance());
        getContentManager().registerOntology(IntrospectionOntology.getInstance());    
        ArrayList<AID> temporary_members = new ArrayList<AID>();
        ArrayList<AID> permanent_members = new ArrayList<AID>();
        ArrayList<AID> regular_members = new ArrayList<AID>();
        ArrayList<Nice> message_records = new ArrayList<Nice>();
        AMSSubscriber myAMSSubscriber = new AMSSubscriber() {
            protected void installHandlers(Map handlers) {
            EventHandler creationsHandler = new EventHandler() {
            public void handle(Event ev) {
            BornAgent ba = (BornAgent) ev;
            if (ba.getWhere().getName().equals("Permanent Members"))
            {
                permanent_members.add(ba.getAgent());
            } else if (ba.getWhere().getName().equals("Temporary Members"))
            {
                temporary_members.add(ba.getAgent());
            } else if (ba.getWhere().getName().equals("Regular Members"))
            {
                regular_members.add(ba.getAgent());
            }
            }
            };
            handlers.put(IntrospectionVocabulary.BORNAGENT,
            creationsHandler);
            }
        };
        Behaviour startVoting = new TickerBehaviour(this,5000) {
            protected void onTick() 
            {
               for (AID i : permanent_members)
               {
                   try {
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    ArrayList<Type> obj = new ArrayList<Type>();
                    for (AID k : temporary_members)
                    {
                        obj.add(new Type(k,"Temporary Member"));
                    }
                    for (AID k : regular_members)
                    {
                        obj.add(new Type(k,"Regular Member"));
                    }
                    msg.setContentObject(obj);
                    msg.addReceiver(i);
                    msg.setSender(getAID());
                    msg.setConversationId("C");
                    send(msg);
                   } catch (Exception e)
                   {
                       e.printStackTrace();
                   }
                   
               }
            }
        };
        Behaviour cyclic = new CyclicBehaviour(this) {
            public void action()
            {
                MessageTemplate mt = MessageTemplate.MatchConversationId("A");
                ACLMessage msg = id.receive(mt);
                if (msg != null)
                {
                    System.out.println("Sent by: " + msg.getSender());
                    try {
                    ArrayList<Type> data = (ArrayList<Type>) msg.getContentObject();
                    Nice nic = new Nice(msg.getSender(),data);
                    message_records.add(nic);
                    for (Nice n : message_records)
                    {
                        for (Type ty : n.record)
                        {
                            System.out.println(ty.name.toString() + " - " + ty.type + " - " + ty.vote);
                        }
                    }
                    /*(for (Type i : data)
                    {
                        System.out.println(i.name.toString() + " - " + i.type + " - " + i.vote);
                    }*/
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        };
        ParallelBehaviour par = new ParallelBehaviour( ParallelBehaviour.WHEN_ALL );
        par.addSubBehaviour(myAMSSubscriber);
        par.addSubBehaviour(startVoting);
        par.addSubBehaviour(cyclic);
        addBehaviour(par);
    }
}
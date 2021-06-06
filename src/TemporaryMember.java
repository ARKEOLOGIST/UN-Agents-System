import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.*;
import jade.domain.JADEAgentManagement.*;
import jade.domain.mobility.*;

import jade.domain.*;

import jade.core.behaviours.*;
import jade.core.*;

import jade.content.onto.basic.*;

import jade.content.*;
import jade.content.abs.*;
import jade.content.onto.*;
import jade.content.lang.*;
import jade.content.lang.sl.*;

import jade.util.leap.Iterator;
import jade.util.leap.List;

public class TemporaryMember extends Agent {

    private ContentManager manager  = (ContentManager) getContentManager();
    private Codec codec = new SLCodec();
    private Ontology ontology = JADEManagementOntology.getInstance();

    @Override
    protected void setup() {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName( getAID() ); 
        ServiceDescription sd  = new ServiceDescription();
        sd.setType("Temporary");
        sd.setName(getLocalName());
        dfd.addServices(sd);
        try {  
            DFService.register(this, dfd);  
        }
        catch (Exception fe) { fe.printStackTrace(); }
        getContentManager().registerLanguage(codec);
        getContentManager().registerOntology(ontology);
    }
}
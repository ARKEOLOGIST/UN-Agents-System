import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

import java.util.Scanner;

import java.io.*;

import jade.core.*;
import jade.core.Runtime;

public class Main {
	public static int i = 1;
	public Profile setProfile(String name) {
		Profile p= new ProfileImpl();
		p.setParameter(Profile.CONTAINER_NAME, name);
		p.setParameter(Profile.GUI, "true");
		p.setParameter(Profile.LOCAL_HOST, "localhost");
		p.setParameter(Profile.EXPORT_HOST, "localhost");
		p.setParameter(Profile.MAIN_HOST, "localhost");
		return p;
	}
    public static void main(String[] args) {
        Runtime r= Runtime.instance();
		Main ob = new Main();
        Profile p= ob.setProfile("Master Member");
		ContainerController cc = r.createMainContainer(p);
		Profile profile1= ob.setProfile("Permanent Members");
		Profile profile2= ob.setProfile("Regular Members");
		Profile profile3= ob.setProfile("Temporary Members");
		ContainerController c1 = r.createAgentContainer(profile1);
		ContainerController c2 = r.createAgentContainer(profile2);
		ContainerController c3 = r.createAgentContainer(profile3);
		AgentController acm,ac1,ac2,ac3;
		try {
				acm = cc.createNewAgent("Master Member", "MasterMember", null);
				acm.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
        int x=4;
		try {
			File myObj = new File("input.txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
			  String data = myReader.nextLine();
			  x = Integer.parseInt(data);
			  if (x==1) {
				try {
					System.out.println("Permanent Member");
					ac1 = c1.createNewAgent("Member " + i, "PermanentMember", null);
					ac1.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
                Main.i++;
			}
			else if (x==2) {
				try {
					System.out.println("Regular Member");
					ac2 = c2.createNewAgent("Member " + i, "RegularMember", null);
					ac2.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
				Main.i++;
			}
			else if (x==3) {
				try {
					System.out.println("Temporary Member");
					ac3 = c3.createNewAgent("Member " + i, "TemporaryMember", null);
					ac3.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
				Main.i++;
			}
			else if (x == 0) {
				//sc.close();
				System.out.println("Quit");
				System.exit(0);
			}
			  //System.out.println(data);
			}
			myReader.close();
		  } catch (Exception e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		  }
		/*while(x>0) {
			System.out.println("1. Permanent Member.\n2. Regular Member.\n3. Temporary Member.\n0. Exit.\n");
			Scanner sc= new Scanner(System.in);
			x=sc.nextInt();
			if (x==1) {
				try {
					ac1 = c1.createNewAgent("Member " + i, "PermanentMember", null);
					ac1.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
                Main.i++;
			}
			else if (x==2) {
				try {
					ac2 = c2.createNewAgent("Member " + i, "RegularMember", null);
					ac2.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
				Main.i++;
			}
			else if (x==3) {
				try {
					ac3 = c3.createNewAgent("Member " + i, "TemporaryMember", null);
					ac3.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
				Main.i++;
			}
			else if (x == 0) {
				sc.close();
				System.exit(0);
			}
    }*/
}
}
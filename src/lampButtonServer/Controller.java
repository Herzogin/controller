package lampButtonServer;

import buttonLampInterfaces.LampInterface;
import buttonLampInterfaces.ButtonInterface;
import buttonLampInterfaces.ControllerInterface;
import lamp.Lamp;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Controller extends java.rmi.server.UnicastRemoteObject implements ControllerInterface {

	public Controller() throws RemoteException {
		super();
	}

	public void start() {
		try {
			Registry registry = LocateRegistry.createRegistry(3000/*Registry.REGISTRY_PORT*/);
			String[] list = registry.list();
			
			Thread.sleep(10000);
			ButtonInterface bi = (ButtonInterface) registry.lookup("button");
			bi.register(this);
			
			Lamp l = new Lamp();
			registry.bind("Lamp", l);
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void update() throws RemoteException {
		LampInterface li;
		try {
			System.out.println("Switch on lamp here");
			li = (LampInterface) Naming.lookup("rmi://localhost:3000/Lamp");
			li.changeStatus();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws RemoteException {
		Controller s = new Controller();
		
		s.start();
	}
	
}

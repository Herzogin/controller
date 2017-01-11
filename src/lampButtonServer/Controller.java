package lampButtonServer;

import buttonLampInterfaces.LampInterface;
import buttonLampInterfaces.ButtonInterface;
import buttonLampInterfaces.ControllerInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Controller extends java.rmi.server.UnicastRemoteObject implements ControllerInterface {
	private LampInterface li = null;

	public Controller() throws RemoteException {
		super();
	}

	public void start() {
		try {
			Registry registry = LocateRegistry.createRegistry(3000/*Registry.REGISTRY_PORT*/);
			String[] list = registry.list();
			
			Thread.sleep(30000);
			ButtonInterface bi = (ButtonInterface) registry.lookup("button");
			bi.register(this);
			
			li = (LampInterface) registry.lookup("lamp");
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void update() throws RemoteException {
		try {
			System.out.println("Switch on lamp here");
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

package lampButtonServer;

import buttonLampInterfaces.LampInterface;
import buttonLampInterfaces.ButtonInterface;
import buttonLampInterfaces.ControllerInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class Controller extends java.rmi.server.UnicastRemoteObject implements ControllerInterface {
	private LampInterface li = null;
	private List<LampInterface> lampGroup = new ArrayList<LampInterface>();

	public Controller() throws RemoteException {
		super();
	}

	public void start() {
		try {
			Registry registry = LocateRegistry.createRegistry(3000/*Registry.REGISTRY_PORT*/);
			
			Thread.sleep(20000); // pause this program until we started all the button and lamp services
			
			String[] list = registry.list();
			
			for (String item : list ) {

				if (item.contains("button")) {
					ButtonInterface bi = (ButtonInterface) registry.lookup(item);
					bi.register(this);
					System.out.println("button was registered: " + item);

				} else if (item.contains("lamp")) {
					li = (LampInterface) registry.lookup(item);

					lampGroup.add(li); // we save every lamp item in one lampgroup, which we will iterate on later
					System.out.println("lamp was registered: " + item);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void update() throws RemoteException {
		try {
			System.out.println("Switch on lamp here");
			for (LampInterface lamp: lampGroup) {
				lamp.changeStatus();
				System.out.println("changed status of lamp: " + lamp);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws RemoteException {
		Controller s = new Controller();
		
		s.start();
	}
	
}

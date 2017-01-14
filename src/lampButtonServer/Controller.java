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
			System.out.println("Controller started. Registry gets created...");
			Registry registry = LocateRegistry.createRegistry(3000/*Registry.REGISTRY_PORT*/);
			System.out.println("Registry created. Add your buttons and lamps.");
			
			Thread.sleep(30000); // pause this program until we started all the button and lamp services
			
			String[] list = registry.list();
			
			for (String item : list ) {

				if (item.contains("button")) {
					ButtonInterface bi = (ButtonInterface) registry.lookup(item);
					bi.register(this);
					System.out.println("button was registered: " + item);

				} else if (item.contains("lamp")) {
					li = (LampInterface) registry.lookup(item);

					lampGroup.add(li);
					System.out.println("lamp was registered: " + item);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void update(String version) throws RemoteException {
		try {
			for (int i = 0; i < lampGroup.size(); i++) {
				if (version.equals("even")) {
					if (i % 2 == 0){
						lampGroup.get(i).changeStatus();
						System.out.println("Changed status of lamp: " + lampGroup.get(i));
					}
				} else {
					if (i % 2 != 0) {
						lampGroup.get(i).changeStatus();
						System.out.println("Changed status of lamp: " + lampGroup.get(i));
					}
				}
			}
			
			//Below turns all lamps on and off
			/*for (LampInterface lamp: lampGroup) {
				lamp.changeStatus();
				System.out.println("Changed status of lamp: " + lamp);
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws RemoteException {
		Controller s = new Controller();
		
		s.start();
	}
	
}

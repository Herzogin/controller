package lampButtonServer;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import org.htw.fiw.vs.IBinder;
import org.htw.fiw.vs.team1.ButtonInterface;
import org.htw.fiw.vs.team1.ControllerInterface;
import org.htw.fiw.vs.team1.LampInterface;

public class Controller extends java.rmi.server.UnicastRemoteObject implements ControllerInterface {
	private LampInterface li = null;
	private List<LampInterface> lampGroup = new ArrayList<LampInterface>();
	private List<String> buttonGroup = new ArrayList<String>();

	public Controller() throws RemoteException {
		super();
	}

	public void start() {
		try {
			System.out.println("Controller started. Registry gets created...");
			//Registry registry = LocateRegistry.createRegistry(3000/*Registry.REGISTRY_PORT*/);
			
			IBinder registry = (IBinder) Naming.lookup("rmi://141.45.209.97/binder");
			
			System.out.println("Registry created. Add your buttons and lamps.");
			
			//Thread.sleep(40000); // pause this program until we started all the button and lamp services
			
			String[] list = registry.list();
			
			for (String item : list ) {

				if (item.contains("button")) {
					ButtonInterface bi = (ButtonInterface) registry.lookup(item);
					bi.register(this);
					System.out.println("button was registered: " + item);
					buttonGroup.add(item);

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
	public void update(String name) throws RemoteException {
		try {
			System.out.println("update called");
			for (int i = 0; i < lampGroup.size(); i++) {
				if (buttonGroup.indexOf(name) % 2 == 0) {
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
	
	public static void main(String[] args) throws RemoteException, UnknownHostException {
		Controller s = new Controller();
		
		s.start();
	
	}
	
}

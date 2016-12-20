package lampButtonServer;

public interface LampInterface extends java.rmi.Remote {
	
	public void changeStatus() throws java.rmi.RemoteException;
	
}

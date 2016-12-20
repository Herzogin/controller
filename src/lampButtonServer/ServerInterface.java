package lampButtonServer;

public interface ServerInterface extends java.rmi.Remote {
	
	public void sendMessage(String message) throws java.rmi.RemoteException;

}

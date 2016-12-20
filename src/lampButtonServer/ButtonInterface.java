package lampButtonServer;

public interface ButtonInterface extends java.rmi.Remote {
	
	public void press() throws java.rmi.RemoteException;

}

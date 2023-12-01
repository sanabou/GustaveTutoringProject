package bank;

public class BankProxy implements bank.Bank {
  private String _endpoint = null;
  private bank.Bank bank = null;
  
  public BankProxy() {
    _initBankProxy();
  }
  
  public BankProxy(String endpoint) {
    _endpoint = endpoint;
    _initBankProxy();
  }
  
  private void _initBankProxy() {
    try {
      bank = (new bank.BankServiceLocator()).getBank();
      if (bank != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)bank)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)bank)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (bank != null)
      ((javax.xml.rpc.Stub)bank)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public bank.Bank getBank() {
    if (bank == null)
      _initBankProxy();
    return bank;
  }
  
  public bank.Customer[] getCustomers() throws java.rmi.RemoteException{
    if (bank == null)
      _initBankProxy();
    return bank.getCustomers();
  }
  
  public long generateRIB() throws java.rmi.RemoteException{
    if (bank == null)
      _initBankProxy();
    return bank.generateRIB();
  }
  
  public void virement(long fromRib, long toRib, double amount) throws java.rmi.RemoteException{
    if (bank == null)
      _initBankProxy();
    bank.virement(fromRib, toRib, amount);
  }
  
  public double getBalance(long rib) throws java.rmi.RemoteException{
    if (bank == null)
      _initBankProxy();
    return bank.getBalance(rib);
  }
  
  public double currencyConversion(double amount, java.lang.String from, java.lang.String to) throws java.rmi.RemoteException{
    if (bank == null)
      _initBankProxy();
    return bank.currencyConversion(amount, from, to);
  }
  
  public void newAccount(java.lang.String name, double balance, java.lang.String currency) throws java.rmi.RemoteException{
    if (bank == null)
      _initBankProxy();
    bank.newAccount(name, balance, currency);
  }
  
  public void printCustomers() throws java.rmi.RemoteException{
    if (bank == null)
      _initBankProxy();
    bank.printCustomers();
  }
  
  public bank.Customer retrieveCustomerByName(java.lang.String name) throws java.rmi.RemoteException{
    if (bank == null)
      _initBankProxy();
    return bank.retrieveCustomerByName(name);
  }
  
  
}
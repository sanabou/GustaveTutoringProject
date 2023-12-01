/**
 * Bank.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package bank;

public interface Bank extends java.rmi.Remote {
    public bank.Customer[] getCustomers() throws java.rmi.RemoteException;
    public long generateRIB() throws java.rmi.RemoteException;
    public void virement(long fromRib, long toRib, double amount) throws java.rmi.RemoteException;
    public double getBalance(long rib) throws java.rmi.RemoteException;
    public double currencyConversion(double amount, java.lang.String from, java.lang.String to) throws java.rmi.RemoteException;
    public void newAccount(java.lang.String name, double balance, java.lang.String currency) throws java.rmi.RemoteException;
    public void printCustomers() throws java.rmi.RemoteException;
    public bank.Customer retrieveCustomerByName(java.lang.String name) throws java.rmi.RemoteException;
}

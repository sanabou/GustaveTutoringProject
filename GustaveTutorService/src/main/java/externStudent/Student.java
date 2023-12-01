/**
 * Student.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package externStudent;

public class Student  implements java.io.Serializable {
    private java.lang.String email;

    private int idStudent;

    private java.lang.String name;

    private java.lang.String nature;

    private java.lang.String password;

    private java.lang.String preferredCurrency;

    private double totalExpenses;

    public Student() {
    }

    public Student(
           java.lang.String email,
           int idStudent,
           java.lang.String name,
           java.lang.String nature,
           java.lang.String password,
           java.lang.String preferredCurrency,
           double totalExpenses) {
           this.email = email;
           this.idStudent = idStudent;
           this.name = name;
           this.nature = nature;
           this.password = password;
           this.preferredCurrency = preferredCurrency;
           this.totalExpenses = totalExpenses;
    }


    /**
     * Gets the email value for this Student.
     * 
     * @return email
     */
    public java.lang.String getEmail() {
        return email;
    }


    /**
     * Sets the email value for this Student.
     * 
     * @param email
     */
    public void setEmail(java.lang.String email) {
        this.email = email;
    }


    /**
     * Gets the idStudent value for this Student.
     * 
     * @return idStudent
     */
    public int getIdStudent() {
        return idStudent;
    }


    /**
     * Sets the idStudent value for this Student.
     * 
     * @param idStudent
     */
    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }


    /**
     * Gets the name value for this Student.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this Student.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the nature value for this Student.
     * 
     * @return nature
     */
    public java.lang.String getNature() {
        return nature;
    }


    /**
     * Sets the nature value for this Student.
     * 
     * @param nature
     */
    public void setNature(java.lang.String nature) {
        this.nature = nature;
    }


    /**
     * Gets the password value for this Student.
     * 
     * @return password
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this Student.
     * 
     * @param password
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }


    /**
     * Gets the preferredCurrency value for this Student.
     * 
     * @return preferredCurrency
     */
    public java.lang.String getPreferredCurrency() {
        return preferredCurrency;
    }


    /**
     * Sets the preferredCurrency value for this Student.
     * 
     * @param preferredCurrency
     */
    public void setPreferredCurrency(java.lang.String preferredCurrency) {
        this.preferredCurrency = preferredCurrency;
    }


    /**
     * Gets the totalExpenses value for this Student.
     * 
     * @return totalExpenses
     */
    public double getTotalExpenses() {
        return totalExpenses;
    }


    /**
     * Sets the totalExpenses value for this Student.
     * 
     * @param totalExpenses
     */
    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Student)) return false;
        Student other = (Student) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.email==null && other.getEmail()==null) || 
             (this.email!=null &&
              this.email.equals(other.getEmail()))) &&
            this.idStudent == other.getIdStudent() &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.nature==null && other.getNature()==null) || 
             (this.nature!=null &&
              this.nature.equals(other.getNature()))) &&
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword()))) &&
            ((this.preferredCurrency==null && other.getPreferredCurrency()==null) || 
             (this.preferredCurrency!=null &&
              this.preferredCurrency.equals(other.getPreferredCurrency()))) &&
            this.totalExpenses == other.getTotalExpenses();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getEmail() != null) {
            _hashCode += getEmail().hashCode();
        }
        _hashCode += getIdStudent();
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getNature() != null) {
            _hashCode += getNature().hashCode();
        }
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        if (getPreferredCurrency() != null) {
            _hashCode += getPreferredCurrency().hashCode();
        }
        _hashCode += new Double(getTotalExpenses()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Student.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://externStudent", "Student"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("email");
        elemField.setXmlName(new javax.xml.namespace.QName("http://externStudent", "email"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idStudent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://externStudent", "idStudent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://externStudent", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nature");
        elemField.setXmlName(new javax.xml.namespace.QName("http://externStudent", "nature"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("http://externStudent", "password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("preferredCurrency");
        elemField.setXmlName(new javax.xml.namespace.QName("http://externStudent", "preferredCurrency"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalExpenses");
        elemField.setXmlName(new javax.xml.namespace.QName("http://externStudent", "totalExpenses"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}

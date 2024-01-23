import java.lang.reflect.Constructor;
import java.util.HashMap;

class SObject {
  public HashMap<String, String> attributes = new HashMap<>();
  private boolean canAddNew = true;
  private int ID;
  public SObject() {
    ID = SObjectManager.register(this);
  }
  public int getID() {
    return this.ID;
  }
  public void setID(int ID) {
    this.ID = ID;
  }
  protected String getAttributeValue(String attrName) {
    if (this.attributes.containsKey(attrName)) {
      return this.attributes.get(attrName);
    }
    return "";
  }
  protected String setAttributeValue(String attrName, String value) {
    if (this.attributes.containsKey(attrName)) {
      String oldValue = this.attributes.get(attrName);
      this.attributes.put(attrName, value);
      return oldValue;
    } else if (canAddNew) {
      this.attributes.put(attrName, value);
      return "";
    }
    return "";
  }
  protected boolean executeMethod(String methodName, String inputParams, String[] outputParams) {
    outputParams[0] = "";
    return false;
  }
}

class SFraction extends SObject {
  public boolean isPositive() {
    int numerator, denominator;
    numerator = Integer.parseInt(this.getAttributeValue("Numerator"));
    denominator = Integer.parseInt(this.getAttributeValue("Denominator"));
    return numerator * denominator > 0;
  }
  public boolean isGreaterThan(double x) {
    return getValue() > x;
  }
  public double getValue() {
    int numerator, denominator;
    numerator = Integer.parseInt(this.getAttributeValue("Numerator"));
    denominator = Integer.parseInt(this.getAttributeValue("Denominator"));
    return (double) numerator / denominator;
  }
  @Override
  public boolean executeMethod(String methodName, String inputParams, String[] outputParams) {
    switch (methodName.toLowerCase()) {
      case "ispositive":
        outputParams[0] = String.valueOf(isPositive());
        return true;
      case "getvalue":
        outputParams[0] = String.valueOf(getValue());
        return true;
      case "isgreaterthan":
        double x = Double.parseDouble(inputParams);
        outputParams[0] = String.valueOf(isGreaterThan(x));
        return true;
      default:
        return false;
    }
  }
}

class SStudent extends SObject {
  @Override
  public boolean executeMethod(String methodName, String inputParams, String[] outputParams) {
    return true;
  }
}

class SObjectManager {
  private static HashMap<Integer, SObject> objects = new HashMap<>();
  private static int id = 1;
  public static int register(SObject sObject) {
    SObjectManager.objects.put(id, sObject);
    return id++;
  }
  public static String getAttributeValue(int ID, String attrName) {
    SObject obj = findObjectByID(ID);
    if (obj != null) {
      return obj.getAttributeValue(attrName);
    }
    return "";
  }
  public static String setAttributeValue(int ID, String attrName, String value) {
    SObject obj = findObjectByID(ID);
    if (obj != null) {
      return obj.setAttributeValue(attrName, value);
    }
    return "";
  }
  public static boolean executeRemoteMethod(int ID, String methodName, String inputParams, String[] outputParams) {
    SObject obj = findObjectByID(ID);
    if (obj != null) {
      return obj.executeMethod(methodName, inputParams, outputParams);
    }
    return false;
  }
  private static SObject findObjectByID(int ID) {
    if (SObjectManager.objects.containsKey(ID)) {
      return SObjectManager.objects.get(ID);
    }
    return null;
  }
  public static int createObject(String className) throws Exception {
    Class<?> clazz = Class.forName(className);
    Constructor<?> ctor = clazz.getDeclaredConstructor();
    Object object = ctor.newInstance();
    if (object instanceof SObject) {
      return ((SObject) object).getID();
    }
    return -1;
  }
}

class CObject {
  protected int ID;
  protected String getAttributeValue(String attrName) {
    return CObjectManager.getAttributeValue(ID, attrName);
  }
  protected String setAttributeValue(String attrName, String value) {
    return CObjectManager.setAttributeValue(ID, attrName, value);
  }
  protected boolean executeMethod(String methodName, String inputParams, String[] outputParams) {
    return CObjectManager.executeRemoteMethod(ID, methodName, inputParams, outputParams);
  }
}

class CObjectManager {
  public static String getAttributeValue(int ID, String attrName) {
    return SObjectManager.getAttributeValue(ID, attrName);
  }
  public static String setAttributeValue(int ID, String attrName, String value) {
    return SObjectManager.setAttributeValue(ID, attrName, value);
  }
  public static boolean executeRemoteMethod(int ID, String methodName, String inputParams, String[] outputParams) {
    return SObjectManager.executeRemoteMethod(ID, methodName, inputParams, outputParams);
  }

  public static int createRemoteObject(String className) throws Exception {
    return SObjectManager.createObject(className);
  }
}

class CPhanSo extends CObject {
  private int tuSo;
  private int mauSo;

  public CPhanSo() throws Exception {
    this.ID = CObjectManager.createRemoteObject("SFraction");
    System.out.println(ID);
    this.setTuSo(0);
    this.setMauSo(1);
  }
  public CPhanSo(int tuSo, int mauSo) throws Exception {
    this.ID = CObjectManager.createRemoteObject("SFraction");
    System.out.println(ID);
    this.setTuSo(tuSo);
    this.setMauSo(mauSo);
  }
  public int getTuSo() {
    return Integer.parseInt(this.getAttributeValue("Numerator"));
  }
  public void setTuSo(int value) {
    this.setAttributeValue("Numerator", String.valueOf(value));
  }
  public int getMauSo() {
    return Integer.parseInt(this.getAttributeValue("Denominator"));
  }
  public void setMauSo(int value) {
    this.setAttributeValue("Denominator", String.valueOf(value));
  }
  public boolean soDuong() {
    String[] outputParams = { "" };
    this.executeMethod("isPositive", "", outputParams);
    return Boolean.parseBoolean(outputParams[0]);
  }
  public double giaTri() {
    String[] outputParams = { "" };
    this.executeMethod("getValue", "", outputParams);
    return Double.parseDouble(outputParams[0]);
  }
  public boolean lonHonGiaTri(double v) {
    String inputParams = String.valueOf(v);
    String[] outputParams = { "" };
    this.executeMethod("isGreaterThan", inputParams, outputParams);
    return Boolean.parseBoolean(outputParams[0]);
  }
}

public class Main {
  public static void main(String[] args) throws Exception {
    CPhanSo ps1 = new CPhanSo();
    ps1.setTuSo(18);
    ps1.setMauSo(5);
    CPhanSo ps2 = new CPhanSo(-2, 5);
    System.out.println(ps1.soDuong());
    System.out.println(ps1.giaTri());
    System.out.println(ps1.lonHonGiaTri(ps2.giaTri()));
    System.out.println(ps2.soDuong());
  }
}
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

class MyObject {
  private HashMap<String, Object> attributes = new HashMap<>();
  private static HashMap<String, MyObject> prototypes = new HashMap<>();
  private boolean canAddNew = true;
  static {
    try {
      InitSchema("D:\\JAVA PROJECT\\Software Architecture\\Schema.txt");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  private static void InitSchema(String filePath) throws Exception {
    try {
      BufferedReader in = new BufferedReader(new FileReader(filePath));
      String line = in.readLine();
      int nClasses = Integer.parseInt(line);
      for (int i = 1; i <= nClasses; i++) {
        line = in.readLine();
        String className = line;
        line = in.readLine();
        int nAttributes = Integer.parseInt(line);
        for (int j = 1; j <= nAttributes; j++) {
          line = in.readLine();
          String attributeName = line;
          line = in.readLine();
          String attributeType = line;
          MyObject newObj = new MyObject();
          newObj.setAttributeValue(attributeName, initialValue(attributeType));
          prototypes.put(className, newObj);
        }
      }
    } catch (Exception e) {
      throw new Exception(e);
    }
  }
  private static Object initialValue(String attributeType) {
    return switch (attributeType) {
      case "int" -> (int) 0;
      case "double" -> (double) 0.00;
      case "String" -> "";
      default -> null;
    };
  }
  public MyObject() {}
  public MyObject(String strType) {
    if (prototypes.containsKey(strType)) {
      CloneAttributesFromPrototype(prototypes.get(strType));
    }
    //InitAttributesByObjectType(strType);
  }
  private void CloneAttributesFromPrototype(MyObject myObject) {
    for (String attributeName : myObject.attributes.keySet()) {
      this.setAttributeValue(attributeName, myObject.getAttributeValue(attributeName));
    }
  }
  private void InitAttributesByObjectType(String strType) {
    switch (strType.toLowerCase()) {
      case "phan so":
        this.setAttributeValue("Tu So", (int) 0);
        this.setAttributeValue("Mau So", (int) 1);
        break;
      case "sinh vien":
        this.setAttributeValue("MSSV", "");
        this.setAttributeValue("Ho va Ten", "");
        this.setAttributeValue("Diem TB", (double) 0.00);
        break;
    }
  }

  public Object getAttributeValue(String attributeName) {
    if (attributes.containsKey(attributeName)) {
      return attributes.get(attributeName);
    }
    return null;
  }
  public void setAttributeValue(String attributeName, Object newValue) {
    if (attributes.containsKey(attributeName)) {
      attributes.put(attributeName, newValue);
    } else if (canAddNew) {
      attributes.put(attributeName, newValue);
    }
  }
}

public class Main {
  public static void main(String[] args) {
    MyObject sv = new MyObject("Sinh Vien");
    sv.setAttributeValue("MSSV", "20120608");
    sv.setAttributeValue("Ho va Ten", "Nguyen Huu Truc");
    sv.setAttributeValue("Diem TB", 7.82);
    System.out.println(
        "MSSV: " + sv.getAttributeValue("MSSV") +
            ", Ho va Ten: " + sv.getAttributeValue("Ho va Ten") +
            ", Diem TB: " + sv.getAttributeValue("Diem TB"));
  }
}
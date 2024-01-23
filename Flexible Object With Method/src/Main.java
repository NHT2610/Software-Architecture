import java.util.HashMap;

class MyObject {
  private HashMap<String, Object> attributes = new HashMap<>();
  private HashMap<String, MyObjectMethod> methods = new HashMap<>();
  private boolean canAddNew = true;
  protected Object getAttributeValue(String attrName) {
    if (attributes.containsKey(attrName)) {
      return attributes.get(attrName);
    }
    return null;
  }
  protected Object setAttributeValue(String attrName, Object value) {
    if (attributes.containsKey(attrName)) {
      Object oldValue = attributes.get(attrName);
      attributes.put(attrName, value);
      return oldValue;
    } else if (canAddNew) {
      attributes.put(attrName, value);
      return null;
    }
    return null;
  }
  private Object getInitialValue(Object value) {
    return switch (value.getClass().getName()) {
      case "Integer" -> 0;
      case "Double" -> 0.00;
      case "String" -> "";
      default -> null;
    };
  }
  protected void addMethod(String methodName, MyObjectMethod method) {
    if (methods.containsKey(methodName)) {
      methods.put(methodName, method);
    } else if (canAddNew) {
      methods.put(methodName, method);
    }
  }
  protected MyObjectMethod getMethod(String methodName) {
    if (methods.containsKey(methodName)) {
      return methods.get(methodName);
    }
    return null;
  }
  protected boolean executeMethod(String medthodName, String inputParams, String[] outputParams) {
    outputParams[0] = "";
    return false;
  }
}

class Fraction extends MyObject {
  public Fraction() {}
  public Fraction(Integer numerator, Integer denominator) {
    this.setNumerator(numerator);
    this.setDenominator(denominator);
  }
  public void setNumerator(Integer numerator) {
    this.setAttributeValue("Numerator", numerator);
  }
  public void setDenominator(Integer denominator) {
    this.setAttributeValue("Denominator", denominator);
  }
  public int getDenominator() {
    int denominator = 1;
    Object obj2 = this.getAttributeValue("Denominator");
    if (obj2 instanceof Integer) {
      denominator = Integer.parseInt(String.valueOf(obj2));
    }
    return denominator;
  }
  public int getNumerator() {
    int numerator = 0;
    Object obj1 = this.getAttributeValue("Numerator");
    if (obj1 instanceof Integer) {
      numerator = Integer.parseInt(String.valueOf(obj1));
    }
    return numerator;
  }
  @Override
  public boolean executeMethod(String methodName, String inputParams, String[] outputParams) {
    MyObjectMethod method = this.getMethod(methodName);
    if (method != null) {
      return method.execute(inputParams, outputParams);
    }
    return false;
  }
}

interface MyObjectMethod {
  boolean execute(String inputParams, String[] outputParams);
}

class FractionMethod implements MyObjectMethod {
  protected Fraction fraction;
  @Override
  public boolean execute(String inputParams, String[] outputParams) {
    outputParams[0] = "";
    return false;
  }
}

class IsPositive extends FractionMethod {
  public IsPositive(Fraction fraction) {
    this.fraction = fraction;
  }
  private boolean isPositive() {
    int numerator = fraction.getNumerator(), denominator = fraction.getDenominator();
    return numerator * denominator > 0;
  }
  @Override
  public boolean execute(String inputParams, String[] outputParams) {
    outputParams[0] = String.valueOf(isPositive());
    return true;
  }
}

class GetValue extends FractionMethod {
  public GetValue(Fraction fraction) {
    this.fraction = fraction;
  }
  private double getValue() {
    int numerator = fraction.getNumerator(), denominator = fraction.getDenominator();
    return (double) numerator / denominator;
  }
  @Override
  public boolean execute(String inputParams, String[] outputParams) {
    outputParams[0] = String.valueOf(getValue());
    return true;
  }
}

class IsGreaterThan extends FractionMethod {
  public IsGreaterThan(Fraction fraction) {
    this.fraction = fraction;
  }
  private boolean isGreaterThan(double x) {
    int numerator = fraction.getNumerator(), denominator = fraction.getDenominator();
    return (double) numerator / denominator > x;
  }
  @Override
  public boolean execute(String inputParams, String[] outputParams) {
    double x = Double.parseDouble(inputParams);
    outputParams[0] = String.valueOf(isGreaterThan(x));
    return true;
  }
}

public class Main {
  public static void main(String[] args) {
    Fraction ps1 = new Fraction(18, 5);
    ps1.addMethod("isPositive", new IsPositive(ps1));
    ps1.addMethod("getValue", new GetValue(ps1));
    ps1.addMethod("isGreaterThan", new IsGreaterThan(ps1));
    String[] outputParams = { "" };
    ps1.executeMethod("isPositive", "", outputParams);
    System.out.println("1. " + outputParams[0]);
    ps1.executeMethod("getValue", "", outputParams);
    System.out.println("2. " + outputParams[0]);
    ps1.executeMethod("isGreaterThan", "10.2", outputParams);
    System.out.println("3. " + outputParams[0]);
  }
}
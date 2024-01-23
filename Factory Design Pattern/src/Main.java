import java.lang.reflect.Constructor;
import java.util.HashMap;

// GameUnit interface
interface GameUnit {
  void attack();
  void defend();
  String getAttackRange();
  int getAttackRangeDistance();
  int getCost();
  int getHitPoints();
  int getDefence();
  int getDamagePoints();
  GameUnit Clone();
}

// Soldier class implementing GameUnit
class Soldier implements GameUnit {
  public static final String RANGE = "Short Range";
  public static final int RANGE_DISTANCE = 1;
  private static final int COST = 5;
  private static final int HIT_POINTS = 10;
  private static final int DEFENCE = 2;
  private static final int DAMAGE_POINTS = 3;
  @Override
  public void attack() {
    System.out.println("Soldier attacking!");
  }

  @Override
  public void defend() {
    System.out.println("Soldier moving!");
  }

  @Override
  public String getAttackRange() {
    return RANGE;
  }

  @Override
  public int getAttackRangeDistance() {
    return RANGE_DISTANCE;
  }

  @Override
  public int getCost() {
    return COST;
  }

  @Override
  public int getHitPoints() {
    return HIT_POINTS;
  }

  @Override
  public int getDefence() {
    return DEFENCE;
  }

  @Override
  public int getDamagePoints() {
    return DAMAGE_POINTS;
  }

  @Override
  public GameUnit Clone() {
    return new Soldier();
  }
}

// Archer class implementing GameUnit
class Archer implements GameUnit {
  public static final String RANGE = "Long Range";
  public static final int RANGE_DISTANCE = 3;
  private static final int COST = 8;
  private static final int HIT_POINTS = 8;
  private static final int DEFENCE = 1;
  private static final int DAMAGE_POINTS = 5;
  @Override
  public void attack() {
    System.out.println("Archer attacking!");
  }

  @Override
  public void defend() {
    System.out.println("Archer moving!");
  }

  @Override
  public String getAttackRange() {
    return RANGE;
  }

  @Override
  public int getAttackRangeDistance() {
    return RANGE_DISTANCE;
  }

  @Override
  public int getCost() {
    return COST;
  }

  @Override
  public int getHitPoints() {
    return HIT_POINTS;
  }

  @Override
  public int getDefence() {
    return DEFENCE;
  }

  @Override
  public int getDamagePoints() {
    return DAMAGE_POINTS;
  }

  @Override
  public GameUnit Clone() {
    return new Archer();
  }
}

class UnitRequirement {
  public boolean isOK(GameUnit unit) {
    return true;
  }
}

class UnitRequirement_MaximumCost extends UnitRequirement {
  private final int maxCost;

  public UnitRequirement_MaximumCost(int maxCost) {
    this.maxCost = maxCost;
  }

  @Override
  public boolean isOK(GameUnit unit) {
    return unit.getCost() <= this.maxCost;
  }
}

// GameUnitFactory class to create GameUnits
class GameUnitFactory {
  private static final HashMap<String, GameUnit> prototypes = new HashMap<>();
  static {
    prototypes.put("Soldier", new Soldier());
    prototypes.put("Archer", new Archer());
  }
  public GameUnit createGameUnit(String unitType) {
    String s = unitType.toLowerCase();
    return switch (s) {
      case "soldier" -> new Soldier();
      case "archer" -> new Archer();
      default -> throw new IllegalArgumentException("Invalid unit type: " + unitType);
    };
  }
  public GameUnit RecruitUnitFromName(String name) throws Exception {
    return CreateObjectFromClassName(name);
  }
  public GameUnit RecruitUnitFromFlexibleCriteria(UnitRequirement unitRequirement) {
    for (GameUnit unit : prototypes.values()) {
      if (unitRequirement.isOK(unit)) {
        return unit.Clone();
      }
    }
    return null;
  }
  private GameUnit CreateObjectFromClassName(String name) throws Exception {
    Class<?> clazz = Class.forName(name);
    Constructor<?> ctor = clazz.getDeclaredConstructor();
    Object object = ctor.newInstance();
    if (object instanceof GameUnit) {
      return (GameUnit) object;
    }
    return null;
  }
}

// Main class to demonstrate the Factory Pattern
public class Main {
  public static void main(String[] args) throws Exception {
    // Create a GameUnitFactory
    GameUnitFactory unitFactory = new GameUnitFactory();

    // Create a Soldier using the factory
    GameUnit soldier = unitFactory.createGameUnit("Soldier");
    System.out.println("Soldier's attack range: " + soldier.getAttackRange() + ", Attack range distance: " + soldier.getAttackRangeDistance());
    System.out.println("Soldier Cost: " + soldier.getCost() + " gold coins");
    System.out.println("Soldier Hit Points: " + soldier.getHitPoints());
    System.out.println("Soldier Defence: " + soldier.getDefence());
    System.out.println("Soldier Damage Points per Attack: " + soldier.getDamagePoints());
    soldier.attack();
    soldier.defend();

    // Create an Archer using the factory
    GameUnit archer = unitFactory.createGameUnit("Archer");
    System.out.println("Archer's range: " + archer.getAttackRange() + ", Attack range distance: " + archer.getAttackRangeDistance());
    System.out.println("Archer Cost: " + archer.getCost() + " gold coins");
    System.out.println("Archer Hit Points: " + archer.getHitPoints());
    System.out.println("Archer Defence: " + archer.getDefence());
    System.out.println("Archer Damage Points per Attack: " + archer.getDamagePoints());
    archer.attack();
    archer.defend();
    GameUnit gu = unitFactory.RecruitUnitFromName("Soldier");
    gu.attack();

    UnitRequirement unitRequirement = new UnitRequirement_MaximumCost(7);
    GameUnit gu1 = unitFactory.RecruitUnitFromFlexibleCriteria(unitRequirement);
    gu1.attack();
  }
}

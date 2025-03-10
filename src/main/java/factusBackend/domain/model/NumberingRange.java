package factusBackend.domain.model;

public class NumberingRange {
    private String id;
    private String prefix;
    private int fromNumber;
    private int toNumber;
    private int currentNumber;
    private boolean active;

    // Constructores
    public NumberingRange() {
    }

    public NumberingRange(String id, String prefix, int fromNumber, int toNumber, int currentNumber, boolean active) {
        this.id = id;
        this.prefix = prefix;
        this.fromNumber = fromNumber;
        this.toNumber = toNumber;
        this.currentNumber = currentNumber;
        this.active = active;
    }

    // Getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getFromNumber() {
        return fromNumber;
    }

    public void setFromNumber(int fromNumber) {
        this.fromNumber = fromNumber;
    }

    public int getToNumber() {
        return toNumber;
    }

    public void setToNumber(int toNumber) {
        this.toNumber = toNumber;
    }

    public int getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(int currentNumber) {
        this.currentNumber = currentNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "NumberingRange{" +
                "id='" + id + '\'' +
                ", prefix='" + prefix + '\'' +
                ", fromNumber=" + fromNumber +
                ", toNumber=" + toNumber +
                ", currentNumber=" + currentNumber +
                ", active=" + active +
                '}';
    }
}
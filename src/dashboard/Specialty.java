package dashboard;

public class Specialty {
    private int specialtyId;
    private String description;

    public Specialty(int specialtyId, String description) {
        this.specialtyId = specialtyId;
        this.description = description;
    }

    public int getSpecialtyId() {
        return specialtyId;
    }

    public String getDescription() {
        return description;
    }
}

package dashboard;

import DAO.DAOSpecialty;

import java.sql.SQLException;

public class Specialty {
    private int specialtyId;
    private String description;

    public Specialty(int specialtyId, String description) {
        this.specialtyId = specialtyId;
        this.description = description;
    }

    public Specialty(int specialtyId) {
        this.specialtyId = specialtyId;
        try {
            DAOSpecialty daoSpecialty = new DAOSpecialty();
            this.description = daoSpecialty.getDescription(specialtyId);
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public int getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(int specialtyId) {
        this.specialtyId = specialtyId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Specialty{" +
                "specialtyId=" + specialtyId +
                ", description='" + description + '\'' +
                '}';
    }
}

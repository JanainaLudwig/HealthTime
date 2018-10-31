package login;

public class Recovery {
    private int idUser;
    private String cpf;
    private String motherName;

    public Recovery(int idUser, String cpf, String motherName) {
        this.idUser = idUser;
        this.cpf = cpf;
        this.motherName = motherName;
    }

    public int getIdUser() { return idUser; }

    public String getCpf() { return cpf; }

    public String getMotherName() { return motherName; }
}
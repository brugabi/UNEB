import java.io.Serializable;

public class Q4Item implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private float lance;
    private boolean check;

    public Q4Item(String nome, boolean check) {
        this.nome = nome;
        this.check = check;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getLance() {
        return lance;
    }

    public void setLance(float lance) {
        this.lance = lance;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}

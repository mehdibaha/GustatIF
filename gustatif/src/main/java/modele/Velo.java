package modele;

import javax.persistence.Entity;

@Entity
public class Velo extends Livreur {
    private String name;
    
    public Velo(){
        super();
    }
    
    public Velo(Double chargeMax, String name){
        super(chargeMax);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    

    @Override
    public String toString() {
        return "Velo{"+ super.toString() + ", name=" + name + '}';
    }
}

package modele;

import javax.persistence.Entity;


@Entity
public class Drone extends Livreur {
    private Double vitesseMoy;
    
    public Drone() {
        super();
    }
    
    public Drone(Double chargeMax, Double vitesseMoy) {
        super(chargeMax);
        this.vitesseMoy = vitesseMoy;
    }

    public Double getVitesseMoy() {
        return vitesseMoy;
    }

    public void setVitesseMoy(Double vitesseMoy) {
        this.vitesseMoy = vitesseMoy;
    }

    @Override
    public String toString() {
        return "Drone{"+ super.toString() + ", vitesseMoy=" + vitesseMoy + '}';
    }    
}

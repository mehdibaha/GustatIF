package modele;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Version;

@Entity
@Inheritance (strategy = InheritanceType.JOINED)
public abstract class Livreur implements Serializable {
    
    public static final int ALL_TYPE=0;
    public static final int DRONE_TYPE=1;
    public static final int VELO_TYPE=2;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    
    @Version
    protected Long version;
    
    protected Boolean dispo;
    /**
     * charge maximum du livreur en grammes
     */
    protected Double chargeMax;
    protected Double longitude;
    protected Double latitude;
    
    public Livreur(){
    }
    
    public Livreur(Double chargeMax){
        dispo = true;
        this.chargeMax = chargeMax;
    }

    public Long getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }

    public Boolean isDispo() {
        return dispo;
    }

    public Double getChargeMax() {
        return chargeMax;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setDispo(boolean dispo) {
        this.dispo = dispo;
    }

    public void setChargeMax(Double chargeMax) {
        this.chargeMax = chargeMax;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Livreur{" + "id=" + id + ", dispo=" + dispo + ", chargeMax=" + chargeMax + ", longitude=" + longitude + ", latitude=" + latitude + '}';
    }    
}

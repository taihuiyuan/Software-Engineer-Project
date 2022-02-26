package fudan.se.lab2.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;
import javax.persistence.*;


/**
 * @author LBW
 */
@Entity
@Table(name = "area")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class Area {
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    public Area(int id){
        this.id = id;
    }
    String name;

    public Area(){}

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}

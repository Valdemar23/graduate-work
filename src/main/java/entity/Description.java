package entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by HP on 23.04.2018.
 */
@Entity
@Table(name="description")
public class Description implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="attribute")
    private String attribute;


    @Column(name="value")
    private String value;

    @ManyToOne
    @JoinColumn(name="tegs_id")
    private Tegs tegs;

    public Description(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Tegs getTegs() {
        return tegs;
    }

    public void setTegs(Tegs tegs) {
        this.tegs = tegs;
    }
}

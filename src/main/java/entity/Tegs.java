package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by HP on 22.04.2018.
 */

@Entity
@Table(name="tegs")
public class Tegs implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="teg")
    private String teg;

   /* @Column(name="amout_properties")
    private int amount_properties;*/

    @Column(name="amount")
    private int amount;

    @ManyToOne//пишемо тип зєднання (багато ролів будуть приходить на дану таблицю), до того ж тута буде множина тому пишемо ManyToOne
    @JoinColumn(name="page_id")//таким чином зєднуємо між собою таблички беручи дане поле із role_id
    private Webpages webpages;

    @OneToMany(mappedBy="tegs")
    private Set<Description>descriptions=new HashSet<Description>();

    public Tegs(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTeg() {
        return teg;
    }

    public void setTeg(String teg) {
        this.teg = teg;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    /*public int getAmount_properties() {
        return amount_properties;
    }

    public void setAmount_properties(int amount_properties) {
        this.amount_properties = amount_properties;
    }*/

    public Webpages getWebpages() {
        return webpages;
    }

    public void setWebpages(Webpages webpages) {
        this.webpages = webpages;
    }

    public Set<Description> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Set<Description> descriptions) {
        this.descriptions = descriptions;
    }

    @Override
    public String toString() {
        return "Tegs{" +
                "id=" + id +
                ", teg='" + teg + '\'' +
                ", amount=" + amount +
                ", webpages=" + webpages +
                ", descriptions=" + descriptions +
                '}';
    }
}

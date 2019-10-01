package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="webpages")
public class Webpages implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@Column(name="page")
	private String webpage;
	
	@ManyToOne//пишемо тип зєднання (багато ролів будуть приходить на дану таблицю), до того ж тута буде множина тому пишемо ManyToOne
	@JoinColumn(name="site_id")//таким чином зєднуємо між собою таблички беручи дане поле із role_id
	private Websites websites;

	@OneToMany(mappedBy="webpages")
	private Set<Tegs> tegs= new HashSet<Tegs>();

	public Websites getWebsites() {
		return websites;
	}

	public void setWebsites(Websites websites) {
		this.websites = websites;
	}

	public Webpages() {}
	
	public Webpages(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getWebpage() {
		return webpage;
	}

	public void setWebpage(String firstname) {
		this.webpage = firstname;
	}

	public Set<Tegs> getTegs() {
		return tegs;
	}

	public void setTegs(Set<Tegs> tegs) {
		this.tegs = tegs;
	}

	@Override
	public String toString() {
		return "Webpages{" +
				"id=" + id +
				", webpage='" + webpage + '\'' +
				", websites=" + websites +
				", tegs=" + tegs +
				'}';
	}
}

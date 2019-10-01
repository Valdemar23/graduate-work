package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="websites")
public class Websites implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	public long id;
	
	@Column(name="site")
	private String website;

	@OneToMany(mappedBy="websites")
	private Set<Webpages> webpages = new HashSet<Webpages>();

	public Websites() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String title) {
		this.website = title;
	}

	public Set<Webpages> getWebpages() {
		return webpages;
	}

	public void setWebpages(Set<Webpages> webpages) {
		this.webpages = webpages;
	}
}

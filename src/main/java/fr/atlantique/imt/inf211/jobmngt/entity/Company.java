package fr.atlantique.imt.inf211.jobmngt.entity;
// Generated 3 mars 2025, 15:44:26 by Hibernate Tools 5.6.15.Final


import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
 import com.fasterxml.jackson.annotation.ObjectIdGenerators;import java.util.HashSet;
import java.util.Set;

/**
 * Company generated by hbm2java
 */
@Entity
@Table(name="company"
    ,schema="public"
    , uniqueConstraints = @UniqueConstraint(columnNames="user_id") 
)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Company  implements java.io.Serializable {


     private int id;
     private AppUser appuser;
     private String name;
     private String description;
     private Set<JobOffer> jobOffers = new HashSet<JobOffer>(0);

    public Company() {
    }

	
    public Company(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Company(int id, AppUser appuser, String name, String description, Set<JobOffer> jobOffers) {
       this.id = id;
       this.appuser = appuser;
       this.name = name;
       this.description = description;
       this.jobOffers = jobOffers;
    }
   
     @Id
	@SequenceGenerator(name = "COMPANY_ID_GENERATOR", sequenceName = "COMPANY_ID_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "COMPANY_ID_GENERATOR") 

    
    @Column(name="id", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", unique=true)
    public AppUser getAppuser() {
        return this.appuser;
    }
    
    public void setAppuser(AppUser appuser) {
        this.appuser = appuser;
    }

    
    @Column(name="name", nullable=false)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    
    @Column(name="description")
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="company", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<JobOffer> getJobOffers() {
        return this.jobOffers;
    }
    
    public void setJobOffers(Set<JobOffer> jobOffers) {
        this.jobOffers = jobOffers;
    }

}



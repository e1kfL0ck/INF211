package fr.atlantique.imt.inf211.jobmngt.entity;
// Generated 3 mars 2025, 15:44:26 by Hibernate Tools 5.6.15.Final


import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
 import jakarta.persistence.GeneratedValue;
 import com.fasterxml.jackson.annotation.JsonIdentityInfo;
 import com.fasterxml.jackson.annotation.ObjectIdGenerators;import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * Sector generated by hbm2java
 */
@Entity
@Table(name="sector"
    ,schema="public"
    , uniqueConstraints = @UniqueConstraint(columnNames="label") 
)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Sector  implements java.io.Serializable {


     private int id;
     private String label;
     private Set<Application> applications = new HashSet<Application>(0);
     private Set<JobOffer> jobOffers = new HashSet<JobOffer>(0);

    public Sector() {
    }

	
    public Sector(int id, String label) {
        this.id = id;
        this.label = label;
    }
    public Sector(int id, String label, Set<Application> applications, Set<JobOffer> jobOffers) {
       this.id = id;
       this.label = label;
       this.applications = applications;
       this.jobOffers = jobOffers;
    }
   
     @Id
	@SequenceGenerator(name = "SECTOR_ID_GENERATOR", sequenceName = "SECTOR_ID_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "SECTOR_ID_GENERATOR") 

    
    @Column(name="id", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    
    @Column(name="label", unique=true, nullable=false, length=50)
    public String getLabel() {
        return this.label;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }

@ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="applicationsector", schema="public", joinColumns = { 
        @JoinColumn(name="sector_id", nullable=false, updatable=false) }, inverseJoinColumns = { 
        @JoinColumn(name="application_id", nullable=false, updatable=false) })
    public Set<Application> getApplications() {
        return this.applications;
    }
    
    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }

@ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="joboffersector", schema="public", joinColumns = { 
        @JoinColumn(name="sector_id", nullable=false, updatable=false) }, inverseJoinColumns = { 
        @JoinColumn(name="job_offer_id", nullable=false, updatable=false) })
    public Set<JobOffer> getJoboffers() {
        return this.jobOffers;
    }
    
    public void setJoboffers(Set<JobOffer> jobOffers) {
        this.jobOffers = jobOffers;
    }




}



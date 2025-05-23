package fr.atlantique.imt.inf211.jobmngt.entity;
// Generated 3 mars 2025, 15:44:26 by Hibernate Tools 5.6.15.Final


import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
 import jakarta.persistence.GeneratedValue;
 import com.fasterxml.jackson.annotation.JsonIdentityInfo;
 import com.fasterxml.jackson.annotation.ObjectIdGenerators;import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * Applicationmessage generated by hbm2java
 */
@Entity
@Table(name="applicationmessage"
    ,schema="public"
)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class ApplicationMessage implements java.io.Serializable {


     private int id;
     private Application application;
     private JobOffer joboffer;
     private Date date;
     private String message;

    public ApplicationMessage() {
    }

    public ApplicationMessage(int id, Application application, JobOffer joboffer, Date date, String message) {
       this.id = id;
       this.application = application;
       this.joboffer = joboffer;
       this.date = date;
       this.message = message;
    }
   
     @Id
	@SequenceGenerator(name = "APPLICATIONMESSAGE_ID_GENERATOR", sequenceName = "APPLICATIONMESSAGE_ID_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "APPLICATIONMESSAGE_ID_GENERATOR") 

    
    @Column(name="id", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="application_id", nullable=false)
    public Application getApplication() {
        return this.application;
    }
    
    public void setApplication(Application application) {
        this.application = application;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="job_offer_id", nullable=false)
    public JobOffer getJoboffer() {
        return this.joboffer;
    }
    
    public void setJoboffer(JobOffer joboffer) {
        this.joboffer = joboffer;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="date", nullable=false, length=13)
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }

    
    @Column(name="message", nullable=false)
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }




}



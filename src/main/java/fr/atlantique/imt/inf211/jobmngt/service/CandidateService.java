package fr.atlantique.imt.inf211.jobmngt.service;

import fr.atlantique.imt.inf211.jobmngt.entity.Candidate;

import java.util.List;

public interface CandidateService {
    public Candidate createCandidate(String mail, String password, String city, String lastname, String firstname);

    public void removeCandidate(int id);

    public Candidate updateCandidate(Candidate candidate);

    public Candidate getCandidate(int id);

    public List<Candidate> listOfCandidates();
}

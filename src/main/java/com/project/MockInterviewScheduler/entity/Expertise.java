package com.project.MockInterviewScheduler.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Expertise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private CustomUser user;
    private String domain;
    @ElementCollection
    @CollectionTable(name = "expertise_subdomains", joinColumns = @JoinColumn(name = "expertise_id"))
    @Column(name = "subdomain")
    private List<String> subDomains;

    public Set<String> getMatchingSubDomains(Expertise other) {
        if (other == null || other.getSubDomains() == null || this.subDomains == null) {
            return new HashSet<>();
        }
        Set<String> overlap = new HashSet<>(this.subDomains);
        overlap.retainAll(other.getSubDomains());
        return overlap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Expertise expertise)) return false;
        return Objects.equals(domain, expertise.domain) &&
                Objects.equals(subDomains, expertise.subDomains);
    }

    @Override
    public int hashCode() {
        return Objects.hash(domain, subDomains);
    }

}

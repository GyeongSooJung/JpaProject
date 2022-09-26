package hellojpa;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team  extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name="TEAM_ID")
    private Long id;

    private String teamName;

    @OneToMany
    @JoinColumn(name = "TEAM_ID")
    private List<Member> members = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }


}

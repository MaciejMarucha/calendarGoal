package model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(
        name = "uk_goal_name", columnNames = "goalName"))
public class Goal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @Min(1)
    private String goalName;

    private Integer sequence;

    @OneToMany(mappedBy = "goal", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    private List<DateGoalResult> dateGoalResultList;

    public Goal(){}
    public Goal(String goalName) {
        this.goalName = goalName;
    }
}

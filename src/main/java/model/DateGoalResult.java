package model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
public class DateGoalResult implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dateGoalResultId;

    @Column(nullable = false)
    private LocalDate localDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "goal_id")
    private Goal goal;

    @Column(nullable = false)
    private int result;

    public DateGoalResult() {
    }

    public DateGoalResult(LocalDate localDate, Goal goal, int result) {
        this.localDate = localDate;
        this.goal = goal;
        this.result = result;
    }
}

package web.app.scrable.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@NoArgsConstructor
@Table(name="polishwords")
public class PolishWords {

    @Id
    @Column(name="word")
    private String word;
    @Column(name="length")
    private int length;
}

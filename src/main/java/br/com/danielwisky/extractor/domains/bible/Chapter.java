package br.com.danielwisky.extractor.domains.bible;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Chapter implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  private Integer chapter;

  @OneToMany(cascade = ALL)
  private List<Verse> verses;
}
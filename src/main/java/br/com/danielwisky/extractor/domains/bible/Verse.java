package br.com.danielwisky.extractor.domains.bible;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Verse implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  private Integer number;

  private String verse;
}
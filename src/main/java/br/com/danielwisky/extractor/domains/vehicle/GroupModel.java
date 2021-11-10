package br.com.danielwisky.extractor.domains.vehicle;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class GroupModel implements Serializable {

  private static final long serialVersionUID = -3032289196544394898L;

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  private String name;
}
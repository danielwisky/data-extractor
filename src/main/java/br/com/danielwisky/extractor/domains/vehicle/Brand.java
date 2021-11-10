package br.com.danielwisky.extractor.domains.vehicle;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;
import org.hibernate.annotations.BatchSize;

@Data
@Entity
public class Brand implements Serializable {

  private static final long serialVersionUID = -589694073953799110L;

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  private String name;

  private String externalCode;

  @BatchSize(size = 50)
  @OneToMany(cascade = ALL, fetch = FetchType.LAZY)
  private List<Model> models;
}

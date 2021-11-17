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
public class Vehicle implements Serializable {

  private static final long serialVersionUID = 408855068954439795L;

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  private String externalCode;

  private String year;

  private Double speed;

  private Double time0to100;

  private String name;

  private String engine;

  private String engineShort;

  private Double weight;

  @BatchSize(size = 50)
  @OneToMany(cascade = ALL, fetch = FetchType.EAGER)
  private List<Metric> metrics;
}
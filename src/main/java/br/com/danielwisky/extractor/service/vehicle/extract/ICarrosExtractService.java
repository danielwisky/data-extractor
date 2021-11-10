package br.com.danielwisky.extractor.service.vehicle.extract;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.math.NumberUtils.isParsable;

import br.com.danielwisky.extractor.domains.vehicle.Brand;
import br.com.danielwisky.extractor.domains.vehicle.Metric;
import br.com.danielwisky.extractor.domains.vehicle.Model;
import br.com.danielwisky.extractor.domains.vehicle.Vehicle;
import br.com.danielwisky.extractor.repositories.GenericRepository;
import br.com.danielwisky.extractor.service.AnoModeloResponse;
import br.com.danielwisky.extractor.service.ModeloResponse;
import br.com.danielwisky.extractor.service.VehicleExtractService;
import br.com.danielwisky.extractor.utils.JsonReaderUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@AllArgsConstructor
public class ICarrosExtractService implements VehicleExtractService {

  private static final String VEHICLE_URL = "https://www.icarros.com.br/%s/%s/%s/ficha-tecnica";

  private static final String VEHICLE_DETAIL_URL = "https://www.icarros.com.br/catalogo/fichatecnica.jsp?modelo=%s&anomodelo=%s&versao=%s";

  private static final String VALUE = "value";

  private static final String MODEL_QUERY = "input[name=modelo]";

  private static final String YEAR_QUERY = "input[name=anomodelo]";

  private static final String VEHICLE_VERSION_QUERY = "#versaoForm option";

  private static final String TD = "td";

  private static final String TABLE_TR = "table tr";

  private static final String MODEL_URL = "https://www.icarros.com.br/rest/select-options/marca/%s/modelos";

  private static final String YEAR_MODEL_URL = "https://www.icarros.com.br/rest/select-options/modelo/%s/anosModelo";

  private GenericRepository genericRepository;

  @Override
  @SneakyThrows
  public List<Brand> extract() {

    final Brand brand = new Brand();
    brand.setExternalCode("5");
    brand.setName("Chevrolet");
    brand.setModels(extractModels(brand));

    return Arrays.asList(brand);
  }

  @SneakyThrows
  private List<Model> extractModels(final Brand brand) {
    System.out.println(String.format("reading %s ...", brand.getName()));
    final List<ModeloResponse> responses =
            JsonReaderUtil.readJsonFromUrl(String.format(MODEL_URL, brand.getExternalCode()), ModeloResponse.class);
    return responses
            .stream()
            .map(response -> buildModel(response, brand))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
  }

  @SneakyThrows
  private Model buildModel(final ModeloResponse response, final Brand brand) {
    sleep();
    if (nonNull(genericRepository.findModelByExternalCode(response.getId()))) {
      return null;
    }

    System.out.println(String.format("reading %s ...", response.getNome()));
    final Model model = new Model();
    model.setExternalCode(response.getId());
    model.setName(response.getNome());

    final List<AnoModeloResponse> responses =
            JsonReaderUtil.readJsonFromUrl(String.format(YEAR_MODEL_URL, model.getExternalCode()), AnoModeloResponse.class);

    model.setVehicles(responses
            .stream()
            .flatMap(it -> extractVehicles(brand, it, model.getName()).stream())
            .collect(Collectors.toList()));

    genericRepository.save(model);
    return model;
  }

  @SneakyThrows
  private List<Vehicle> extractVehicles(final Brand brand, final AnoModeloResponse response, final String modelName) {
    sleep();
    final String externalBrandName = StringUtils.lowerCase(brand.getName()).replaceAll(" ", "-");
    final String externalModelName = StringUtils.lowerCase(modelName).replaceAll(" ", "-");
    final Document vehicleAsDoc = Jsoup.connect(String.format(VEHICLE_URL, externalBrandName, externalModelName, response.getNome())).get();
    final String modelCode = vehicleAsDoc.select(MODEL_QUERY).attr(VALUE);
    final String yearCode = vehicleAsDoc.select(YEAR_QUERY).attr(VALUE);

    return vehicleAsDoc.select(VEHICLE_VERSION_QUERY)
            .stream()
            .map(element -> extractVehicleDetail(modelCode, yearCode, element, response.getNome()))
            .filter(Objects::nonNull)
            .collect(toList());
  }

  private Vehicle extractVehicleDetail(final String modelCode, final String yearCode, final Element element, final String year) {
    try {
      final String versionCode = element.attr(VALUE);
      final String version = element.text();
      final String vehicleUrl = String.format(VEHICLE_DETAIL_URL, modelCode, yearCode, versionCode);

      final Document vehicleDetailAsDoc = Jsoup.connect(vehicleUrl).get();
      final Elements vehicleDetailAsElems = vehicleDetailAsDoc.select(TABLE_TR);

      final String engine = vehicleDetailAsElems.get(0).select(TD).get(1).text();
      final String weight = vehicleDetailAsElems.get(17).select(TD).get(1).text();

      final Vehicle vehicle = new Vehicle();
      vehicle.setYear(year);
      vehicle.setName(version);
      vehicle.setEngine(engine);
      vehicle.setWeight(parseASDouble(weight));
      vehicle.setMetrics(new ArrayList<>());

      buildMetrics(vehicleDetailAsElems, vehicle);

      if (isNull(vehicle.getWeight()) && isNull(vehicle.getEngine())) {
        return null;
      } else {
        return vehicle;
      }
    } catch (Exception e) {
      System.out.println("error: " + e.getLocalizedMessage());
      return null;
    }
  }

  private void sleep() throws InterruptedException {
    //Thread.sleep(600);
  }

  private void buildMetrics(final Elements vehicleDetailAsElems, final Vehicle vehicle) {

    final Metric metricT1 = new Metric();
    final Metric metricT2 = new Metric();

    final Elements metricElements = vehicleDetailAsElems.get(1).select(TD);
    metricT1.setType(metricElements.get(1).text());
    metricT2.setType(metricElements.get(2).text());

    final Elements powerElements = vehicleDetailAsElems.get(2).select(TD);
    metricT1.setPower(parseASDouble(powerElements.get(1).text()));
    metricT2.setPower(parseASDouble(powerElements.get(2).text()));

    final Elements torqueElements = vehicleDetailAsElems.get(3).select(TD);
    metricT1.setTorque(parseASDouble(torqueElements.get(1).text()));
    metricT2.setTorque(parseASDouble(torqueElements.get(2).text()));

    if (nonNull(metricT1.getPower()) && nonNull(metricT1.getTorque())) {
      vehicle.getMetrics().add(metricT1);
    }

    if (nonNull(metricT2.getPower()) && nonNull(metricT2.getTorque())) {
      vehicle.getMetrics().add(metricT2);
    }
  }

  private Double parseASDouble(String valueAsString) {
    return !isParsable(valueAsString) ? null : Double.parseDouble(valueAsString.replaceAll(",", "."));
  }

  private Integer parseASInt(String valueAsString) {
    return !isParsable(valueAsString) ? null : Integer.parseInt(valueAsString);
  }
}
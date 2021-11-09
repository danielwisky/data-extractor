package br.com.danielwisky.extractor.service.vehicle;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.indexOf;
import static org.apache.commons.lang3.StringUtils.substring;
import static org.apache.commons.lang3.math.NumberUtils.isParsable;

import br.com.danielwisky.extractor.domains.vehicle.Brand;
import br.com.danielwisky.extractor.domains.vehicle.Metric;
import br.com.danielwisky.extractor.domains.vehicle.Model;
import br.com.danielwisky.extractor.domains.vehicle.Vehicle;
import br.com.danielwisky.extractor.repositories.GenericRepository;
import br.com.danielwisky.extractor.service.ModeloResponse;
import br.com.danielwisky.extractor.service.VehicleExtractService;
import br.com.danielwisky.extractor.utils.JsonReaderUtil;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@AllArgsConstructor
public class ShopcarExtractService implements VehicleExtractService {

  private static final String SHOPCAR_URL = "https://www.shopcar.com.br/%s";

  private GenericRepository genericRepository;

  @Override
  @SneakyThrows
  public List<Brand> extract() {
    final String href = format(SHOPCAR_URL, "fichatecnica.php");
    System.out.println(format("reading... %s", href));
    final Document fichaTecnicaAsDoc = Jsoup.connect(href).get();
    return fichaTecnicaAsDoc.select("li.tags-marcas a")
            .stream()
            .map(this::buildBrand)
            .collect(toList());
  }

  private Brand buildBrand(final Element element) {
    final Brand brand = new Brand();
    String name = element.text();
    name = "VW - Volkswagen".equalsIgnoreCase(name) ? "Volkswagen" : "GM - Chevrolet".equalsIgnoreCase(name) ? "Chevrolet" : name;
    brand.setName(WordUtils.capitalizeFully(name, "-".charAt(0), " ".charAt(0)));

    final String externalCode = substring(element.attr("href"), indexOf(element.attr("href"), "=") + 1);

    if (isNull(genericRepository.findBrandByName(brand.getName()))) {
      brand.setModels(extractModels(externalCode));
      genericRepository.save(brand);
    }

    return brand;
  }

  @SneakyThrows
  private List<Model> extractModels(String externalCode) {
    final String data = "acao=busca_fichas&marca=" + externalCode;
    final String href = format(SHOPCAR_URL, "getdados.php");
    System.out.println(format("reading... %s", href));
    final URL url = new URL(href);
    final HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("POST");
    con.setDoOutput(true);
    con.getOutputStream().write(data.getBytes("UTF-8"));
    List<ModeloResponse> response = JsonReaderUtil.readJsonFromInputStream(con.getInputStream(), ModeloResponse.class);
    con.disconnect();

    return response
            .stream()
            .map(it -> buildModel(it, externalCode))
            .collect(toList());
  }

  private Model buildModel(final ModeloResponse response, final String brandCode) {
    final Model model = new Model();
    model.setName(response.getNome());
    model.setVehicles(extractVehicles(brandCode, response.getId()));
    return model;
  }

  @SneakyThrows
  private List<Vehicle> extractVehicles(final String brandCode, final String modelCode) {
    String href = format(SHOPCAR_URL, format("fichatecnica.php?marca=%s&modelo=%s&pag=%s", brandCode, modelCode, "1"));
    System.out.println(format("reading... %s", href));

    Document veiculosAsDoc = Jsoup.connect(href).get();
    final List<Vehicle> vehicles = veiculosAsDoc.select("ul.resultado-busca a")
            .stream()
            .map(this::buildVehicle)
            .collect(toList());

    final int lastPage = veiculosAsDoc.select("ul.paginacao a")
            .stream()
            .reduce((first, second) -> second)
            .map(item -> substring(item.attr("href"), item.attr("href").indexOf("pag=") + 4))
            .map(this::parseASInt)
            .orElse(1);

    for (int i = 2; i <= lastPage; i++) {
      href = format(SHOPCAR_URL, format("fichatecnica.php?marca=%s&modelo=%s&pag=%s", brandCode, modelCode, i));
      System.out.println(format("reading... %s", href));
      veiculosAsDoc = Jsoup.connect(href).get();
      vehicles.addAll(veiculosAsDoc.select("ul.resultado-busca a")
              .stream()
              .map(this::buildVehicle)
              .collect(toList()));
    }

    return vehicles
            .stream()
            .map(this::enrichVehicle)
            .collect(toList());
  }

  @SneakyThrows
  private Vehicle enrichVehicle(final Vehicle vehicle) {
    String href = format(SHOPCAR_URL, format("fichatecnica.php?id=%s", vehicle.getExternalCode()));
    System.out.println(format("reading... %s", href));

    try {
      final Document detailAsDoc = Jsoup.connect(href).get();

      final Elements detailElements = detailAsDoc.select("li.l2.motor tr td");

      vehicle.setWeight(extractWeight(detailAsDoc));
      vehicle.setEngine(detailElements.get(3).text());
      vehicle.setMetrics(new ArrayList<>());

      String[] tipoAsSplit = detailElements.get(12).text().replace("n/d", "ND").split("/");
      String[] potenciaAsSplit = detailElements.get(14).text().split("/");
      String[] torqueAsSplit = detailElements.get(18).text().split("/");

      for (int i = 0; i < tipoAsSplit.length; i++) {
        final Metric metric = new Metric();
        final String type = StringUtils.trim(tipoAsSplit[i]);
        metric.setType("ND".equalsIgnoreCase(type) ? "N/D" : type);

        try {
          metric.setPower(parseASDouble(StringUtils.trim(potenciaAsSplit[i]).replaceAll("\\D+", "")));
        } catch (Exception e) {
          metric.setPower(parseASDouble(StringUtils.trim(potenciaAsSplit[0]).replaceAll("\\D+", "")));
        }

        try {
          metric.setTorque(parseASDouble(StringUtils.trim(torqueAsSplit[i]).replaceAll("\\D+", "")));
        } catch (Exception e) {
          metric.setTorque(parseASDouble(StringUtils.trim(torqueAsSplit[0]).replaceAll("\\D+", "")));
        }

        vehicle.getMetrics().add(metric);
      }
    } catch (Exception e) {
      // nothing
    }

    return vehicle;
  }

  private Vehicle buildVehicle(final Element element) {
    final Vehicle vehicle = new Vehicle();
    vehicle.setExternalCode(StringUtils.substring(element.attr("href"), element.attr("href").indexOf("id=") + 3));
    vehicle.setName(element.select("span.modelo").text());
    vehicle.setYear(StringUtils.replace(element.select("span.ano").text(), " ", ""));
    return vehicle;
  }

  private Double extractWeight(final Document doc) {
    return doc.select("div.icone.peso")
            .stream()
            .map(it -> it.text())
            .filter(it -> it.contains("PESO EM ORDEM DE MARCHA"))
            .map(it -> it.replaceAll("\\D+", ""))
            .findFirst()
            .map(this::parseASDouble)
            .orElse(null);
  }

  private Integer parseASInt(String valueAsString) {
    return !isParsable(valueAsString) ? null : Integer.parseInt(valueAsString);
  }

  private Double parseASDouble(String valueAsString) {
    return !isParsable(valueAsString) ? null : Double.parseDouble(valueAsString.replaceAll(",", "."));
  }
}
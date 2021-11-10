package br.com.danielwisky.extractor.service.vehicle.extract;

import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

import br.com.danielwisky.extractor.domains.vehicle.Brand;
import br.com.danielwisky.extractor.domains.vehicle.Model;
import br.com.danielwisky.extractor.domains.vehicle.Vehicle;
import br.com.danielwisky.extractor.service.VehicleExtractService;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.imageio.ImageIO;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@AllArgsConstructor
public class CarrosNaWebExtractService implements VehicleExtractService {

  private static final String CARROS_URL = "https://www.carrosnaweb.com.br/%s";

  private static final String MARCA_URL = "https://www.carrosnaweb.com.br/avancada.asp";

  private static final String DETALHE_URL = "resultcompara.asp?modelos=%s-%s";

  private static final String HREF = "href";

  private static final String PROXIMA = "Pr�xima";

  private static final String FICHA_DETALHE_QUERY = "[href*=fichadetalhe.asp]";

  private static final String PAGES_QUERY = "[href*=catalogo.asp?curpage]";

  private static final String MODELO_QUERY = "[href*=catalogomodelo.asp]";

  private static final String MARCA_QUERY = "[href*=catalogofabricante.asp?fabricante]";

  private static final String USER_AGENT_VALUE = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36";

  private static final String COOKIE_VALUE = "_ga=GA1.3.1873920051.1636380936; _gid=GA1.3.1414605661.1636380936; __gads=ID=ccbadb1333b37b43-22077fe24a7b000b:T=1636380938:RT=1636380938:S=ALNI_Mao5ySchknLH1rPoRJP58nDwdD7XA; aceitaCookie=yes; CNWmob=1; ASPSESSIONIDSERRSTCC=PGFOPJIAGDPENHIDEMFNHBKN; ASPSESSIONIDCUQQQQAB=IJDNJFFBMOHBBNGCAOKJDIMN; ASPVRQCMPECXDYCNSSW=6; _gat=1; ASPSESSIONIDCUQQQQAB=IPCNJFFBPJKEIKHKBJAJGLPN";

  @Override
  @SneakyThrows
  public List<Brand> extract() {
    //System.setProperty("http.agent", "");
    /*
    final Document marcaAsDoc = Jsoup.connect(MARCA_URL).get();
    return marcaAsDoc.select(MARCA_QUERY)
            .stream()
            .limit(1)
            .map(this::buildBrand)
            .collect(toList());
     */
    extractVehicle("fichadetalhe.asp?codigo=12871");
    return null;
  }

  @SneakyThrows
  private Brand buildBrand(final Element element) {
    final Brand brand = new Brand();
    brand.setName(element.text());
    brand.setModels(extractModels(element));
    return brand;
  }

  @SneakyThrows
  private List<Model> extractModels(final Element element) {
    final String href = element.attr(HREF);
    System.out.println(format("reading... %s", href));
    final Document modeloAsDoc = Jsoup.connect(format(CARROS_URL, href)).get();
    return modeloAsDoc.select(MODELO_QUERY)
            .stream()
            .limit(1)
            .map(this::buildModel)
            .collect(toList());
  }

  private Model buildModel(final Element element) {
    Model modelo = new Model();
    modelo.setName(element.text());
    modelo.setVehicles(extractVehicles(element));
    return modelo;
  }

  @SneakyThrows
  private List<Vehicle> extractVehicles(final Element element) {
    String href = element.attr(HREF).replaceAll("catalogomodelo.asp", "catalogo.asp");
    //String href = "catalogo.asp?fabricante=Hyundai&varnome=HB20";
    Document veiculoAsDoc;
    Element proximaPagina;
    final Set<String> urls = new HashSet<>();

    do {
      System.out.println(format("reading... %s", href));
      veiculoAsDoc = Jsoup.connect(format(CARROS_URL, href)).get();

      urls.addAll(veiculoAsDoc.select(FICHA_DETALHE_QUERY)
              .stream()
              .map(it -> it.attr(HREF))
              .collect(toList()));

      proximaPagina = veiculoAsDoc.select(PAGES_QUERY)
              .stream()
              .filter(page -> PROXIMA.equalsIgnoreCase(page.text()))
              .findFirst().orElse(null);

      if (nonNull(proximaPagina)) {
        href = proximaPagina.attr(HREF);
      }

    } while (nonNull(proximaPagina));

    return urls.stream()
            .limit(1)
            .map(this::extractVehicle)
            .collect(toList());
  }

  @SneakyThrows
  private Vehicle extractVehicle(String href) {
    /*final String codigoVeiculo = StringUtils.substring(href, StringUtils.indexOf(href, "=") + 1);
    href = String.format(DETALHE_URL, codigoVeiculo, codigoVeiculo);
    System.out.println(format("reading... %s", href));

    Document detalheAsDoc = Jsoup.connect(format(CARROS_URL, href)).get();

    String nome = detalheAsDoc.select("[color*=darkred]").get(3).text();

    Elements detalhe = detalheAsDoc.select("[bgcolor*=#F8F8FF]");

    String combustivel  = detalhe.get(0).text();
    String ano = detalhe.get(2).text();

    String potencia = detalhe.get(27).text();
    String torque =  detalhe.get(29).text();

    Vehicle vehicle = new Vehicle();*/
    //vehicle.setEngine();
    //vehicle.setName();
    //vehicle.setYear();
    //vehicle.setWeight();

    //Metric metric = new Metric();

    final String codigoVeiculo = StringUtils.substring(href, StringUtils.indexOf(href, "=") + 1);
    href = String.format(DETALHE_URL, codigoVeiculo, codigoVeiculo);
    System.out.println(format("reading... %s", href));

    final Document detalheAsDoc = Jsoup.connect(format(CARROS_URL, href)).get();
    final String nome = detalheAsDoc.select("[color*=darkred]").get(3).text();

    final Elements detalhe = detalheAsDoc.select("[bgcolor*=#F8F8FF]");

    String combustivel = detalhe.get(0).text();
    String ano = detalhe.get(2).text();

    Element element = detalhe.get(27);

    String s = extractImageContent(element);
    StringUtils.replace(s, "..\"", "").replace("\"", "/");

    String urlImage = String.format(CARROS_URL, s);

    String potencia = element.text();
    String torque = detalhe.get(29).text();

    //String s = extractTextFromImage();
    //System.out.println(StringUtils.trim(s));

    return null;
  }

  private String extractImageContent(final Element element) {
    return element.select("img")
            .stream()
            .map(it -> it.attr("src"))
            .filter(it -> it.contains("campoImagem"))
            .findFirst()
            .orElse(null);
  }

  private String extractTextFromImage() throws IOException, TesseractException {

    final URL url = new URL("https://www.carrosnaweb.com.br/campoImagem/imgValor4.asp");
    HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
    httpConn.addRequestProperty("Cookie", COOKIE_VALUE);
    httpConn.addRequestProperty("User-Agent", USER_AGENT_VALUE);

    final ITesseract instance = new Tesseract();
    instance.setTessVariable("user_defined_dpi", "150");
    instance.setDatapath("/usr/local/Cellar/tesseract/4.1.1/share/tessdata");
    instance.setPageSegMode(6);

    return StringUtils.trim(instance.doOCR(ImageIO.read(httpConn.getInputStream())));
  }
}
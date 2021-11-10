package br.com.danielwisky.extractor.service.vehicle.writer;

import static java.util.Objects.isNull;

import br.com.danielwisky.extractor.domains.vehicle.Brand;
import br.com.danielwisky.extractor.domains.vehicle.GroupModel;
import br.com.danielwisky.extractor.domains.vehicle.Model;
import br.com.danielwisky.extractor.domains.vehicle.Vehicle;
import br.com.danielwisky.extractor.repositories.GenericRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class WriteVehicleService {

  private static final String DATA = "data";

  private static final String FILES = "files";

  private static final String AUTO = "auto";

  private final GenericRepository genericRepository;

  private final ObjectMapper objectMapper;

  @SneakyThrows
  public void write() {
    final String root = String.format("%s/%s/%s", DATA, FILES, AUTO);
    createDirectory(Paths.get(root));

    final List<Brand> brands = genericRepository.findBrands();
    writeInFile(root,
            objectMapper.writeValueAsString(brands.stream().sorted(Comparator.comparing(Brand::getName)).map(MarcaResource::new).collect(Collectors.toList())));

    brands
            .stream()
            .forEach(brand -> writeBrand(root, brand));
  }

  @SneakyThrows
  private void writeBrand(final String root, final Brand brand) {
    createDirectory(Paths.get(String.format("%s/%s", root, brand.getId())));
    final String brandFolder = String.format("%s/%s/%s", root, brand.getId(), "modelos");
    createDirectory(Paths.get(brandFolder));

    final List<Model> models = brand.getModels();
    writeInFile(
            brandFolder,
            objectMapper.writeValueAsString(models.stream().sorted(Comparator.comparing(Model::getName)).map(ModeloResource::new).collect(Collectors.toList())));
    models
            .stream()
            .forEach(model -> writeModel(brandFolder, model));
  }

  @SneakyThrows
  private void writeModel(final String brandFolder, final Model model) {
    final String modelFolder = String.format("%s/%s", brandFolder, model.getId());
    createDirectory(Paths.get(modelFolder));

    final String versionFolder = String.format("%s/%s", modelFolder, "versoes");
    createDirectory(Paths.get(versionFolder));

    final Map<String, List<Vehicle>> mapModels = model.getVehicles()
            .stream()
            .collect(Collectors.groupingBy(Vehicle::getName));

    List<GroupModel> groupModels = mapModels.keySet()
            .stream()
            .map(this::saveAndFindGroupModel)
            .collect(Collectors.toList());

    writeInFile(
            versionFolder,
            objectMapper.writeValueAsString(groupModels
                    .stream()
                    .sorted(Comparator.comparing(GroupModel::getName))
                    .map(VersaoResource::new)
                    .collect(Collectors.toList())));

    mapModels.entrySet()
            .stream()
            .forEach(it -> {

              GroupModel groupModel = genericRepository.findGroupModelByName(it.getKey());
              final String groupFolder = String.format("%s/%s", versionFolder, groupModel.getId());
              final String vehicleFolder = String.format("%s/%s", groupFolder, "veiculos");
              createDirectory(Paths.get(groupFolder));
              createDirectory(Paths.get(vehicleFolder));

              writeVehicles(it, vehicleFolder);

            });
  }

  @SneakyThrows
  private void writeVehicles(Map.Entry<String, List<Vehicle>> mpVehicles, String vehicleFolder) {
    writeInFile(
            vehicleFolder,
            objectMapper.writeValueAsString(mpVehicles.getValue().stream()
                    .sorted(Comparator.comparing(Vehicle::getYear)).map(VehicleResource::new).collect(Collectors.toList())));
  }

  private GroupModel saveAndFindGroupModel(final String name) {
    GroupModel dbGroupModel = genericRepository.findGroupModelByName(name);
    if (isNull(dbGroupModel)) {
      dbGroupModel = new GroupModel();
      dbGroupModel.setName(name);
      dbGroupModel = (GroupModel) genericRepository.save(dbGroupModel);
    }
    return dbGroupModel;
  }

  @SneakyThrows
  private void writeInFile(final String path, final String json) {
    final FileWriter file = new FileWriter(String.format("%s/%s", path, "index.json"));
    file.write(json);
    file.flush();
    file.close();
  }

  private void createDirectory(final Path path) {
    try {
      Files.createDirectory(path);
    } catch (Exception e) {
      // nothing
    }
  }
}
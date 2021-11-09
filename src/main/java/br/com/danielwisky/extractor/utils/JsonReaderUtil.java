package br.com.danielwisky.extractor.utils;

import br.com.danielwisky.extractor.service.ModeloResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class JsonReaderUtil {

  public static <T> List<T> readJsonFromInputStream(final InputStream is, final Class<T> clazz) throws IOException {
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      ObjectMapper objectMapper = new ObjectMapper();
      CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, ModeloResponse.class);
      return objectMapper.readValue(jsonText, listType);
    } finally {
      is.close();
    }
  }

  public static <T> List<T> readJsonFromUrl(final String url, final Class<T> clazz) throws IOException {
    InputStream is = new URL(url).openStream();
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      ObjectMapper objectMapper = new ObjectMapper();
      CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
      return objectMapper.readValue(jsonText, listType);
    } finally {
      is.close();
    }
  }

  private static String readAll(final Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

}

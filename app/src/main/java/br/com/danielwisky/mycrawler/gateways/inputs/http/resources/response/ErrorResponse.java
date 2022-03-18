package br.com.danielwisky.mycrawler.gateways.inputs.http.resources.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

  private List<String> errors;
}

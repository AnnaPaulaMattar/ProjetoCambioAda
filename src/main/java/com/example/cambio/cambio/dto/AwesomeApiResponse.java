package com.example.cambio.cambio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AwesomeApiResponse {
    @JsonProperty("USDBRL")
    private CotacaoExterna usdbrl;

    @JsonProperty("EURBRL")
    private CotacaoExterna eurbrl;
}

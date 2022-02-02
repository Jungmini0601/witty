package com.daelim.witty.web.controller.dto.wittys;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class CreateWittyDTO {
    private String content;
    private List<String> tags;
}

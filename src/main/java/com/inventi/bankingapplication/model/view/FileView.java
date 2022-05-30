package com.inventi.bankingapplication.model.view;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FileView {

    byte[] bytes;
    String name;
    String extension;

}

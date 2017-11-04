package com.glumov.convertandsend.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Dmitriy Glumov on 04/11/2017.
 */
@Component
public class ConverterCSVtoJSON {
    private static final Logger LOG = LoggerFactory.getLogger(ConverterCSVtoJSON.class);

    public String convert(String file) throws IOException {

        LOG.info("Start reading file: {}", file);

        File input = new ClassPathResource(file).getFile();

        CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();

        CsvMapper csvMapper = new CsvMapper();

        List<Object> readAll = csvMapper.readerFor(Map.class).with(csvSchema).readValues(input).readAll();

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(readAll);

        LOG.info("Finish converting csv to json");
        LOG.trace("json: \n {}", json);
        return json;
    }
}

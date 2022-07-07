package com.example.ProjetoTabela.Mapper;

import lombok.val;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ModelMapperConfiguration {
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public ModelMapper modelMapper() {
        val mapper = new ModelMapper();

        mapper.getConfiguration()
                .setFieldMatchingEnabled(true) // habilitar correspondência de campos
                .setMatchingStrategy(MatchingStrategies.STRICT); // definir estratêgia de correspondência

        return mapper;
    }
}

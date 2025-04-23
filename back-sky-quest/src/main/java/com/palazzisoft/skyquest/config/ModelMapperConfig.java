package com.palazzisoft.skyquest.config;

import com.palazzisoft.skyquest.entity.User;
import com.palazzisoft.skyquest.model.UserDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(PRIVATE);

        initParameters(modelMapper);

        return modelMapper;
    }

    private void initParameters(ModelMapper modelMapper) {
        modelMapper.typeMap(User.class, UserDTO.class);
    }

}

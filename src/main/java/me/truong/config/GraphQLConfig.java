package me.truong.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import graphql.scalars.ExtendedScalars;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import org.springframework.web.multipart.MultipartFile;

@Configuration
public class GraphQLConfig {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
                .scalar(ExtendedScalars.Date)
                .scalar(uploadScalar());
    }
    
    private GraphQLScalarType uploadScalar() {
        return GraphQLScalarType.newScalar()
                .name("Upload")
                .description("A file upload scalar")
                .coercing(new Coercing<MultipartFile, String>() {
                    @Override
                    public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
                        if (dataFetcherResult instanceof MultipartFile) {
                            return ((MultipartFile) dataFetcherResult).getOriginalFilename();
                        }
                        return null;
                    }

                    @Override
                    public MultipartFile parseValue(Object input) throws CoercingParseValueException {
                        if (input instanceof MultipartFile) {
                            return (MultipartFile) input;
                        }
                        return null;
                    }

                    @Override
                    public MultipartFile parseLiteral(Object input) throws CoercingParseLiteralException {
                        return null; // File uploads are not supported in literal form
                    }
                })
                .build();
    }
}
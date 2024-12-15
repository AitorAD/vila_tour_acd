package com.example.vila_tour.domain.deserializer;

import com.example.vila_tour.domain.Article;
import com.example.vila_tour.domain.Festival;
import com.example.vila_tour.domain.Place;
import com.example.vila_tour.domain.Recipe;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class ArticleDeserializer extends JsonDeserializer<Article> {
    @Override
    public Article deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);

        // Lógica para determinar el tipo de la subclase
        if (node.has("startDate") && node.has("endDate")) {
            return parser.getCodec().treeToValue(node, Festival.class);
        } else if (node.has("ingredients")) {
            return parser.getCodec().treeToValue(node, Recipe.class);
        } else if (node.has("categoryPlace")) {
            return parser.getCodec().treeToValue(node, Place.class);
        }

        throw new IllegalArgumentException("No matching type for Article");
    }
}
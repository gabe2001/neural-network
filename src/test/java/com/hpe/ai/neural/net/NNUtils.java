// (c) Copyright 2024 Hewlett Packard Enterprise Development LP
package com.hpe.ai.neural.net;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * {@code com.hpe.ai.neural.net}
 *
 * @author Gabriel Inäbnit - 2024-04-01
 */
public final class NNUtils
{

   private static final ObjectMapper mapper = new ObjectMapper();

   private NNUtils()
   {
      throw new UnsupportedOperationException("Utility class.");
   }

   public static JsonNode jsonFromFile(final String filename)
           throws URISyntaxException, IOException
   {
      return mapper.readTree(Objects.requireNonNull(NeuralNetworkTest.class.getResource(filename)).toURI().toURL());
   }

   public static List<Double> getNumbers(final JsonNode jsonNode)
   {
      final List<Double> numbers = new ArrayList<>();
      if (jsonNode.isArray())
      {
         for (final JsonNode numberNode : jsonNode)
         {
            numbers.add(numberNode.asDouble());
         }
      }
      return numbers;
   }

   public static List<List<Double>> getInputs(final JsonNode jsonNode)
   {
      final List<List<Double>> inputs = new ArrayList<>();
      if (jsonNode.isArray())
      {
         for (final JsonNode tupleNode : jsonNode)
         {
            final List<Double> tuple = new ArrayList<>();
            if (tupleNode.isArray())
            {
               for (final JsonNode testNumber : tupleNode)
               {
                  tuple.add(testNumber.asDouble());
               }
            }
            inputs.add(tuple);
         }
      }
      return inputs;
   }


}

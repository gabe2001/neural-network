// (c) Copyright 2024 Hewlett Packard Enterprise Development LP
package com.hpe.ai.neural.net;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Gabriel Inäbnit - 2024-02-29
 */
public class NeuralNetworkTest
{

   private final ObjectMapper mapper = new ObjectMapper();

   @Test
   public void test() throws URISyntaxException, IOException
   {
      final JsonNode jsonNode = mapper.readTree(
              Objects.requireNonNull(NeuralNetworkTest.class.getResource("/data.json")).toURI().toURL());
      final NeuralNetwork nn = new NeuralNetwork();
      System.out.println("Untrained neural network:");
      final List<List<Double>> inputs = getTuples(jsonNode.get("test_inputs"));
      for (final List<Double> input : inputs)
      {
         System.out.println(String.format("Input: %.1f + %.1f = %.1f", input.get(0), input.get(1), nn.predict(input)));
      }
      System.out.println("Training neural network...");
      nn.train(getTuples(jsonNode.get("training_inputs")), getNumbers(jsonNode.get("training_outputs")), 10000);
      System.out.println("Trained neural network:");
      for (final List<Double> input : inputs)
      {
         System.out.println(String.format("Input: %.1f + %.1f = %.1f", input.get(0), input.get(1), nn.predict(input)));
      }

   }

   private List<Double> getNumbers(final JsonNode jsonNode)
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

   private List<List<Double>> getTuples(final JsonNode jsonNode)
   {
      final List<List<Double>> tuples = new ArrayList<>();
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
            tuples.add(tuple);
         }
      }
      return tuples;
   }

}

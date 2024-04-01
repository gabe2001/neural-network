// (c) Copyright 2024 Hewlett Packard Enterprise Development LP
package com.hpe.ai.neural.net;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Gabriel Inäbnit - 2024-02-29
 */
public class NeuralNetworkTest
{

   private final ObjectMapper mapper = new ObjectMapper();

   @Test
   public void test_simple_neural_network_two_inputs() throws URISyntaxException, IOException
   {
      final JsonNode jsonNode = mapper.readTree(
              Objects.requireNonNull(NeuralNetworkTest.class.getResource("/data_two_inputs.json")).toURI().toURL());
      final NeuralNetwork nn = NeuralNetwork.getInstance();
      nn.setupNeuralNetwork(List.of(2));
      System.out.println("Untrained neural network:");
      final List<List<Double>> inputs = getInputs(jsonNode.get("test_inputs"));
      for (final List<Double> input : inputs)
      {
         System.out.printf("Input: %.1f + %.1f = %.2f%n", input.get(0), input.get(1), nn.predict(input));
      }
      System.out.print("Training neural network... ");
      final long start = System.currentTimeMillis();
      nn.train(getInputs(jsonNode.get("training_inputs")), getNumbers(jsonNode.get("training_outputs")), 100000);
      final long end = System.currentTimeMillis();
      System.out.printf("done in %d ms%n", end - start);
      System.out.println("Trained neural network:");
      for (final List<Double> input : inputs)
      {
         System.out.printf("Input: %.1f + %.1f = %.2f%n", input.get(0), input.get(1), nn.predict(input));
      }

   }

   @Test
   public void test_simple_neural_network_three_inputs() throws URISyntaxException, IOException
   {
      final JsonNode jsonNode = mapper.readTree(
              Objects.requireNonNull(NeuralNetworkTest.class.getResource("/data_three_inputs_generated.json")).toURI()
                      .toURL());
      final NeuralNetwork nn = NeuralNetwork.getInstance();
      nn.setupNeuralNetwork(List.of(3));
      System.out.println("Untrained neural network:");
      final List<List<Double>> inputs = getInputs(jsonNode.get("test_inputs"));
      for (final List<Double> input : inputs)
      {
         System.out.printf("Input: %.1f + %.1f + %.1f = %.2f%n", input.get(0), input.get(1), input.get(2),
                 nn.predict(input));
      }
      System.out.print("Training neural network... ");
      final long start = System.currentTimeMillis();
      nn.train(getInputs(jsonNode.get("training_inputs")), getNumbers(jsonNode.get("training_outputs")),
              100000);
      final long end = System.currentTimeMillis();
      System.out.printf("done in %d ms%n", end - start);
      System.out.println("Trained neural network:");
      for (final List<Double> input : inputs)
      {
         System.out.printf("Input: %.1f + %.1f + %.1f = %.2f%n", input.get(0), input.get(1), input.get(2),
                 nn.predict(input));
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

   private List<List<Double>> getInputs(final JsonNode jsonNode)
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

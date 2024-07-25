// (c) Copyright 2024 Hewlett Packard Enterprise Development LP
package com.hpe.ai.neural.net;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static com.hpe.ai.neural.net.NNUtils.getInputs;
import static com.hpe.ai.neural.net.NNUtils.getNumbers;
import static com.hpe.ai.neural.net.NNUtils.jsonFromFile;

/**
 * @author Gabriel Inäbnit - 2024-02-29
 */
public class NeuralNetworkTest
{

   private static final NeuralNetwork nn = NeuralNetwork.getInstance();

   @After
   public void tearDown()
   {
      nn.destroy();
   }

   @Test
   public void test_simple_neural_network_two_inputs() throws URISyntaxException, IOException
   {
      final JsonNode jsonNode = jsonFromFile("/data_two_inputs.json");
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
      final JsonNode jsonNode = jsonFromFile("/data_three_inputs_generated.json");
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
      nn.train(getInputs(jsonNode.get("training_inputs")), getNumbers(jsonNode.get("training_outputs")), 100000);
      final long end = System.currentTimeMillis();
      System.out.printf("done in %d ms%n", end - start);
      System.out.println("Trained neural network:");
      for (final List<Double> input : inputs)
      {
         System.out.printf("Input: %.1f + %.1f + %.1f = %.2f%n", input.get(0), input.get(1), input.get(2),
                 nn.predict(input));
      }
   }

   @Test
   public void test_simple_neural_network_two_layers() throws URISyntaxException, IOException
   {
      final JsonNode jsonNode = jsonFromFile("/data_two_inputs.json");
      nn.setupNeuralNetwork(List.of(2, 3));
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

}

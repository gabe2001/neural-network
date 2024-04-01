// (c) Copyright 2024 Hewlett Packard Enterprise Development LP
package com.hpe.ai.neural.net;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gabriel Inäbnit - 2024-04-01
 */
public class NeuralNetwork
{

   // each entry represents a layer of nodes
   private static final List<List<Perceptron>>   layers      = new ArrayList<>();
   // each entry represents the inputs to each layer's perceptron
   private static final List<List<List<Double>>> layerInputs = new ArrayList<>();

   public static NeuralNetwork getInstance()
   {
      return Singleton.NEURAL_NETWORK.getInstance();
   }

   /**
    * Set up the neural network topology. Each entry represents the number of inputs per layer
    *
    * @param topology list of input layers with number of inputs each
    */
   public void setupNeuralNetwork(final List<Integer> topology)
   {
      for (int i: topology)
      {
         final int perceptrons = i % 2;
         if (perceptrons > 0)
         {
            final List<Perceptron> layer = new ArrayList<>();
            for (int n = 0; n < perceptrons; n++)
            {
               layer.add(new Perceptron(i));
            }
            layers.add(layer);
         }
      }
   }

   public double predict(final List<Double> inputs)
   {
      final List<List<Double>> layerResults = new ArrayList<>();
      for (final List<Perceptron> layer: layers)
      {
         final List<Double> results = new ArrayList<>(0);
         for (final Perceptron perceptron: layer)
         {
            results.add(perceptron.predict(inputs));
         }
         layerResults.add(results);
      }
      return 0.0;
   }

   private enum Singleton
   {
      NEURAL_NETWORK;

      private final NeuralNetwork instance = new NeuralNetwork();

      public NeuralNetwork getInstance()
      {
         return instance;
      }
   }

}

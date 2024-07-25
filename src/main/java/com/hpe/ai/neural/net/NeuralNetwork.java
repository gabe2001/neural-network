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

   private int numberOfLayers;

   public static NeuralNetwork getInstance()
   {
      return Singleton.NEURAL_NETWORK.getInstance();
   }

   public void destroy()
   {
      layers.clear();
      layerInputs.clear();
   }

   /**
    * Set up the neural network topology. Each entry represents the number of inputs per layer
    *
    * @param topology list of number of inputs for each layer
    */
   public void setupNeuralNetwork(final List<Integer> topology)
   {
      numberOfLayers = topology.size();
      int currentLayer = 1;
      for (int i : topology)
      {
         int perceptrons; // equals number of outputs of the next layer
         if (currentLayer < numberOfLayers)
         {
            perceptrons = topology.get(i); // we're looking ahead
         }
         else
         {
            perceptrons = 1;
         }
         if (perceptrons > 0)
         {
            final List<Perceptron> layer = new ArrayList<>();
            for (int n = 0; n < perceptrons; n++)
            {
               layer.add(new Perceptron(i));
            }
            layers.add(layer);
         }
         currentLayer++;
      }
   }

   public double predict(final List<Double> inputs)
   {
      final List<List<Double>> layerResults = new ArrayList<>();
      for (final List<Perceptron> layer : layers)
      {
         final List<Double> results = new ArrayList<>();
         for (final Perceptron perceptron : layer)
         {
            results.add(perceptron.predict(inputs));
         }
         layerResults.add(results);
      }
      if (numberOfLayers > 1)
      {
         return 0.0; // todo
      }
      else
      {
         return layerResults.getFirst().getFirst();
      }
   }

   /**
    * how are we training a network with multiple layers? it has to be done backwards: expected output back through
    * the layers all the way to the inputs
    *
    * @param inputs  training data
    * @param outputs expected results
    * @param epochs  number of repetitions
    */
   public void train(final List<List<Double>> inputs, final List<Double> outputs, final int epochs)
   {
      for (final List<Perceptron> layer : layers)
      {
         for (final Perceptron perceptron : layer)
         {
            if (numberOfLayers > 1)
            {
               // todo
            }
            else
            {
               perceptron.train(inputs, outputs, epochs);
            }
         }
      }
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

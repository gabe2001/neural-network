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
   private final List<List<Perceptron>>   theNetwork  = new ArrayList<>();
   // each entry represents the inputs to each layer's perceptron
   private final List<List<List<Double>>> layerInputs = new ArrayList<>();

   private int numberOfLayers;

   public static NeuralNetwork getInstance()
   {
      return Singleton.NEURAL_NETWORK.getInstance();
   }

   public void destroy()
   {
      theNetwork.clear();
      layerInputs.clear();
   }

   /**
    * Set up the neural network topology. Each entry represents the number of inputs per layer
    * <p>
    * Reconsider this approach:
    * <ul>
    *    <li>the number of inputs for each layer is not necessarily the number of perceptrons in the next layer</li>
    *    <li>the number of perceptrons in the last layer is always 1</li>
    *    <li>the number of perceptrons in the first layer is always the number of inputs</li>
    * </ul>
    * <p>
    * Say we've got 1 hidden layer with 3 perceptions, 2 inputs and 1 output (implied). The topology would be [2, 3].
    * <p>
    * [ "#inputs", "#perceptrons and #inputs", "#perceptrons and #inputs", ... ]
    * <ol>
    *    <li>the 2 inputs have to be fed 3 times to each perceptron</li>
    *    <li>the results have to be collected from these 3 predictions and fed into the 1 output perceptron</li>
    * </ol>
    *
    * @param topology list of number of inputs for each layer
    */
   public void setupNeuralNetwork(final List<Integer> topology)
   {
      numberOfLayers = topology.size();
      int currentLayer = 1; // notice, we're not starting at 0!
      for (int numberOfInputsOfCurrentLayer : topology)
      {
         int perceptrons;
         if (currentLayer < numberOfLayers)
         {
            perceptrons = topology.get(currentLayer); // remember, we're looking ahead!
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
               layer.add(new Perceptron(numberOfInputsOfCurrentLayer));
            }
            theNetwork.add(layer);
         }
         currentLayer++;
      }
   }

   /**
    * In a multi-layer network, the prediction has to be done layer by layer. The output of one layer is the input of
    * the next layer.
    * <p>
    * The predictions have to be collected into a list and fed into the next layer.
    * <p>
    * If you don't see it, look at the pictures!
    *
    * @param inputs list of doubles to predict
    * @return the prediction
    */
   public double predict(final List<Double> inputs)
   {
      // TODO: results array needs to be collected into a list
      //       is the List<List<Double>> the right data structure?
      final List<List<Double>> layerResults = new ArrayList<>();
//      int layerIndex = 1;
      for (final List<Perceptron> layer : theNetwork)
      {
//         if (layerIndex < numberOfLayers)
//         {
            // do I need to collect the layerResults?
            // get next layer's perceptrons
            final List<Double> results = new ArrayList<>();
            for (final Perceptron perceptron : layer)
            {
               results.add(perceptron.predict(inputs));
            }
            layerResults.add(results);
            // TODO: feed the results into the next layer

//         }
//         else
//         {
//            // TODO: get the last layer's perceptron
//         }
//         layerIndex++;
      }
      if (numberOfLayers > 1)
      {
         return 0.0; // TODO: implement this...
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
      for (final List<Perceptron> layer : theNetwork)
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

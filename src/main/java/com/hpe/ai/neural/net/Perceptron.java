// (c) Copyright 2024 Hewlett Packard Enterprise Development LP
package com.hpe.ai.neural.net;

import java.util.ArrayList;
import java.util.List;

/**
 * Converted to java from <a href="https://dev.to/farshed/building-a-neural-network-in-rust-from-scratch-5bm1">Building a Neural Network in Rust (From Scratch)</a>
 *
 * @author Gabriel Inäbnit - 2024-02-28
 */
public class Perceptron
{

   private final List<Double> weights = new ArrayList<>();
   private       double       bias;

   public Perceptron(final int inputCount)
   {
      for (int i = 0; i < inputCount; i++)
      {
         weights.add(Math.random());
      }
      bias = Math.random();
   }

   public double predict(final List<Double> inputs)
   {
      double sum = bias;
      for (int i = 0; i < inputs.size(); i++)
      {
         sum += inputs.get(i) * weights.get(i);
      }
      return sigmoid(sum);
   }

   public void train(final List<List<Double>> inputs, final List<Double> outputs, final int epochs)
   {
      double learningRate = 0.1;
      for (int n = 0; n < epochs; n++)
      {
         int i = 0;
         for (final List<Double> input : inputs)
         {
            double output = predict(input);
            double error = outputs.get(i) - output;
            double delta = derivative(output);
            int j = 0;
            for (double weight : weights)
            {
               weight += learningRate * error * input.get(j) * delta;
               weights.set(j, weight);
               j++;
            }
            bias += learningRate * error * delta;
            i++;
         }
      }
   }

   static double sigmoid(final double x)
   {
      return 1.0 / (1.0 + Math.exp(-x));
   }

   static double derivative(final double x)
   {
      return x * (1.0 - x);
   }

}

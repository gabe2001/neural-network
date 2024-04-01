// (c) Copyright 2024 Hewlett Packard Enterprise Development LP
package com.hpe.ai.neural.net;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * {@code com.hpe.ai.neural.net}
 *
 * @author Gabriel Inäbnit - 2024-04-01
 */
public class GenerateJsonData
{

   @Test
   public void print_json_data()
   {

      final List<List<Double>> inputsList = new ArrayList<>();
      final List<Double> results = new ArrayList<>();

      for (int n = 0; n < 40; n++)
      {
         final List<Double> inputs = new ArrayList<>();
         for (int i = 0; i < 3; i++)
         {
            inputs.add(Math.floor(10.0 * Math.random()) / 10.0);
         }
         // normalize doubles so that their sum is <= 1.0
         final double sum = inputs.stream().reduce(0.0, Double::sum);
         if (sum > 1.0)
         {
            inputsList.add(inputs.stream().map(d -> Math.floor(d / sum * 10.0) / 10.0).toList());
         }
         else
         {
            inputsList.add(inputs);
         }
         results.add(Math.floor(inputsList.get(n).stream().reduce(0.0, Double::sum) * 10.0) / 10.0 );
      }
      for (final List<Double> inputs : inputsList)
      {
         System.out.println("[" + String.join(",", inputs.stream().map(String::valueOf).toList()) + "],");
      }
      for (final double result : results)
      {
         System.out.println(result + ",");
      }

      // generate ordered input
      for (int x = 0; x < 10; x++)
      {
         for (int y = 0; y < 10; y++)
         {
            for (int z = 0; z < 10; z++)
            {
               if ( x + y + z <= 10)
               {
                  System.out.printf("[ %.1f, %.1f, %.1f ],\n", x / 10.0, y / 10.0, z / 10.0);
               }
            }
         }
      }
   }


}

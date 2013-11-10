collaborative-filtering
=======================
Project 3: Machine Learning for CS 440.

Objective
---------
Implement and evaluate collaborative filtering by using the MovieLens movie
rating data (about 100.000 sets of ratings).

The actual implementation is just a few lines of code, but you must explore
parameters to get interesting results.

Baselines
---------
The baselines are simple algorithms you can understand analytically; they are
meant to be compared to the more sophisticated collaborative filtering
techniques. Use them to determine whether or not your algorithm is an
improvement over a simpler one.

Note that you don't need to beat the baselines to get full credit on the
assignment.

Programming
-----------
There is only one method to fill in, and that is the `predict` function, found
in `RatingDictionary.java`:

    public Rating predict(String rater, String item, Method method,
                          int numItemNeighbors, int numRaterNeighbors) {
        // Your code here, e.g.
        double score = this.defaultScore();
        return new Rating(rater, item, score);
    }

Implement it to make a prediction about what score `rater` would give to `item`,
based on different analytical methods and the nearest neighbors of `rater` in
the sample data space.

Extra Credit
------------
Combine both user-based filtering and item-based filtering together to get even
more accurate results.

Baselines
---------
The idea of a baseline is important in machine learning (or in any other
engineering discipline where you might not be certain that a change that you
have made would lead to an improvement). A baseline is a simple algorithm whose
performance you understand analytically and whose results you can compare to a
more sophisticated method whose behavior is more complicated. A baseline is a
way to gauge whether you have made an improvement and, sometimes more
importantly, whether you have done the right experiments and made the right
measurements so that you could recognize any possible improvement if it realy
was present.

So, each of the baselines is a simple rule that says how to predict a new
rating based on ratings that you have in your collection.

- The ITEM_BASELINE says: guess that a new rating for an item will be about
  equal to the average rating that that item has received in the past.

- The RATER_BASELINE says: guess that a new rating for an item will be about
  equal to the average rating that the user you're looking at has given in the
  past.

- The MIXED_BASELINE says: make a good guess about what rating you'd expect the
  item to receive and what rating you'd expect the rater to give, and split the
  difference between them.

You can implement each of these in just a few lines of code. You just need to
understand how the RatingDictionary object stores ratings by item and by rater
in hash tables called itemData and raterData; this allows you to look up
ratings information of either type by name (e.g., itemData.get(movieName) or
raterData.get(raterName)). These functions return objects of type RatingTable
which have a variety of methods defined, including a method getAverage() which
returns the average rating in the table.

The RatingDictionary itself already has a method called
geometricMeanBaseline(rater, item) that provides a good mixed baseline. I have
provided this for you because it is an open-ended problem (that I actually got
badly wrong a few times along the way). The trick is that you not only need to
figure out how to balance the weights for rater and item together in a sensible
way, but then you have to figure out how to set the weights (so balanced) so
that they actually match your training data. (You can maybe convince yourself
that unless you're very careful, when you predict new data based on combining
old averages, the averages from the predictions might not match those averages
you started from. For those of you who are interested, you can trace through
the assignment and see how the weights and estimation works - the programs for
creating new test data, which I will describe later, also show how I eventually
managed to debug the method I chose.)

The baselines are actually the most important ingredient in building and
understanding a recommendation system -- something that we have really only
properly come to understand and appreciate in the last few years (since the
last round of the Netflix prize competition). In this assignment, you have to
work carefully to beat the mixed baseline.

Samples
-------
### Sample.data
Contains data that makes the baselines perform poorly; the similarity
measures should achieve good ratings fairly quickly.

- 1000 ratings
- Estimated item baseline RMSE: 0.89275853
- Estimated rater baseline RMSE: 1.143639650
- Estimated mixed baseline RMSE: 0.958249

### Sample2.data
Given enough noise, the similarity measures will struggle to perform well.

- 1000 ratings for file Sample2.data
- Estimated rater baseline RMSE: 1.1907518231273422
- Estimated item baseline RMSE: 1.1169338056511784
- Estimated mixed baseline RMSE: 1.0

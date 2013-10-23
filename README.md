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
There is only one method to fill in, and that is the `predict` function:

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

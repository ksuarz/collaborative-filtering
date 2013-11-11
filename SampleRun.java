/**
 * This program illustrates the general method of
 * initializing a RatingDictionary from sample
 * data and making predictions.
 * 
 * @author Tarek El-Gaaly and Matthew Stone
 */
public class SampleRun {

    /**
     * Inventory of parameters for controlling calculations
     */
    
    /** How many crossvalidation folds to consider */
    static int numCrossFolds = 10;
    
    /** Which crossvalidation fold to use as a sample */
    static int sampleFold = 0;
    
    /**
     * Should we subtract off a baseline before assessing
     * similarity (and then add back in the baseline
     * after making predictions)?
     */
    static boolean predictAgainstBaseline = true;

    /** How to calculate similarities between items */
    static RatingTable.SimilarityMeasure itemSimilarityMeasure =
        RatingTable.SimilarityMeasure.EUCLIDEAN;

    /** How to calculate similarities between raters */
    static RatingTable.SimilarityMeasure raterSimilarityMeasure =
        RatingTable.SimilarityMeasure.PEARSON;
    
    /** 
     * How many ratings must two item records have in common
     * before we are willing to treat them as similar
     */
    static int minItemOverlapForSimilarity = 12;

    /** 
     * How many ratings must two rater records have in common
     * before we are willing to treat them as similar
     */
    static int minRaterOverlapForSimilarity = 10;
    
    /**
     * What is the maximum number of neighbors to consider
     * in building similarity tables.
     * Determines the overall memory requirements for the
     * program so be careful about setting this too big.
     */
    static int maxNeighbors = 120;
    
    /** How many neighbors should be considered in making predictions */
    static int numItemNeighbors = 5;

    /** How many neighbors should be considered in making predictions */
    static int numRaterNeighbors = 7;
    
    /** What method should be used for making predictions */
    static RatingDictionary.Method predictionMethod =
        RatingDictionary.Method.RATER_SIMILARITY;
    
    /** Whether to display each prediction made */
    static boolean printPredictions = false;
    
    /**
     * Main method
     * @param args args[0] should be the prefix of the movie lens data
     */
    public static void main(String[] args) {
        
        // Get the prefix for movielens data.
        String filePrefix = args[0];
        
        // Load training data from the movielens dataset.
        RatingDictionary rd = RatingDictionary.fromMovieLensItems(filePrefix + ".item");
        RatingTable data = rd.tabulateMovieLensData(filePrefix + ".data");
        rd.addTrainingData(data, sampleFold, numCrossFolds);
        
        // Process the data to build models.
        if (predictAgainstBaseline)
            rd.subtractBaseline();
        rd.computeItemSimilarities(itemSimilarityMeasure, 
				   minItemOverlapForSimilarity, 
				   maxNeighbors);
        rd.computeRaterSimilarities(raterSimilarityMeasure, 
				    minRaterOverlapForSimilarity, 
				    maxNeighbors);
        // Predict on test data.
        RatingTable p = 
	    rd.predictTestData(data, 
			       predictionMethod, numItemNeighbors, numRaterNeighbors,
			       sampleFold, numCrossFolds,
			       printPredictions);
        if (predictAgainstBaseline)
            p.addBaseline(rd);
        
        // Print out results.
        System.out.println("RMSE of predictions against actual ratings: " + p.getDistance(data));

    }

}

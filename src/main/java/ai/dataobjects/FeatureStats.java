package ai.dataobjects;

import java.util.HashMap;
import java.util.Map;

public class FeatureStats {
    public int n;
    
    public Map<String, Map<String, Integer>> featureCategoryJointCount;   
    public Map<String, Integer> categoryCounts;
    public FeatureStats() {
        n = 0;
        featureCategoryJointCount = new HashMap<String, Map<String, Integer>>();
        categoryCounts = new HashMap<String, Integer>();
    }
}

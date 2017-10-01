package airbnb.knn;
/**
 * Created by Σταυρίνα on 1/10/2017.
 */

import airbnb.model.RenterEntity;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

public class Knn {
	public static final String PATH_TO_DATA_FILE = "C:\\ProjectsDI\\src\\o.txt";
	public static  int NUM_ATTRS ;
	public static  int K =5;

	public static final int DISTANCE_INDEX = 1;
	public static final int EXPIRATION_INDEX = 2;
	public static final int NUM_RUNS = 1;
	public static double averageDistance = 0;
	
	public ArrayList<Neighbor> doKnn(Integer mode,RenterEntity renterEntity,ArrayList<RenterEntity> renterList,int apart_sum) {
		NUM_ATTRS=apart_sum+1;


		ArrayList<Instance> instances = null;
		ArrayList<Neighbor> distances = null;
		ArrayList<Neighbor> neighbors = null;
		Instance classificationInstance = null;

		int numRuns = 0, truePositives = 0,falsePositives = 0;
		double precision = 0;
		
		falsePositives = 1;
	
		int[] a= {1,1,1};
		int[] b= {1,1,1};
		 System.out.println("Cosine "+cosineSimilarity(a, b));
		VectorMaker vectorMaker=new VectorMaker();
		instances=vectorMaker.buildVectors(mode,renterList,apart_sum);
		ArrayList<Integer> apids=null;
				
		do {
			System.out.println("Megethos"+instances.size());
			if(instances.size()<K)
				K=instances.size()-1;
			classificationInstance = extractIndividualInstance(instances,renterEntity.getUsersUsername());
			
			distances = calculateDistances(instances, classificationInstance);
			neighbors = getNearestNeighbors(distances);
			
			System.out.println("Gathering " + K + " nearest neighbors to:");
			printClassificationInstance(classificationInstance);
			
			printNeighbors(neighbors);

		//	if(mode==1)
		//		apids=findRecommendations(classificationInstance,neighbors);
	
			numRuns++;
			
			instances.add(classificationInstance);
		} while(numRuns < NUM_RUNS);
		
		precision = ((double)(truePositives / (double)(truePositives + falsePositives)));
	//	recall = ((double)(truePositives / (double)(truePositives + falseNegatives)));

		
		System.out.println("Precision: " + precision);
		System.out.println("Average distance: " + (double)(averageDistance / (double)(NUM_RUNS * K)));
		return  neighbors;
	}
	
	public static Instance extractIndividualInstance(ArrayList<Instance> instances,String username) {
		int random=0;
		int i=0;
		for(Instance single :instances)
		{
			if(single.getUuid().equals(username))
				random=i;
			i++;
		}
		Instance singleInstance = instances.get(random);
		instances.remove(random);
		return singleInstance;
	}
	
	public static void printClassificationInstance(Instance classificationInstance) {
		System.out.println(" Xristis "+classificationInstance.getUuid());
	}
	
	public static void printNeighbors(ArrayList<Neighbor> neighbors) {
		int i = 0;
		for(Neighbor neighbor : neighbors) {
			System.out.println("\nNeighbor " + (i + 1) + ", distance: " + neighbor.getDistance()+" name "+neighbor.getInstance().getUuid());
			i++;
		}
	}
	
	
	public static ArrayList<Neighbor> getNearestNeighbors(ArrayList<Neighbor> distances) {
		ArrayList<Neighbor> neighbors = new ArrayList<Neighbor>();
		
		for(int i = 0; i < K; i++) {
			averageDistance += distances.get(i).getDistance();
			neighbors.add(distances.get(i));
		}
		
		return neighbors;
	}
	
	public static double cosineSimilarity(int[] vectorA, int[] vectorB) {
	    double dotProduct = 0.0;
	    double normA = 0.0;
	    double normB = 0.0;
	    for (int i = 0; i < vectorA.length; i++) {
	        dotProduct += vectorA[i] * vectorB[i];
	        normA += Math.pow(vectorA[i], 2);
	        normB += Math.pow(vectorB[i], 2);
	    }   
	    return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
	}
	
	public static ArrayList<Neighbor> calculateDistances(ArrayList<Instance> instances, Instance singleInstance) {
		ArrayList<Neighbor> distances = new ArrayList<Neighbor>();
		Neighbor neighbor = null;
		int distance = 0;
		
		for(int i = 0; i < instances.size(); i++) {
			Instance instance = instances.get(i);
			distance = 0;
			neighbor = new Neighbor();
			
			// for each feature, go through and calculate the "distance"
			int j=0;
			for(Feature f : instance.getAttributes()) {
					Feature singleInstanceCat = (Feature)singleInstance.getAttributes().get(j);
					distance += Math.pow((double)f.getRate() - singleInstanceCat.getRate(),2);
					j++;
			}
			neighbor.setDistance(distance);
			neighbor.setInstance(instance);
			
			distances.add(neighbor);
			
		}
		
		for (int i = 0; i < distances.size(); i++) {
			for (int j = 0; j < distances.size() - i - 1; j++) {
				if(distances.get(j).getDistance() > distances.get(j + 1).getDistance()) {
					Neighbor tempNeighbor = distances.get(j);
					distances.set(j, distances.get(j + 1));
					distances.set(j + 1, tempNeighbor);
				}
			}
		}
		
		return distances;
	}



	ArrayList<Integer> findRecommendations(Instance renterInstance,ArrayList<Neighbor> neighbors)
	{
		int i = 0;
		int nindex;
		ArrayList <Integer> apids=new ArrayList<Integer>(0);
		for(Neighbor neighbor : neighbors) {
			nindex=neighbor.getInstance().getMaxIndex();
			if(renterInstance.getAttributes().get(nindex).getRate() ==0)
				apids.add(nindex+1);		//bc index starts from 0 where ids start from 1
			else
			{
				nindex=neighbor.getInstance().getSecondMaxIndex();
				if(renterInstance.getAttributes().get(nindex).getRate() ==0)
					apids.add(nindex+1);		//bc index starts from 0 where ids start from 1
				//else no recommendation from that neighbor
			}
		}
		return apids;

	}


}

package com.tk.mapoi.cluster;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tk.mapoi.database.ClusterInfoDB;
import com.tk.mapoi.database.LocationDB;
import com.tk.mapoi.globals.Constants;
import com.tk.mapoi.globals.GlobalParams;
import com.tk.mapoi.helper.Utils;
import com.tk.mapoi.labels.GetLabels;

/**
 * The Class DBScan. Clusters applying DBScan algorithm Sends them for labeling
 * Returns labelled cluster
 * 
 * @author Team SNAPS (Shashank,Nandita,Ankit,Pooja,Shreyas)
 */
public class DBScan {

	/** Neighbours List of a point. */
	private List<LocationDB> neighbours;

	/** The Visit list. */
	private List<LocationDB> visitList = new ArrayList<LocationDB>();

	/** The Unvisited list. */
	private List<LocationDB> unvisited = new ArrayList<LocationDB>();

	/** Cluster list. */
	private List<ClusterInfoDB> cluster = new ArrayList<ClusterInfoDB>();

	/** The cluster name id. */
	private int clusterNameId = 0;

	/** The loc list size. */
	private long locListSize = 0L;

	/** The centroid lat. */
	private double centroidLat = 0.0D;

	/** The centroid long. */
	private double centroidLong = 0.0D;

	/**
	 * Gets the centroid latitude and longitude.
	 * 
	 */
	public void getCentroid() {
		centroidLat = 0;
		centroidLong = 0;
		for (LocationDB neighbourPt : neighbours) {
			centroidLat += Double.parseDouble(neighbourPt.getLatitude());
			centroidLong += Double.parseDouble(neighbourPt.getLongitude());
		}
		centroidLat = centroidLat / neighbours.size();
		centroidLong = centroidLong / neighbours.size();
	}

	/**
	 * Get neighbourhood points of location loc based on Constants.EPSILON.
	 * 
	 * @param loc
	 *            - Location
	 * @return the neighbours to loc
	 */

	public List<LocationDB> getNeighbours(LocationDB loc) {
		List<LocationDB> neigh = new ArrayList<LocationDB>();
		Iterator<LocationDB> points = unvisited.iterator();
		while (points.hasNext()) {
			LocationDB locNext = points.next();
			// If Distance between 2 locations less than Constants.EPSILON
			// Add to neighbours list
			if (Utils.getDistance(loc, locNext) <= Constants.EPSILON) {
				neigh.add(locNext);
			}
		}
		return neigh;
	}

	/**
	 * Add location to visited List of locations.
	 * 
	 * @param loc
	 *            - Location to add
	 * 
	 */
	public void Visited(LocationDB loc) {
		visitList.add(loc);

	}

	/**
	 * Checks if location is visited.
	 * 
	 * @param loc
	 *            - Location to check
	 * @return true, if is visited, else false
	 */
	public boolean isVisited(LocationDB loc) {

		if (visitList.contains(loc)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Merge 2 list of locations.
	 * 
	 * @param locList1
	 *            - Location list 1
	 * @param locList2
	 *            - Location list 2
	 * @return the merged list of locations
	 */
	public static List<LocationDB> merge(List<LocationDB> locList1,
			List<LocationDB> locList2) {

		Iterator<LocationDB> it5 = locList2.iterator();
		while (it5.hasNext()) {
			LocationDB locNext = it5.next();
			if (!locList1.contains(locNext)) {
				locList1.add(locNext);
			}
		}
		return locList1;
	}

	/**
	 * Get all neighbourhood points for the cluster.
	 * 
	 */
	public void expandCluster() {

		// Get all neighbours
		int index = 0;
		while (neighbours.size() > index) {
			LocationDB neighbourPt = neighbours.get(index);
			if (!isVisited(neighbourPt)) {
				Visited(neighbourPt);
				List<LocationDB> Neighbours2 = getNeighbours(neighbourPt);
				if (Neighbours2.size() >= Constants.MIN_PTS) {
					neighbours = merge(neighbours, Neighbours2);
				}
			}
			index++;
		}

		// Get Centroid of cluster
		getCentroid();
	}

	/**
	 * Send clusters for labeling
	 * 
	 * @param homeWorkCluster
	 *            boolean for homeWork cluster calculation
	 */
	public void labelCluster(boolean homeWorkCluster) {

		GetLabels getLabels = new GetLabels();
		// If home work cluster is true, calculate home work clusters
		if (homeWorkCluster) {
			// set count of (2nd col)timePoints to zero
			getLabels.initializeTimePoints();

			// Calculate home and work cluster
			getLabels.calculateHomeWorkCluster(neighbours, centroidLat,
					centroidLong, locListSize);
		} else {
			// Get the label of the cluster
			clusterNameId = getLabels.clusterName(centroidLat, centroidLong);

			// Add cluster to the list with appropriate label
			cluster.add(new ClusterInfoDB(String.valueOf(centroidLat), String
					.valueOf(centroidLong), neighbours.get(0).getTimestamp(),
					neighbours.get(0).getClockG(), String
							.valueOf(clusterNameId)));
		}
	}

	/**
	 * Apply dbscan.
	 * 
	 * @param locList
	 *            the list of locations to cluster
	 * @param homeWorkCluster
	 *            whether to calculate (home and work cluster) or google places
	 *            clustering
	 * @return the list of clusters
	 */
	public List<ClusterInfoDB> applyDbscan(List<LocationDB> locList,
			boolean homeWorkCluster) {
		locListSize = locList.size();
		visitList.clear();
		cluster.clear();
		unvisited = locList;

		// Set maxHomeClusterPercenatge to 0 if we need to calculate home work
		// cluster
		if (homeWorkCluster) {
			GlobalParams.MAX_HOME_CLUSTER_PERCENTAGE = 0.0D;
		}

		// Cluster and label places
		for (LocationDB unVisited : unvisited) {
			if (!isVisited(unVisited)) {
				Visited(unVisited);
				neighbours = getNeighbours(unVisited);
				if (neighbours.size() >= Constants.MIN_PTS) {
					expandCluster();
					labelCluster(homeWorkCluster);
				}
			}
		}
		return cluster;
	}

}

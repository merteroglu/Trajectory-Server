package com.merteroglu.Trajectory.Util;

import com.merteroglu.Trajectory.Model.Coordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ReductionUtils {
    Logger logger = LoggerFactory.getLogger(getClass().getName());

    public ReductionUtils() {
    }

    public static double Rad(double d){
        return d * Math.PI / 180.0;
    }

    // Enlem ve boylamına göre iki puanlık mesafe hesaplandı
    public static double geodist(double lat1,double lon1,double lat2,double lon2){
        double radLat1 = Rad(lat1);
        double radLat2 = Rad(lat2);
        double delta_lon = Rad(lon2 - lon1);
        double top1 = Math.cos(radLat2) * Math.sin(delta_lon);
        double top2 = Math.cos(radLat1) * Math.sin(radLat2) - Math.sin(radLat1) * Math.cos(radLat2) * Math.cos(delta_lon);
        double top = Math.sqrt(top1 * top1 + top2 * top2);
        double bottom = Math.sin(radLat1) * Math.sin(radLat2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(delta_lon);
        double delta_sigma = Math.atan2(top,bottom);
        double distance = delta_sigma * 6378137.0;
        return distance;
    }

    public static double distancePointToLine(Coordinate pa , Coordinate pb , Coordinate pc){
        double a = Math.abs(geodist(pa.getLatitude(),pa.getLongitude(),pb.getLatitude(),pb.getLongitude()));
        double b = Math.abs(geodist(pa.getLatitude(),pa.getLongitude(),pc.getLatitude(),pc.getLongitude()));
        double c = Math.abs(geodist(pb.getLatitude(),pb.getLongitude(),pc.getLatitude(),pc.getLongitude()));

        double p = (a + b + c) / 2;
        double area = Math.sqrt(p * (p -a ) * (p - b) * (p-c));
        double distance = area * 2 / b;
        return distance;
    }

    public static boolean isUnderMaxDistance(ArrayList<Coordinate> tmpList, ArrayList<Coordinate> list , int start , int end , double maxDistance){
        int size = tmpList.size();
        int i = 0;
        double distance = 0;

        for (i = 1; i < size - 2 ; i++) {
            distance = distancePointToLine(list.get(start),list.get(start+1),list.get(end));
            if(distance > maxDistance)
                return false;
        }
        return true;
    }

    public static ArrayList<Coordinate> trajectoryReduction(ArrayList<Coordinate> coordinatesList,double maxDistance){
        int i = 0;
        int size = coordinatesList.size();
        int start = 0;
        int end = 0;
        ArrayList<Coordinate> tmpList = new ArrayList<>();
        ArrayList<Coordinate> reducedList = new ArrayList<>();

        reducedList.add(coordinatesList.get(0));

        while(i < size - 1){
            start = i;
            tmpList.add(coordinatesList.get(i));
            tmpList.add(coordinatesList.get(++i));
            end = i;

            while ((i+1) < size && (end - start == 1 || isUnderMaxDistance(tmpList,coordinatesList,start,end,maxDistance))){
                tmpList.add(coordinatesList.get(++i));
                end++;
            }

            if(i == size - 1 && isUnderMaxDistance(tmpList,coordinatesList,start,end,maxDistance))
                reducedList.add(coordinatesList.get(i));
            else
                reducedList.add(coordinatesList.get(--i));

            tmpList.clear();
        }

        return reducedList;
    }




}

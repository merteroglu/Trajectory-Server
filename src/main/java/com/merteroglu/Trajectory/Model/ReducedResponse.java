package com.merteroglu.Trajectory.Model;


import java.util.List;

public class ReducedResponse {

    List<Coordinate> reducedCoordinates;
    double reducedRate;
    double responseTime;

    public ReducedResponse() {
    }

    public ReducedResponse(List<Coordinate> reducedCoordinates, double reducedRate, double responseTime) {
        this.reducedCoordinates = reducedCoordinates;
        this.reducedRate = reducedRate;
        this.responseTime = responseTime;
    }

    public List<Coordinate> getReducedCoordinates() {
        return reducedCoordinates;
    }

    public void setReducedCoordinates(List<Coordinate> reducedCoordinates) {
        this.reducedCoordinates = reducedCoordinates;
    }

    public double getReducedRate() {
        return reducedRate;
    }

    public void setReducedRate(double reducedRate) {
        this.reducedRate = reducedRate;
    }

    public double getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(double responseTime) {
        this.responseTime = responseTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReducedResponse that = (ReducedResponse) o;

        if (Double.compare(that.reducedRate, reducedRate) != 0) return false;
        if (Double.compare(that.responseTime, responseTime) != 0) return false;
        return reducedCoordinates != null ? reducedCoordinates.equals(that.reducedCoordinates) : that.reducedCoordinates == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = reducedCoordinates != null ? reducedCoordinates.hashCode() : 0;
        temp = Double.doubleToLongBits(reducedRate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(responseTime);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        String coordinates = "";
        for (Coordinate c : reducedCoordinates) {
            coordinates += c.toString() + "\n";
        }

        return "ReducedResponse{" +
                ", reducedRate=" + reducedRate +
                ", responseTime=" + responseTime +
                "reducedCoordinates=" + coordinates +
                '}';
    }


}

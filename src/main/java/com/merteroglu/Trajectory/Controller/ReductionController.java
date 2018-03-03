package com.merteroglu.Trajectory.Controller;


import com.merteroglu.Trajectory.Model.Coordinate;
import com.merteroglu.Trajectory.Model.Coordinates;
import com.merteroglu.Trajectory.Model.ReducedResponse;
import com.merteroglu.Trajectory.Util.ReductionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/reduction")
public class ReductionController {

    Logger logger = LoggerFactory.getLogger(getClass().getName());

    @PostMapping()
    public ResponseEntity<ReducedResponse> getCoordinates(@RequestBody Coordinates request){
        long startTime = System.currentTimeMillis();
        if(Objects.isNull(request.getCoordinates())){
            return new ResponseEntity<>(new ReducedResponse(),HttpStatus.BAD_REQUEST);
        }

        logger.info("Coordinates size :"  + request.getCoordinates().size() ) ;

        ArrayList<Coordinate> reducedList = ReductionUtils.trajectoryReduction(request.getCoordinates(),2.0);

        logger.info("Reduced coordinates size :"  + reducedList.size() ) ;

        ReducedResponse reducedResponse = new ReducedResponse();
        reducedResponse.setReducedCoordinates(reducedList);

        double reducedRate = (1.0 - ((double)reducedList.size()/(double)request.getCoordinates().size())) * 100.0;
        reducedResponse.setReducedRate(reducedRate);

        long responseTime = System.currentTimeMillis() - startTime;
        reducedResponse.setResponseTime(responseTime);

        return new ResponseEntity<ReducedResponse>(reducedResponse, HttpStatus.OK);
    }

}

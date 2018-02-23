package com.merteroglu.Trajectory.Controller;


import com.merteroglu.Trajectory.Model.Coordinate;
import com.merteroglu.Trajectory.Model.Coordinates;
import com.merteroglu.Trajectory.Model.ReducedResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/reduction")
public class ReductionController {

    Logger logger = LoggerFactory.getLogger(getClass().getName());


    /*
    Simple Post Json

    ---------------------

    {
    "coordinates" : [{
    	"longitude":"25.42144",
    	"latitude":"27.88787"
    },{
    	"longitude":"35.4231144",
    	"latitude":"13.81238787"
    }]
    }
    --------------------------

     */
    @PostMapping()
    public ResponseEntity<ReducedResponse> getCoordinates(@RequestBody Coordinates request){

        if(Objects.isNull(request.getCoordinates())){
            return new ResponseEntity<>(new ReducedResponse(),HttpStatus.BAD_REQUEST);
        }


        for(Coordinate c : request.getCoordinates()){
            logger.info(c.toString());
        }


        return new ResponseEntity<ReducedResponse>(new ReducedResponse(), HttpStatus.OK);
    }


}

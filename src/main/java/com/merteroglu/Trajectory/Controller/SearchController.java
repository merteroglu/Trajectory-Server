package com.merteroglu.Trajectory.Controller;

import com.merteroglu.Trajectory.Model.Coordinate;
import com.merteroglu.Trajectory.Model.Coordinates;
import com.merteroglu.Trajectory.Model.SearchingBody;
import com.merteroglu.Trajectory.Util.KdTree;
import com.merteroglu.Trajectory.Util.RectHV;
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
@RequestMapping("api/search")
public class SearchController {
    Logger logger = LoggerFactory.getLogger(getClass().getName());

    @PostMapping
    public ResponseEntity<Coordinates> searchCoordinates(@RequestBody SearchingBody request){
        if(Objects.isNull(request.getAllCoordinates()) || Objects.isNull(request.getBottomRight()) || Objects.isNull(request.getTopLeft())){
            return new ResponseEntity<>(new Coordinates(), HttpStatus.BAD_REQUEST);
        }

        KdTree kdTree = new KdTree();

        for (Coordinate c : request.getAllCoordinates()){
            kdTree.insert(c);
        }

        RectHV rect = new RectHV(request.getTopLeft().getLongitude(),request.getBottomRight().getLatitude(),request.getBottomRight().getLongitude(),request.getTopLeft().getLatitude());

        ArrayList<Coordinate> fondCoordinates = kdTree.range(rect);

        Coordinates coordinates = new Coordinates();
        coordinates.setCoordinates(fondCoordinates);

        return new ResponseEntity<Coordinates>(coordinates,HttpStatus.OK);
    }

}

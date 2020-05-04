package org.hyperion.rs2.clipping.pf;

import java.util.ArrayDeque;
import java.util.Deque;

import org.hyperion.rs2.clipping.BasicPoint;

public class PathState {

    private Deque<BasicPoint> points = new ArrayDeque<BasicPoint>();
    private int state = 0;

    public Deque<BasicPoint> getPoints() {
        return points;
    }

    public void routeFailed() {
        this.state = 1;
    }
    
    public void routeIncomplete() {
    	this.state = 2;
    }

    public boolean isRouteFound() {
        return state != 1;
    }
    
    public boolean isRouteIncomplete() {
    	return state == 2;
    }

}

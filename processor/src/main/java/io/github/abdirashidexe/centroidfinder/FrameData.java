/**
 * Represents centroid data for a single video frame.
 *
 * @param time timestamp of the frame in seconds
 * @param x x-coordinate of the centroid
 * @param y y-coordinate of the centroid
 */
package io.github.abdirashidexe.centroidfinder;

public record FrameData(double time, int x, int y) {}
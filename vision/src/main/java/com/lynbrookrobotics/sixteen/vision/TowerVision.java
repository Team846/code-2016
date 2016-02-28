package com.lynbrookrobotics.sixteen.vision;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;

public class TowerVision {
  public static Mat convertToHSV(Mat image) {
    Mat destination = new Mat();

    Imgproc.cvtColor(image, destination, Imgproc.COLOR_BGR2HSV);
    Imgproc.blur(destination, destination, new Size(10, 10));

    Mat mask = new Mat();
    Core.inRange(destination, new Scalar(80, 0, 12), new Scalar(96, 255, 255), mask);

    ArrayList<MatOfPoint> contours = new ArrayList<>();
    Mat matHeirarchy = new Mat();
    Imgproc.findContours(mask, contours, matHeirarchy, Imgproc.RETR_EXTERNAL,
        Imgproc.CHAIN_APPROX_SIMPLE);

    Rect biggest = new Rect(0, 0, 0, 0);
    for (MatOfPoint matOfPoint: contours) {
      Rect rec = Imgproc.boundingRect(matOfPoint);
      if (rec.area() > biggest.area()) {
        biggest = rec;
      }
    }

    Imgproc.rectangle(image, biggest.br(), biggest.tl(), new Scalar(0, 255, 0));

    return image;
  }
}

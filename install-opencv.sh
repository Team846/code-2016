git clone https://github.com/Itseez/opencv.git
cd opencv
git checkout tags/3.1.0
cd ..
mkdir opencv-build
cd opencv-build
cmake ../opencv
make -j4
make -j4 install
cd ..
mkdir vision/lib
cp /usr/local/share/OpenCV/java/opencv-310.jar vision/lib/opencv-310.jar
cp /usr/local/share/OpenCV/java/libopencv_java310.so vision/lib/libopencv_java310.so

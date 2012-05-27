#!/bin/bash


#android update project --path IDscanner --target android-14
#android update project --path IDscannerTest --target android-14

cd tesseract-android-tools
wget http://tesseract-ocr.googlecode.com/files/tesseract-3.01.tar.gz
wget http://leptonica.googlecode.com/files/leptonica-1.68.tar.gz
tar -zxvf tesseract-3.01.tar.gz
tar -zxvf leptonica-1.68.tar.gz
rm -f tesseract-3.01.tar.gz
rm -f leptonica-1.68.tar.gz
mv tesseract-3.01 jni/com_googlecode_tesseract_android/src
mv leptonica-1.68 jni/com_googlecode_leptonica_android/src
/opt/android-ndk-r7b/ndk-build
android update project --path .
ant release


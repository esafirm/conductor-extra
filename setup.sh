#!/bin/bash
echo "Running setup!"
rm settings.gradle
echo "include ':conductor-extra'" > settings.gradle
echo "include ':conductor-extra-butterknife'" >> settings.gradle
echo "include ':conductor-extra-databinding'" >> settings.gradle
echo "include ':conductor-extra-listener'"  >> settings.gradle
echo "include ':conductor-extra-screen'"  >> settings.gradle
echo "include ':conductor-extra-common'"  >> settings.gradle

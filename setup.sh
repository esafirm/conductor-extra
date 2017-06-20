#!/bin/bash
rm settings.gradle
echo "include ':conductor-extra'" > settings.gradle
echo "include ':conductor-extra-butterknife'" >> settings.gradle
echo "include ':conductor-extra-databinding'" >> settings.gradle

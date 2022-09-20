#!/usr/bin/env bash

helm delete a

kubectl wait --for=delete -f deploy/helm/application/templates/

#mvn clean install -B -f ../../../pom.xml

echo "build docker images"

cp example/order/Dockerfile example/order/target
docker rmi freshchen/order
docker build -t freshchen/order example/order/target

echo "helm install"
helm install a deploy/helm/application

kubectl rollout status deployment/order

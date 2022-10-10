#!/usr/bin/env bash

helm delete a

kubectl wait --for=delete -f deploy/helm/application/templates/

#mvn clean install -B -f ../../../pom.xml

echo "build docker images"

cp example/order/Dockerfile example/order/target
docker rmi freshchen/order
docker build -t freshchen/order example/order/target

cp example/payment/Dockerfile example/payment/target
docker rmi freshchen/payment
docker build -t freshchen/payment example/payment/target

echo "helm install"
helm install a deploy/helm/application

kubectl rollout status deployment/order
kubectl rollout status deployment/payment

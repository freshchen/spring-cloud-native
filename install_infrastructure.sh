#!/usr/bin/env bash

helm install i deploy/helm/infrastructure

kubectl rollout status deployment/apollo
kubectl rollout status deployment/grafana
kubectl rollout status deployment/kibana
kubectl rollout status deployment/kube-state-metrics
kubectl rollout status deployment/rabbitmq
kubectl rollout status deployment/redis
kubectl rollout status deployment/skywalking-oap
kubectl rollout status deployment/skywalking-ui
kubectl rollout status deployment/dashboard-metrics-scraper -n kubernetes-dashboard
kubectl rollout status deployment/kubernetes-dashboard -n kubernetes-dashboard

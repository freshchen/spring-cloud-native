helm install apisix apisix/apisix

helm install apisix-dashboard apisix/apisix-dashboard



- skywalking-logger

```json
{
  "disable": false,
  "endpoint_addr": "http://skywalking-oap:12800",
  "include_req_body": true,
  "service_instance_name": "APISIX Instance Name",
  "service_name": "APISIX"
}
```
